package com.ylc.order.service;

/**
 * @Description: TODO
 * @Date: 2019/8/13
 */
public interface OrderService {

    boolean createOrder(String cityId, String platformId, String userId, String suppliedId, String goodsId) throws Exception;

    void sendOrderlyMessage4Pkg(String userId, String orderId);
}
