package com.qk.dam.cache;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({RedisCacheConfiguration.class})
@EnableConfigurationProperties(CacheProperties.class)
@Slf4j
class CacheConfiguration {
  final CacheProperties cacheProperties;

  CacheConfiguration(CacheProperties cacheProperties) {
    this.cacheProperties = cacheProperties;
  }

  @Bean
  SlaveCacheManager slaveCacheManager(
      CacheProperties cacheProperties,
      ObjectProvider<Caffeine<Object, Object>> caffeine,
      ObjectProvider<CaffeineSpec> caffeineSpec,
      ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
    // todo 此处manager需要支持自由切换 如果redis连接异常自动切换到 CaffeineCache
    CaffeineCacheManager cacheManager =
        createCacheManager(cacheProperties, caffeine, caffeineSpec, cacheLoader);
    List<String> cacheNames = getCacheNames();
    if (!CollectionUtils.isEmpty(cacheNames)) {
      cacheManager.setCacheNames(cacheNames);
    }
    return new SlaveCacheManager() {

      @Override
      public Cache getCache(String name) {
        return cacheManager.getCache(name);
      }

      @Override
      public Collection<String> getCacheNames() {
        return cacheManager.getCacheNames();
      }
    };
  }

  private List<String> getCacheNames() {
    return Arrays.stream(CacheManagerEnum.values())
        .map(CacheManagerEnum::toString)
        .collect(Collectors.toList());
  }

  private CaffeineCacheManager createCacheManager(
      CacheProperties cacheProperties,
      ObjectProvider<Caffeine<Object, Object>> caffeine,
      ObjectProvider<CaffeineSpec> caffeineSpec,
      ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    setCacheBuilder(
        cacheProperties, caffeineSpec.getIfAvailable(), caffeine.getIfAvailable(), cacheManager);
    cacheLoader.ifAvailable(cacheManager::setCacheLoader);
    return cacheManager;
  }

  private void setCacheBuilder(
      CacheProperties cacheProperties,
      CaffeineSpec caffeineSpec,
      Caffeine<Object, Object> caffeine,
      CaffeineCacheManager cacheManager) {
    String specification = cacheProperties.getCaffeine().getSpec();
    if (StringUtils.hasText(specification)) {
      cacheManager.setCacheSpecification(specification);
    } else if (caffeineSpec != null) {
      cacheManager.setCaffeineSpec(caffeineSpec);
    } else if (caffeine != null) {
      cacheManager.setCaffeine(caffeine);
    }
  }
}
