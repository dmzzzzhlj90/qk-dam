package com.qk.dam.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.core.convert.MappingConfiguration;
import org.springframework.data.redis.core.index.IndexConfiguration;
import org.springframework.data.redis.core.mapping.RedisMappingContext;

/**
 * redis 映射上下文配置
 * @author zhudaoming
 */
@Configuration
@Order(-1)
class RedisMappingConfig {
  @Bean
  public RedisMappingContext keyValueMappingContext() {
    return new RedisMappingContext(
        new MappingConfiguration(new IndexConfiguration(), new MyKeyspaceConfiguration()));
  }

  public static class MyKeyspaceConfiguration extends KeyspaceConfiguration {}
}
