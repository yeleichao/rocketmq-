package com.ylc.store.entity;

import com.ylc.store.entity.StoreEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface StoreEntityMapper {
    int deleteByPrimaryKey(String store_id);

    int insert(StoreEntity record);

    int insertSelective(StoreEntity record);

    StoreEntity selectByPrimaryKey(String store_id);

    int updateByPrimaryKeySelective(StoreEntity record);

    int updateByPrimaryKey(StoreEntity record);

    int selectVersion(@Param("supplierId") String supplierId, @Param("goodsId") String goodsId);

    int updateStoreCountByVersion(@Param("version") int version, @Param("supplierId") String supplierId,
                                  @Param("goodsId") String goodsId, @Param("updateBy") String updateBy,
                                  @Param("updateTime") Date updateTime);

    int selectStoreCount(@Param("supplierId") String supplierId,@Param("goodsId")  String goodsId);
}









