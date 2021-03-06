package com.qk.dam.redis.config;

import static java.util.Collections.singletonMap;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis template配置
 * @author zhudaoming
 */
@Configuration
@Order(-1)
public class RedisTemplateConfig {
  public static final String PREFIX_CACHE_NAME ="dm";
  public static final String COMPUTE_PREFIX_WITH ="v";
  private final RedisConnectionFactory redisConnectionFactory;

  public RedisTemplateConfig(RedisConnectionFactory redisConnectionFactory) {
    this.redisConnectionFactory = redisConnectionFactory;
  }

  /**
   * 默认的cacheManager
   * @param connectionFactory 连接工厂
   * @return RedisCacheManager
   */
  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    RedisCacheConfiguration redisCacheConfiguration =
        defaultCacheConfig()
            .serializeKeysWith(fromSerializer(RedisSerializer.string()))
            .serializeValuesWith(fromSerializer(valueSerializer()))
            // 静态key前缀
//            .prefixCacheNameWith(PREFIX_CACHE_NAME)
            // 计算key前缀
//            .computePrefixWith(cacheName -> COMPUTE_PREFIX_WITH +":"+ cacheName)
            .disableCachingNullValues();

    return RedisCacheManager.builder(connectionFactory)
        // 缓存默认的设置
        .cacheDefaults(redisCacheConfiguration)
        // 预先初始化的缓存名称，并禁止获取null
        .withInitialCacheConfigurations(
            singletonMap("predefined", redisCacheConfiguration.disableCachingNullValues()))
        // 启用RedisCaches以使缓存放置/退出操作与正在进行的Spring管理的事务同步
        .transactionAware()
        .build();
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
