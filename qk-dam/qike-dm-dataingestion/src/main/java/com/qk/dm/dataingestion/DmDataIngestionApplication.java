package com.qk.dm.dataingestion;

import com.qk.dam.commons.util.CodeGenerateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * 数据接入服务 Data Ingestion Service
 *
 * @author daomingzhu
 * @date 2021/06/07
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.qk")
public class DmDataIngestionApplication {
  public static void main(String[] args) {
    SpringApplication.run(DmDataIngestionApplication.class, args);
  }
  @PostConstruct
  public void ttt(){

  }
}
