package com.qk.dam.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis template配置
 * @author zhudaoming
 */
@Configuration
class RedisTemplateConfig {
  private final RedisConnectionFactory redisConnectionFactory;

  public RedisTemplateConfig(RedisConnectionFactory redisConnectionFactory) {
    this.redisConnectionFactory = redisConnectionFactory;
  }

  @Bean
  public RedisTemplate<String, byte[]> redisTemplate() {
    RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);

    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setHashKeySerializer(RedisSerializer.string());

    redisTemplate.setValueSerializer(valueSerializer());
    redisTemplate.setHashValueSerializer(valueSerializer());

    redisTemplate.setDefaultSerializer(valueSerializer());

    return redisTemplate;
  }

  /**
   * 配置jackjson 序列化
   * @param <V> 对象值
   * @return 对象值
   */
  @Bean
  @Order(-1)
  public <V> RedisSerializer<V> valueSerializer() {
    // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
    Jackson2JsonRedisSerializer<V> jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    return jackson2JsonRedisSerializer;
  }
}
