package com.qk.dm.metadata.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenpj
 * @date 2021/8/17 11:48 上午
 * @since 1.0.0
 */
@Configuration
public class FeignConfiguration {
  @Bean
  feign.Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
