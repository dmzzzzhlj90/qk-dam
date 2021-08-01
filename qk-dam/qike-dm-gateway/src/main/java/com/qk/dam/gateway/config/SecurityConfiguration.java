package com.qk.dam.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

@Configuration
public class SecurityConfiguration {
    @Bean
    RedisIndexedSessionRepository redisIndexedSessionRepository(final RedisTemplate redisTemplate){
       return  new RedisIndexedSessionRepository(redisTemplate);
    }
}