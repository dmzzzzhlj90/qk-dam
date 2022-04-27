package com.qk.dam.redis.config;

import com.qk.dam.cache.CacheManagerEnum;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Arrays;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

/**
 * 该自定义程序为和配置特定的生存时间
 *
 * @author zhudaoming
 */
@Configuration(proxyBeanMethods = false)
class MyRedisCacheManagerConfiguration {

  /**
   * 默认的cacheManager
   *
   * @param redisSerializer 连接工厂
   * @return RedisCacheManager
   */
  @Bean
  public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer(
      final RedisSerializer<Object> redisSerializer) {
    return builder ->
        Arrays.stream(CacheManagerEnum.values())
            .forEach(
                cacheManagerEnum ->
                    builder.withCacheConfiguration(
                        cacheManagerEnum.toString(),
                        RedisCacheConfiguration.defaultCacheConfig()
                            // 默认 计算key使用::做区隔
                            .computePrefixWith(k -> k + "::")
                            .serializeKeysWith(fromSerializer(RedisSerializer.string()))
                            .serializeValuesWith(fromSerializer(redisSerializer))
                            .entryTtl(cacheManagerEnum.getDuration())));
  }
}
