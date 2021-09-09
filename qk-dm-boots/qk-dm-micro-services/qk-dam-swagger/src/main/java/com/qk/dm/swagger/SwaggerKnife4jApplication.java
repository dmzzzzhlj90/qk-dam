package com.qk.dm.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class SwaggerKnife4jApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerKnife4jApplication.class, args);
    }
}
