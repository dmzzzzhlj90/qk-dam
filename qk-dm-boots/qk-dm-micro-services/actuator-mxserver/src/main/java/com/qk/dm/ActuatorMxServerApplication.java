package com.qk.dm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 日志服务
 *
 * @author daomingzhu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@ComponentScan(basePackages = "com.qk")
public class ActuatorMxServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ActuatorMxServerApplication.class, args);
  }
}
