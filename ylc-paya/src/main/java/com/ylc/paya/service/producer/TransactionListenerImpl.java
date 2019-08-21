package com.ylc.paya.service.producer;

import com.ylc.paya.mapper.CustomerAccountMapper;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @Description: TODO
 * @Date: 2019/8/17
 */
@Component
public class TransactionListenerImpl implements TransactionListener {

    @Autowired
    private CustomerAccountMapper customerAccountMapper;

    /**
     * 执行本地事务
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("-----------执行本地事务单元-------------");
        CountDownLatch currentCountDown = null;
        Map<String, Object> params = (Map<String, Object>) o;
        String userId = (String) params.get("userId");
        String orderId = (String) params.get("orderId");
        String accountId = (String) params.get("accountId");
        BigDecimal money = (BigDecimal) params.get("money");//这个是需要减的钱
        BigDecimal newBalance = (BigDecimal) params.get("newBalance");//这个是减完剩余的钱
        int currentVersion = (int) params.get("currentVersion");
        currentCountDown = (CountDownLatch)params.get("currentCountDown");
        try{
            //
            int count = this.customerAccountMapper.updateBalance(accountId,newBalance, currentVersion);
            if(count == 1){
                currentCountDown.countDown();
                return LocalTransactionState.COMMIT_MESSAGE;
            }else{
                currentCountDown.countDown();
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }

        }catch (Exception e){
            e.printStackTrace();
            currentCountDown.countDown();
            return LocalTransactionState.ROLLBACK_MESSAGE;

        }

    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("check ----------->");
        return null;
    }
}


















