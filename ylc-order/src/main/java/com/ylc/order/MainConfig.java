package com.ylc.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 配置项
 * @Date: 2019/8/11
 */
@Configuration
@ComponentScan(basePackages = {"com.ylc.order.*","com.ylc.order.config"})
@MapperScan(basePackages = "mapping.*")
public class MainConfig {

}
