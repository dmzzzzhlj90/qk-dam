package com.qk.dm.datastandards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 数据标准服务
 * @author daomingzhu
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DmDataStandardApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmDataStandardApplication.class, args);
    }
}
