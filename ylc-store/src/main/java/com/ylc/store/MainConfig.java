package com.ylc.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 配置项
 * @Date: 2019/8/11
 */
@Configuration
@ComponentScan(basePackages = {"com.ylc.store.*","com.ylc.store.config"})
@MapperScan(basePackages = "mapping.*")
public class MainConfig {

}
