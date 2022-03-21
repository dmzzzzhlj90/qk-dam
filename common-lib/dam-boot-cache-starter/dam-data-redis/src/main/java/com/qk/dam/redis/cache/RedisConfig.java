package com.qk.dam.redis.cache;

import com.qk.dam.redis.RedisCacheInfoConf;
import com.qk.dam.redis.config.DynamicTtlRedisCacheManager;
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
public class RedisConfig {

    @Bean
    @Primary
    public CacheManager dynamicTtlCacheManager(final RedisCacheInfoConf redisCacheInfoConf,
                                               final RedisConnectionFactory factory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(factory);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        return new DynamicTtlRedisCacheManager(
                redisCacheWriter,
                config,
                redisCacheInfoConf.getRedisMinSecond(),
                redisCacheInfoConf.getRedisMaxSecond());
    }
}
