package com.qk.dm.dataquery.cache;

import com.qk.dam.redis.config.DynamicTtlRedisCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author zhudaoming
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MybatisRedisCacheManager implements Cache {
  public static final String CACHE_NAME_DAS_QUERY = "dataservice:query";
  final CacheManager cm;
  private DynamicTtlRedisCache dynamicTtlRedisCache;

  @PostConstruct
  public void inti() {
    Object cache = cm.getCache(CACHE_NAME_DAS_QUERY);
    if (Objects.nonNull(cache)){
      dynamicTtlRedisCache = (DynamicTtlRedisCache)cache;
      dynamicTtlRedisCache.put("cc","a1");
    }

    Object cc = dynamicTtlRedisCache.get("cc");
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public void putObject(Object key, Object value) {}

  @Override
  public Object getObject(Object key) {
    return null;
  }

  @Override
  public Object removeObject(Object key) {
    return null;
  }

  @Override
  public void clear() {}

  @Override
  public int getSize() {
    return 0;
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return Cache.super.getReadWriteLock();
  }
}
