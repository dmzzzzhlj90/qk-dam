package com.qk.dm.logserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 日志服务
 * @author daomingzhu
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class LogServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogServerApplication.class, args);
    }
}
