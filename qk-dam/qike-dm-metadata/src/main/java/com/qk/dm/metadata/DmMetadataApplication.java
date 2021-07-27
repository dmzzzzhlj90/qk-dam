package com.qk.dm.metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 数据资产管理-元数据服务中心
 *
 * @author daomingzhu
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class DmMetadataApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmMetadataApplication.class, args);
  }
}
