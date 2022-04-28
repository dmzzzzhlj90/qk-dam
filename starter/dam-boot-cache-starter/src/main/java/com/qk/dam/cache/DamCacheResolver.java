package com.qk.dam.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zhudaoming
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DamCacheResolver implements CacheResolver {
  final CacheManagerCompose cacheManagerCompose;

  @Override
  public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
    Collection<Cache> result = new ArrayList<>(CacheManagerEnum.values().length);
    for (CacheManagerEnum cacheNameEnum : CacheManagerEnum.values()) {
      Cache cache = cacheManagerCompose.getCache(cacheNameEnum);

      result.add(cache);
    }
    return result;
  }
}
