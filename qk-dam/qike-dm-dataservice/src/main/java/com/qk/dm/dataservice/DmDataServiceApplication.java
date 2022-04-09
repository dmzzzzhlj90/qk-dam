package com.qk.dm.dataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 数据服务
 *
 * @author wjq
 */
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qk.dm.*")
@ComponentScan(basePackages = "com.qk")
public class DmDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmDataServiceApplication.class, args);
    }
}
