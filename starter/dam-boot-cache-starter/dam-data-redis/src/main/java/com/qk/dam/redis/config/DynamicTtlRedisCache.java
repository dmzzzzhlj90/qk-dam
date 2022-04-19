package com.qk.dam.redis.config;

import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.Random;

/**
 * 在put方法中随机生成失效时间
 *
 * @author yangwenwu
 */
public class DynamicTtlRedisCache extends RedisCache {

    /**
     * 最小失效时间
     */
    private int minSecond;

    /**
     * 最大失效时间
     */
    private int maxSecond;

    private Random random = new Random();

    private String name;

    private RedisCacheWriter cacheWriter;

    /**
     * Create new {@link RedisCache}.
     *
     * @param name        must not be {@literal null}.
     * @param cacheWriter must not be {@literal null}.
     * @param cacheConfig must not be {@literal null}.
     */
    protected DynamicTtlRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig,
                                   int minSecond, int maxSecond) {
        super(name, cacheWriter, cacheConfig);
        if (minSecond <= 0) {
            throw new IllegalArgumentException("minSecond should be bigger than 0");
        }
        if (minSecond > maxSecond) {
            throw new IllegalArgumentException("maxSecond should be bigger than minSecond");
        }
        this.minSecond = minSecond;
        this.maxSecond = maxSecond;
        this.name = name;
        this.cacheWriter = cacheWriter;
    }

    /*
        实际上RedisCache中有put和putIfAbsent两个添加缓存的方法，因此都要进行处理
        从父类中复制代码并修改ttl相关的部分
     */

    @Override
    public void put(Object key, @Nullable Object value) {

        Object cacheValue = preProcessCacheValue(value);

        if (!isAllowNullValues() && cacheValue == null) {

            throw new IllegalArgumentException(String.format(
                    "Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.",
                    name));
        }

        cacheWriter.put(name, createAndConvertCacheKey(key), serializeCacheValue(cacheValue), getRandomDuration(minSecond, maxSecond));
    }


    @Override
    public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {

        Object cacheValue = preProcessCacheValue(value);

        if (!isAllowNullValues() && cacheValue == null) {
            return get(key);
        }

        byte[] result = cacheWriter.putIfAbsent(name, createAndConvertCacheKey(key), serializeCacheValue(cacheValue),
                getRandomDuration(minSecond, maxSecond));

        if (result == null) {
            return null;
        }

        return new SimpleValueWrapper(fromStoreValue(deserializeCacheValue(result)));
    }

    /**
     * 两个put方法需要
     *
     * @param key
     * @return
     */
    private byte[] createAndConvertCacheKey(Object key) {
        return serializeCacheKey(createCacheKey(key));
    }

    /**
     * 根据失效时间的范围随机生成一个Duration
     *
     * @param minSecond
     * @param maxSecond
     * @return
     */
    private Duration getRandomDuration(int minSecond, int maxSecond) {
        int diff = maxSecond - minSecond;
        int randomSecond = minSecond + random.nextInt(diff + 1);
        return Duration.ofSeconds(randomSecond);
    }

}
