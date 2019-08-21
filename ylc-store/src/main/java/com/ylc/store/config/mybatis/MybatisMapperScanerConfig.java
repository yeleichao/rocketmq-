package com.ylc.store.config.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @Date: 2019/8/11
 */
@Configuration
@AutoConfigureAfter(MybatisMapperScanerConfig.class)
public class MybatisMapperScanerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.ylc.store.entity");
        return mapperScannerConfigurer;
    }
}
