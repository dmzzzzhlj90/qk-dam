package com.qk.dam.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qk.dam.auth.client.ClientInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

@Configuration
public class SecurityConfiguration {
  @Bean
  RedisIndexedSessionRepository redisIndexedSessionRepository(final RedisTemplate redisTemplate) {
    return new RedisIndexedSessionRepository(redisTemplate);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  }

  @Bean
  @ConfigurationProperties(prefix = "auth.client", ignoreInvalidFields = true)
  public ClientInfo clientInfo() {
    return new ClientInfo();
  }
}
