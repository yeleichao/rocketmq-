package com.ylc.paya.service.producer;

import com.ylc.paya.utils.FastJsonConvertUtil;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: TODO
 * @Date: 2019/8/18
 */
@Service
public class CallbackService {
    public static final String CALLBACK_PAY_TOPIC = "callback_pay_topic";

    public static final String CALLBACK_PAY_TAGS = "callback_pay";

    public static final String NAMESERVER = "192.168.247.128:9876;192.168.247.129:9876192.168.247.130:9876;192.168.247.131:9876";

    @Autowired
    private SyncProducer syncProducer;

    public void sendOKMessage(String orderId, String userId) {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("orderId", orderId);
        params.put("status", "2");	//ok

        String keys = UUID.randomUUID().toString() + "$" + System.currentTimeMillis();
        Message message = new Message(CALLBACK_PAY_TOPIC, CALLBACK_PAY_TAGS, keys, FastJsonConvertUtil.convertObjectToJSON(params).getBytes());

        SendResult ret = syncProducer.sendMessage(message);
    }
}
