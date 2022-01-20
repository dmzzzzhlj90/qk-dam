package com.qk.dam.redis.cache;

import com.qk.dam.redis.config.CaCheInfoConfig;
import com.qk.dam.redis.config.DynamicTtlRedisCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;


/**
 * @author shenpj
 * @date 2022/1/19 12:03 下午
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "redis")
public class RedisConfig {
    private final CaCheInfoConfig caCheInfoConfig;

    public RedisConfig(CaCheInfoConfig caCheInfoConfig) {
        this.caCheInfoConfig = caCheInfoConfig;
    }

    @Bean
    @Primary
    public CacheManager dynamicTtlCacheManager(RedisConnectionFactory factory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(factory);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        return new DynamicTtlRedisCacheManager(redisCacheWriter, config, caCheInfoConfig.getRedisMinSecond(), caCheInfoConfig.getRedisMaxSecond());
    }
}
