package com.ylc.paya.web;

import com.ylc.paya.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Date: 2019/8/17
 */
@RestController
public class PayController {


    @Autowired
    private PayService payService;


    @RequestMapping("/pay")
    public String pay(@RequestParam("userId")String userId,
                      @RequestParam("orderId")String orderId,
                      @RequestParam("accountId")String accountId,
                      @RequestParam("money")BigDecimal money) throws Exception {
        return payService.payment(userId,orderId,accountId,money);
    }
}

















