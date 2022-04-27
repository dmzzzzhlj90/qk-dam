package com.qk.dm.datacollect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 元数据采集
 *
 * @author spj
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
@EnableFeignClients(basePackages = "com.qk.dm.*")
public class DmDataCollectApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmDataCollectApplication.class, args);
  }
}
