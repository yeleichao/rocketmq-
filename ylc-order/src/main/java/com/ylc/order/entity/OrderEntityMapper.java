package com.ylc.order.entity;

import com.ylc.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;

public interface OrderEntityMapper {
    int deleteByPrimaryKey(String order_id);

    int insert(OrderEntity record);

    int insertSelective(OrderEntity record);

    OrderEntity selectByPrimaryKey(String order_id);

    int updateByPrimaryKeySelective(OrderEntity record);

    int updateByPrimaryKey(OrderEntity record);

    int updateOrderStatus(@Param("orderId")String orderId, @Param("orderStatus")String orderStatus, @Param("updateBy")String updateBy);
}