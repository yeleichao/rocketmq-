package com.ylc.store.service.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.ylc.store.service.api.HelloServiceApi;

/**
 * @Description: dubbo服务提供者
 * @Date: 2019/8/11
 */
@Service(version = "1.0.0",application = "${dubbo.application.id}",
protocol = "${dubbo.protocol.id}",registry = "${dubbo.registry.id}")
public class HelloServiceProvider implements HelloServiceApi {
    @Override
    public String sayHello(String name) {
        System.out.println("-----name:"+name);
        return "hello "+name;
    }
}
