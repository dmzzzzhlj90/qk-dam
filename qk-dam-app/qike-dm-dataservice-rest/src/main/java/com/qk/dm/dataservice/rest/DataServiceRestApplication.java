package com.qk.dm.dataservice.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 数据查询服务rest api
 *
 * @author daomingzhu
 * @date 2022/04/12
 * @since 1.5.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class DataServiceRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataServiceRestApplication.class, args);
    }
}