package com.ylc.paya.service.impl;

import com.ylc.paya.entity.CustomerAccount;
import com.ylc.paya.mapper.CustomerAccountMapper;
import com.ylc.paya.service.PayService;
import com.ylc.paya.service.producer.CallbackService;
import com.ylc.paya.service.producer.TransactionProducer;
import com.ylc.paya.utils.FastJsonConvertUtil;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @Description: TODO
 * @Date: 2019/8/17
 */
@Service
public class PayServiceImpl implements PayService {

    public static final String TX_PAY_TOPIC = "tx_pay_topic";
    public static final String TX_PAY_TAGS = "pay";


    @Autowired
    private CustomerAccountMapper customerAccountMapper;

    @Autowired
    private TransactionProducer transactionProducer;

    @Autowired
    private CallbackService callbackService;

    @Override
    public String payment(String userId, String orderId, String accountId, BigDecimal money) {
        String paymentRet = "";
        try{
            //查询出当前账户的余额
            CustomerAccount customerAccount = customerAccountMapper.selectByPrimaryKey(accountId);
            int currentVersion = customerAccount.getVersion();
            BigDecimal currentBalance = customerAccount.getCurrent_balance();//余额

            //将金额进行比较  count>=0 表示余额够用
            BigDecimal count = currentBalance.subtract(money);
            if(count.compareTo(BigDecimal.ZERO) >= 0){
                // 1.组装消息
                // 2.执行本地事务   与组装消息 是并行执行的
                String key = UUID.randomUUID().toString()+"$"+System.currentTimeMillis();
                Map<String, Object> params = new HashMap<>();
                params.put("userId",userId);
                params.put("orderId",orderId);
                params.put("accountId",accountId);
                params.put("money", money);

                Message message = new Message(TX_PAY_TOPIC, TX_PAY_TAGS,
                        FastJsonConvertUtil.convertObjectToJSON(params).getBytes());

                //	同步阻塞
                CountDownLatch countDownLatch = new CountDownLatch(1);
                //发消息 并且本地事务执行  并行操作
                params.put("newBalance", count);
                params.put("currentVersion", currentVersion);
                params.put("currentCountDown", countDownLatch);
                TransactionSendResult result = transactionProducer.sendMessage(message,params);

                countDownLatch.await();
                if(result.getSendStatus() == SendStatus.SEND_OK
                        && result.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE) {
                    //	回调order通知支付成功消息
                    callbackService.sendOKMessage(orderId, userId);
                    paymentRet = "支付成功!";
                } else {
                    paymentRet = "支付失败!";
                }
            }else{
                paymentRet = "余额不足";
            }


        }catch (Exception e){
            e.printStackTrace();
            paymentRet = "支付失败";
        }

        return paymentRet;
    }
}

















