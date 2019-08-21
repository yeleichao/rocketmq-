package com.ylc.paya.service;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Date: 2019/8/17
 */
public interface PayService {

    String payment(String userId, String orderId, String accountId,
                   BigDecimal money);
}
