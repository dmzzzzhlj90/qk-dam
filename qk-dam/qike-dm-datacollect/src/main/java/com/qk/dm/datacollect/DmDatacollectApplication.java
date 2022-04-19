package com.qk.dm.datacollect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 权限服务
 *
 * @author spj
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
@EnableFeignClients(basePackages = "com.qk.dm.*")
public class DmDatacollectApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmDatacollectApplication.class, args);
  }
}
