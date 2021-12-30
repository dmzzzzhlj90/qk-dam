package com.qk.dam.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@Configuration
@Order(-1)
public class RedisCacheManagerConfig extends RedisTemplateConfig {

    public RedisCacheManagerConfig(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory);
    }

  private RedisCacheConfiguration redisCacheConfiguration(Integer minutes){
    return defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(minutes))
            .serializeKeysWith(fromSerializer(RedisSerializer.string()))
            .serializeValuesWith(fromSerializer(valueSerializer()))
            // 静态key前缀
            .prefixCacheNameWith("dm")
            // 计算key前缀
            .computePrefixWith(cacheName -> "dm-middle-" + cacheName)
            .disableCachingNullValues();
  }

  private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
    Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
    redisCacheConfigurationMap.put("predefined", redisCacheConfiguration(5).disableCachingNullValues());
    redisCacheConfigurationMap.put("statistics", redisCacheConfiguration(2).disableCachingNullValues());
    return redisCacheConfigurationMap;
  }

    /**
     * 根据指定名称来设置时间
     */
  @Override
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
        // 缓存默认的设置
        .cacheDefaults(redisCacheConfiguration(1))
        // 预先初始化的缓存名称，并禁止获取null
        .withInitialCacheConfigurations(getRedisCacheConfigurationMap())
        // 启用RedisCaches以使缓存放置/退出操作与正在进行的Spring管理的事务同步
        .transactionAware()
        .build();
  }

    @Bean
    @Primary
    public CacheManager dynamicTtlCacheManager(RedisConnectionFactory factory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(factory);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        return new DynamicTtlRedisCacheManager(redisCacheWriter, config, 300, 600);
    }
}
