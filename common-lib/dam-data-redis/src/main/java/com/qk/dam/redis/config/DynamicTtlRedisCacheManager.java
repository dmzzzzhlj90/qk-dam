package com.qk.dam.redis.config;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

public class DynamicTtlRedisCacheManager extends RedisCacheManager {

    private RedisCacheWriter cacheWriter;

    private int minSecond;

    private int maxSecond;

    public DynamicTtlRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration,
                                       int minSecond,
                                       int maxSecond) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheWriter = cacheWriter;
        this.minSecond = minSecond;
        this.maxSecond = maxSecond;
    }

    /**
     * 生成自定义的DynamicTtlRedisCache
     * @param name
     * @param cacheConfig
     * @return
     */
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        return new DynamicTtlRedisCache(name,
                cacheWriter,
                cacheConfig != null ? cacheConfig : RedisCacheConfiguration.defaultCacheConfig(),
                minSecond,
                maxSecond);
    }

}
