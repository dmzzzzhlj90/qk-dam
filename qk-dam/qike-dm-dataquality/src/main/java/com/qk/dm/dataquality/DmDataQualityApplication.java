package com.qk.dm.dataquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 数据质量服务
 *
 * @author wjq
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qk.dm.*")
@ComponentScan(basePackages = "com.qk")
@EnableAspectJAutoProxy
@EnableScheduling
@EnableCaching
public class DmDataQualityApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmDataQualityApplication.class, args);
  }
}
