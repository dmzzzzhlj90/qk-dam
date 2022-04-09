package com.qk.dm.datamodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qk.dm.*")
@ComponentScan(basePackages = "com.qk")
public class DmDataModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmDataModelApplication.class, args);
    }
}
