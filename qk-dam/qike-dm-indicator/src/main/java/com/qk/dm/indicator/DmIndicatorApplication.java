package com.qk.dm.indicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 数据规范-数据指标
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class DmIndicatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmIndicatorApplication.class, args);
  }
}
