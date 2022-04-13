package com.qk.dm.datacollect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 权限服务
 *
 * @author spj
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.qk")
public class DmDatacollectApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmDatacollectApplication.class, args);
  }
}
