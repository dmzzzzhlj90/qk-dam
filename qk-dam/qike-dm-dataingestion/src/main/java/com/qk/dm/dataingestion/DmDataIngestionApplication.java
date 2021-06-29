package com.qk.dm.dataingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 数据接入服务 Data Ingestion Service
 *
 * @author daomingzhu
 * @date 2021/06/07
 * @since 1.0.0
 */
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class DmDataIngestionApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmDataIngestionApplication.class, args);
  }
}
