package com.qk.dam.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author zhudaoming
 */
@Component
@Slf4j
public class CacheManagerCompose {
    public static final String REDIS = "redis";
    public static final String SLAVE = "slave";
    final RedisCacheManager redisCacheManager;
    final SlaveCacheManager slaveCacheManager;
    final CacheProperties cacheProperties;

    private volatile String activeCache = REDIS;

    CacheManagerCompose(RedisCacheManager redisCacheManager,
                        SlaveCacheManager slaveCacheManager,
                        CacheProperties cacheProperties) {
        this.redisCacheManager = redisCacheManager;
        this.slaveCacheManager = slaveCacheManager;
        this.cacheProperties = cacheProperties;

        final Cache redisCache = redisCacheManager.getCache(CacheManagerEnum.TEST.toString());

        // first test
        testCache(redisCache);

    }
    @PostConstruct
    public void init(){
        final Cache redisCache = redisCacheManager.getCache(CacheManagerEnum.TEST.toString());
        new Thread(() -> new Timer("checkCache").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                testCache(redisCache);
            }
        },new Date(),15*60*1000)).start();
    }

    private void testCache(Cache cache) {
        Objects.requireNonNull(cache);
        try{
            cache.put("t",1);
        }catch (Exception e){
            log.error("redis初始化异常====>{}",e.getLocalizedMessage());
            activeCache = SLAVE;
        }
    }


    public Cache getCache(CacheManagerEnum cacheManagerEnum) {
        if (REDIS.equals(activeCache)){
           return redisCacheManager.getCache(cacheManagerEnum.toString());
        }
        if (SLAVE.equals(activeCache)){
           return slaveCacheManager.getCache(cacheManagerEnum.toString());
        }
        return null;
    }

    public Collection<String> getCacheNames() {
        if (REDIS.equals(activeCache)){
            return redisCacheManager.getCacheNames();
        }
        if (SLAVE.equals(activeCache)){
            return slaveCacheManager.getCacheNames();
        }
        return List.of();
    }
}
