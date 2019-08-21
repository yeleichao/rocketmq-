package com.ylc.paya.service.producer;



import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Description: 支付事务消息
 * @Date: 2019/8/17
 */
@Component
public class TransactionProducer implements InitializingBean{

    public static final String NAMESERV_ADDR_2MASTER_2SLAVE = "192.168.247.128:9876;192.168.247.129:9876192.168.247.130:9876;192.168.247.131:9876";
    public static final String PRODUCER_GROUP_NAME = "tx_pay_producer_group_name";

    private TransactionMQProducer producer;

    private ExecutorService executorServicel;

    @Autowired
    private TransactionListenerImpl transactionListenerImpl;


    private TransactionProducer(){
        System.out.println("初始化方法加载");
        this.producer = new TransactionMQProducer(PRODUCER_GROUP_NAME);
        this.executorServicel = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("tx_producer_check_thread");
                        return thread;
                    }
                });
        producer.setExecutorService(executorServicel);
        this.producer.setNamesrvAddr(NAMESERV_ADDR_2MASTER_2SLAVE);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //这个方法表示 当这个类初始化完成之后在注入
        System.out.println("initializingBean 方法执行");
        this.producer.setTransactionListener(transactionListenerImpl);
        start();
    }

    /**
     * 启动producer
     */
    private void start(){
        try{
            this.producer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 关闭producer
     */
    public void shutdown(){
        this.producer.shutdown();
    }

    /**
     * 发送消息
     * @param message
     * @param object
     * @return
     */
    public TransactionSendResult sendMessage(Message message , Object object){
        TransactionSendResult result =  null;

        try{
            result = this.producer.sendMessageInTransaction(message,object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}











































