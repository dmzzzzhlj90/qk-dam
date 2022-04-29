package com.qk.dam.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
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
public class DamCacheCaffeineResolver implements CacheResolver {
  final CaffeineCacheManager caffeineCacheManager;

  @Override
  public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
    Collection<Cache> result = new ArrayList<>();
    Set<String> cacheNames = context.getOperation().getCacheNames();
    for (String cacheName : cacheNames) {
      Cache cache = caffeineCacheManager.getCache(cacheName);

      result.add(cache);
    }
    return result;
  }
}
