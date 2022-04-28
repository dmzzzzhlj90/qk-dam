package com.qk.dam.cache;

import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.SpringCacheAnnotationParser;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CompositeCacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhudaoming
 */
@Configuration(proxyBeanMethods = false)
public class CacheOperationSourceConfiguration {
  @Bean
  public CompositeCacheOperationSource overCompositeCacheOperationSource() {
    return new CompositeCacheOperationSource(
        new AnnotationCacheOperationSource(new DamCacheAnnotationParser()),
        new AnnotationCacheOperationSource(new SpringCacheAnnotationParser()));
  }

  @Bean
  public BeanFactoryCacheOperationSourceAdvisor overCacheAdvisor(
      final CompositeCacheOperationSource compositeCacheOperationSource,
      final BeanFactoryCacheOperationSourceAdvisor beanFactoryCacheOperationSourceAdvisor) {

    beanFactoryCacheOperationSourceAdvisor.setCacheOperationSource(compositeCacheOperationSource);
    return beanFactoryCacheOperationSourceAdvisor;
  }

  @Bean
  public CacheInterceptor overCacheInterceptor(
      final CompositeCacheOperationSource compositeCacheOperationSource,
      final CacheInterceptor cacheInterceptor) {
    cacheInterceptor.setCacheOperationSource(compositeCacheOperationSource);
    return cacheInterceptor;
  }
}
