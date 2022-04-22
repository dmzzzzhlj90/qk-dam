package com.qk.dm.dataquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 数据查询服务
 *
 * @author zhudaoming
 * @date  20220414
 * @since 1.5.0
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients(basePackages =  "com.qk")
@ComponentScan(basePackages = "com.qk")
public class DmDataServiceQueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmDataServiceQueryApplication.class, args);
    }
}
