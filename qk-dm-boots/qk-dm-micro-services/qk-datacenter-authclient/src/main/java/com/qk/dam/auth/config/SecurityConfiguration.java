package com.qk.dam.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
}
