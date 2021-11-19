package com.qk.dm.dataquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 数据质量服务
 *
 * @author wjq
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class DmDataQualityApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmDataQualityApplication.class, args);
  }
}
