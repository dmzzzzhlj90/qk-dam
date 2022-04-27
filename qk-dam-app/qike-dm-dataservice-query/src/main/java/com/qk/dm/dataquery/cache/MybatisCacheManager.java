package com.qk.dm.dataquery.cache;

import com.qk.dam.cache.CacheManagerCompose;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.qk.dam.cache.CacheManagerEnum.CACHE_NAME_DAS_QUERY;

/**
 * @author zhudaoming
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MybatisCacheManager {
  final CacheManagerCompose cacheManagerCompose;

  @PostConstruct
  public void inti() {
    Cache cache = cacheManagerCompose.getCache(CACHE_NAME_DAS_QUERY);
    cache.put("cc","a1");
    System.out.println(cache.get("cc"));
  }
}
