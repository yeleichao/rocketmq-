package com.ylc.store.service.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.ylc.store.entity.StoreEntityMapper;
import com.ylc.store.service.api.StoreServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description: TODO
 * @Date: 2019/8/14
 */
@Service(version = "1.0.0",application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",registry = "${dubbo.registry.id}")
public class StoreServiceProvider implements StoreServiceApi {
    private static final Logger log = LoggerFactory.getLogger(StoreServiceProvider.class);

    @Autowired
    private StoreEntityMapper storeEntityMapper;

    @Override
    public int selectVersion(String supplierId, String goodsId) {
        log.info("【查询版本号】,supplierId:{},goodsId:{}", supplierId, goodsId);
        return storeEntityMapper.selectVersion(supplierId,goodsId);
    }

    @Override
    public int updateStoreCountByVersion(int version, String supplierId, String goodsId, String updateBy, Date updateTime) {
        log.info("【修改库存】,version:{},supplierId:{},goodsId:{},updateBy:{}", version,supplierId, goodsId,updateBy);
        return storeEntityMapper.updateStoreCountByVersion(version,supplierId,goodsId,updateBy,updateTime);
    }

    @Override
    public int selectStoreCount(String supplierId, String goodsId) {
        log.info("【查询库存数量】,supplierId:{},goodsId:{}", supplierId, goodsId);
        return storeEntityMapper.selectStoreCount(supplierId,goodsId);
    }
}















