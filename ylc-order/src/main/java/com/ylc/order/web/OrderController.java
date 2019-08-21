package com.ylc.order.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ylc.order.service.OrderService;
import com.ylc.store.service.api.StoreServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 服务降级测试
 * @Date: 2019/8/12
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;



    //超时降级
    /*@HystrixCommand(
            commandKey = "createOrder",
            commandProperties = {
                    @HystrixProperty(name="execution.timeout.enabled",value="true"),
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
            },
            fallbackMethod = "createOrderFallbackMethod4Timeout"
    )*/

    //线程池限流降级
    /*@HystrixCommand(
            commandKey = "createOrder",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.strategy",value = "THREAD")
            },
            threadPoolKey = "createOrderThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value = "10"),
                    @HystrixProperty(name="maxQueueSize", value = "20000"),
                    @HystrixProperty(name="queueSizeRejectionThreshold",value = "30")
            },
            fallbackMethod = "createOrderFallbackMethod4Timeout"
    )*/


    //信号量方式限流
    /*@HystrixCommand(
            commandKey = "createOrder",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.strategy",value = "SEMAPHORE"),
                    @HystrixProperty(name="execution.isolation.semaphore.maxConcurrentRequests",value = "3")
            },
            fallbackMethod = "createOrderFallbackMethod4Timeout"
    )*/
    @RequestMapping("/createOrder")
    public String createOrder(@RequestParam("cityId")String cityId,
                              @RequestParam("platformId")String platformId,
                              @RequestParam("userId")String userId,
                              @RequestParam("supplierId")String supplierId,
                              @RequestParam("goodsId")String goodsId){
        try{
            boolean b = orderService.createOrder(cityId,platformId,userId,supplierId,goodsId);
            if(b){
                return "下单成功";
            }else{
                return "下单失败";
            }
        }catch (Exception e){
            return "下单失败";
        }

    }

    public String createOrderFallbackMethod4Timeout(@RequestParam("cityId")String cityId,
                                                    @RequestParam("platformId")String platformId,
                                                    @RequestParam("userId")String userId,
                                                    @RequestParam("suppliefId")String suppliefId,
                                                    @RequestParam("goodsId")String goodsId) throws Exception {
        System.err.println("-------超时降级策略执行------------");
        return "hysrtix timeout !";
    }

}

























