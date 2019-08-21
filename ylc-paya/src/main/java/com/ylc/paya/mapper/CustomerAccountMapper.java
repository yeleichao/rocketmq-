package com.ylc.paya.mapper;

import com.ylc.paya.entity.CustomerAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface CustomerAccountMapper {
    int deleteByPrimaryKey(String account_id);

    int insert(CustomerAccount record);

    int insertSelective(CustomerAccount record);

    CustomerAccount selectByPrimaryKey(String account_id);

    int updateByPrimaryKeySelective(CustomerAccount record);

    int updateByPrimaryKey(CustomerAccount record);

    int updateBalance(@Param("accountId") String accountId, @Param("newBalance") BigDecimal newBalance, @Param("version")int currentVersion);
}