package com.ylc.order.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ylc.store.service.api.HelloServiceApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Date: 2019/8/11
 */
@RestController
public class HelloController {

    @Reference(
            version = "1.0.0",
            application = "${dubbo.application.id}",
            interfaceName = "com.ylc.store.service.api.HelloServiceApi",
            check = false,
            timeout = 3000,
            retries = 0 //重试次数
    )
    private HelloServiceApi helloServiceApi;

    @RequestMapping("/hello")
    public String hello(@RequestParam("name") String name){
        System.out.println("-----------");
        return helloServiceApi.sayHello(name);
    }
}



































