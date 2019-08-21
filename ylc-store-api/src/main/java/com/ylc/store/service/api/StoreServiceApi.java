package com.ylc.store.service.api;

import java.util.Date;

/**
 * @Description: 库存给出的三个对外接口
 * @Date: 2019/8/14
 */
public interface StoreServiceApi {

    int selectVersion(String supplierId, String goodsId);

    int updateStoreCountByVersion(int version, String supplierId, String goodsId, String updateBy, Date updateTime);

    int selectStoreCount(String supplierId, String goodsId);


}
























