package com.qk.dam.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * @author zhudaoming
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DamCacheRedisResolver implements CacheResolver {
  final RedisCacheManager redisCacheManager;

  @Override
  public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
    Collection<Cache> result = new ArrayList<>();
    Set<String> cacheNames = context.getOperation().getCacheNames();
    for (String cacheName : cacheNames) {
      Cache cache = redisCacheManager.getCache(cacheName);

      result.add(cache);
    }
    return result;
  }
}
