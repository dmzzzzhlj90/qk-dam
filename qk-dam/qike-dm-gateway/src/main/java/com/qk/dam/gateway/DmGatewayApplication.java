package com.qk.dam.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.session.data.redis.config.annotation.web.server.RedisWebSessionConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * 统一接入网关
 *
 * @author daomingzhu
 */
@EnableWebFlux
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRedisHttpSession
@EnableRedisWebSession
@ComponentScan(basePackages = "com.qk")
public class DmGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(DmGatewayApplication.class, args);
  }
}
