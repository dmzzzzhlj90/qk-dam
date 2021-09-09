package com.qk.dm.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 日志服务
 *
 * @author daomingzhu
 */
@SpringBootApplication
@EnableRedisHttpSession
@ComponentScan(basePackages = "com.qk")
public class OAuth2Authorizationserver {
  public static void main(String[] args) {
    SpringApplication.run(OAuth2Authorizationserver.class, args);
  }
}
