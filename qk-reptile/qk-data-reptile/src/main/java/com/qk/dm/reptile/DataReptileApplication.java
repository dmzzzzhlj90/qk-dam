package com.qk.dm.reptile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 爬虫数据采集
 * @author wangzp
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class DataReptileApplication {
  public static void main(String[] args) {
    SpringApplication.run(DataReptileApplication.class, args);
  }
}
