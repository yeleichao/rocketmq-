package com.ylc.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ylc.order.constants.OrderStatus;
import com.ylc.order.entity.OrderEntity;
import com.ylc.order.entity.OrderEntityMapper;
import com.ylc.order.service.producer.OrderlyProducer;
import com.ylc.order.utils.FastJsonConvertUtil;
import com.ylc.store.service.api.StoreServiceApi;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: TODO
 * @Date: 2019/8/13
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderEntityMapper orderEntityMapper;


    @Reference(
            version = "1.0.0",
            application = "${dubbo.application.id}",
            interfaceName = "com.ylc.store.service.api.StoreServiceApi",
            check = false,
            timeout = 3000,
            retries = 0 //重试次数
    )
    private StoreServiceApi storeServiceApi;


    @Autowired
    private OrderlyProducer orderlyProducer;


    /**
     * 订单创建，订单信息入表
     * @param cityId
     * @param platformId
     * @param userId
     * @param suppliedId
     * @param goodsId
     * @return
     * @throws Exception
     */
    @Override
    public boolean createOrder(String cityId, String platformId, String userId, String suppliedId, String goodsId) throws Exception{
        boolean flag = false;
        try{
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrder_id(UUID.randomUUID().toString());
            orderEntity.setOrder_type("1");
            orderEntity.setCity_id(cityId);
            orderEntity.setPlatform_id(platformId);
            orderEntity.setUser_id(userId);
            orderEntity.setSupplier_id(suppliedId);
            orderEntity.setGoods_id(goodsId);
            orderEntity.setOrder_status(OrderStatus.ORDER_CREATED.getStatus());
            orderEntity.setRemark("下单成功");
            orderEntity.setCreate_by("admin");

            //查询某条记录的verison
            int currentVersion = storeServiceApi.selectVersion(suppliedId,goodsId);

            //rpc调用 这里应该需要try catch 获取异常，比如通讯异常等 这里为了方便 暂时没写
            int updateCount = storeServiceApi.updateStoreCountByVersion(currentVersion,suppliedId,goodsId,"admin",new Date());
            if(updateCount == 1){
                //订单入库 这里如果sql异常 会有分布式事务的数据一致性问题  可以在catch中回滚 这里为了方便 暂时没写
                orderEntityMapper.insertSelective(orderEntity);
                flag = true;//下单成功
            }else if(updateCount == 0){

                //有两种情况，1.高并发下version版本不一致， 2.库存不足
               int count = storeServiceApi.selectStoreCount(suppliedId,goodsId);
               if(count <= 0){
                   System.out.println("---库存不足");
               }else{
                   System.out.println("乐观锁生效，重新下单");
               }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
            //这里可以捕获详细的异常
        }


        return flag;
    }


    public static final String PKG_TOPIC = "pkg_topic";

    public static final String PKG_TAGS = "pkg";
    @Override
    public void sendOrderlyMessage4Pkg(String userId, String orderId) {
        List<Message> messageList = new ArrayList<>();

        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("userId", userId);
        param1.put("orderId", orderId);
        param1.put("text", "创建包裹操作---1");

        String key1 = UUID.randomUUID().toString() + "$" +System.currentTimeMillis();
        Message message1 = new Message(PKG_TOPIC, PKG_TAGS, key1, FastJsonConvertUtil.convertObjectToJSON(param1).getBytes());

        messageList.add(message1);


        Map<String, Object> param2 = new HashMap<String, Object>();
        param2.put("userId", userId);
        param2.put("orderId", orderId);
        param2.put("text", "发送物流通知操作---2");

        String key2 = UUID.randomUUID().toString() + "$" +System.currentTimeMillis();
        Message message2 = new Message(PKG_TOPIC, PKG_TAGS, key2, FastJsonConvertUtil.convertObjectToJSON(param2).getBytes());

        messageList.add(message2);

        //	顺序消息投递 是应该按照 供应商ID 与topic 和 messagequeueId 进行绑定对应的
        //  supplier_id

        OrderEntity order = orderEntityMapper.selectByPrimaryKey(orderId);
        int messageQueueNumber = Integer.parseInt(order.getSupplier_id());

        //对应的顺序消息的生产者 把messageList 发出去
        orderlyProducer.sendOrderlyMessages(messageList, messageQueueNumber);
    }
}

























