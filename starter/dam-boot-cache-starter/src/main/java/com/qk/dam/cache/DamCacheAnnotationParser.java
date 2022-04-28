package com.qk.dam.cache;

import org.springframework.cache.annotation.*;
import org.springframework.cache.interceptor.CacheEvictOperation;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.cache.interceptor.CachePutOperation;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;

public class DamCacheAnnotationParser implements CacheAnnotationParser, Serializable {

  private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS =
      new LinkedHashSet<>(8);
  private static final long serialVersionUID = 3598039626439349544L;

  static {
    CACHE_OPERATION_ANNOTATIONS.add(RestCache.class);
    CACHE_OPERATION_ANNOTATIONS.add(QueryCache.class);
    CACHE_OPERATION_ANNOTATIONS.add(CacheEvict.class);
    CACHE_OPERATION_ANNOTATIONS.add(CachePut.class);
    CACHE_OPERATION_ANNOTATIONS.add(Caching.class);
  }

  @Override
  public boolean isCandidateClass(Class<?> targetClass) {
    return AnnotationUtils.isCandidateClass(targetClass, CACHE_OPERATION_ANNOTATIONS);
  }

  @Override
  @Nullable
  public Collection<CacheOperation> parseCacheAnnotations(Class<?> type) {
    DefaultCacheConfig defaultConfig = new DefaultCacheConfig(type);
    return parseCacheAnnotations(defaultConfig, type);
  }

  @Override
  @Nullable
  public Collection<CacheOperation> parseCacheAnnotations(Method method) {
    DefaultCacheConfig defaultConfig = new DefaultCacheConfig(method.getDeclaringClass());
    return parseCacheAnnotations(defaultConfig, method);
  }

  @Nullable
  private Collection<CacheOperation> parseCacheAnnotations(
      DefaultCacheConfig cachingConfig, AnnotatedElement ae) {
    Collection<CacheOperation> ops = parseCacheAnnotations(cachingConfig, ae, false);
    if (ops != null && ops.size() > 1) {
      // More than one operation found -> local declarations override interface-declared ones...
      Collection<CacheOperation> localOps = parseCacheAnnotations(cachingConfig, ae, true);
      if (localOps != null) {
        return localOps;
      }
    }
    return ops;
  }

  @Nullable
  private Collection<CacheOperation> parseCacheAnnotations(
      DefaultCacheConfig cachingConfig, AnnotatedElement ae, boolean localOnly) {

    Collection<? extends Annotation> anns =
        (localOnly
            ? AnnotatedElementUtils.getAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS)
            : AnnotatedElementUtils.findAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS));
    if (anns.isEmpty()) {
      return null;
    }

    final Collection<CacheOperation> ops = new ArrayList<>(1);
    anns.stream()
        .filter(QueryCache.class::isInstance)
        .forEach(ann -> ops.add(parseCacheableAnnotation(ae, cachingConfig, (QueryCache) ann)));
    anns.stream()
        .filter(RestCache.class::isInstance)
        .forEach(ann -> ops.add(parseCacheableAnnotation(ae, cachingConfig, (RestCache) ann)));
    anns.stream()
        .filter(CacheEvict.class::isInstance)
        .forEach(ann -> ops.add(parseEvictAnnotation(ae, cachingConfig, (CacheEvict) ann)));
    anns.stream()
        .filter(CachePut.class::isInstance)
        .forEach(ann -> ops.add(parsePutAnnotation(ae, cachingConfig, (CachePut) ann)));
    return ops;
  }

  private CacheableOperation parseCacheableAnnotation(
      AnnotatedElement ae, DefaultCacheConfig defaultConfig, RestCache restCache) {

    CacheableOperation.Builder builder = new CacheableOperation.Builder();
    String[] cacheNames =
        Arrays.stream(restCache.cacheNames())
            .map(CacheManagerEnum::toString)
            .toArray(String[]::new);

    builder.setName(ae.toString());
    builder.setCacheNames(cacheNames);
    builder.setCondition(restCache.condition());
    builder.setUnless(restCache.unless());
    builder.setKey(
            ObjectUtils.isEmpty(restCache.key())
                    ? Objects.requireNonNull(
                    Arrays.stream(restCache.value())
                            .filter(Objects::nonNull)
                            .map(CacheManagerEnum::getKeyExpression)
                            .findFirst()
                            .orElse(null))
                    : restCache.key());
    builder.setKeyGenerator(restCache.keyGenerator());
    builder.setCacheManager(restCache.cacheManager());
    builder.setCacheResolver(restCache.cacheResolver());
    builder.setSync(restCache.sync());

    defaultConfig.applyDefault(builder);
    CacheableOperation op = builder.build();
    validateCacheOperation(ae, op);

    return op;
  }

  private CacheableOperation parseCacheableAnnotation(
      AnnotatedElement ae, DefaultCacheConfig defaultConfig, QueryCache queryCache) {

    CacheableOperation.Builder builder = new CacheableOperation.Builder();
    String[] cacheNames =
        Arrays.stream(queryCache.cacheNames())
            .map(CacheManagerEnum::toString)
            .toArray(String[]::new);

    builder.setName(ae.toString());
    builder.setCacheNames(cacheNames);
    builder.setCondition(queryCache.condition());
    builder.setUnless(queryCache.unless());
    builder.setKey(
        ObjectUtils.isEmpty(queryCache.key())
            ? Objects.requireNonNull(
                Arrays.stream(queryCache.value())
                    .filter(Objects::nonNull)
                    .map(CacheManagerEnum::getKeyExpression)
                    .findFirst()
                    .orElse(null))
            : queryCache.key());
    builder.setKeyGenerator(queryCache.keyGenerator());
    builder.setCacheManager(queryCache.cacheManager());
    builder.setCacheResolver(queryCache.cacheResolver());
    builder.setSync(queryCache.sync());

    defaultConfig.applyDefault(builder);
    CacheableOperation op = builder.build();
    validateCacheOperation(ae, op);

    return op;
  }

  private CacheEvictOperation parseEvictAnnotation(
      AnnotatedElement ae, DefaultCacheConfig defaultConfig, CacheEvict cacheEvict) {

    CacheEvictOperation.Builder builder = new CacheEvictOperation.Builder();

    builder.setName(ae.toString());
    builder.setCacheNames(cacheEvict.cacheNames());
    builder.setCondition(cacheEvict.condition());
    builder.setKey(cacheEvict.key());
    builder.setKeyGenerator(cacheEvict.keyGenerator());
    builder.setCacheManager(cacheEvict.cacheManager());
    builder.setCacheResolver(cacheEvict.cacheResolver());
    builder.setCacheWide(cacheEvict.allEntries());
    builder.setBeforeInvocation(cacheEvict.beforeInvocation());

    defaultConfig.applyDefault(builder);
    CacheEvictOperation op = builder.build();
    validateCacheOperation(ae, op);

    return op;
  }

  private CacheOperation parsePutAnnotation(
      AnnotatedElement ae, DefaultCacheConfig defaultConfig, CachePut cachePut) {

    CachePutOperation.Builder builder = new CachePutOperation.Builder();

    builder.setName(ae.toString());
    builder.setCacheNames(cachePut.cacheNames());
    builder.setCondition(cachePut.condition());
    builder.setUnless(cachePut.unless());
    builder.setKey(cachePut.key());
    builder.setKeyGenerator(cachePut.keyGenerator());
    builder.setCacheManager(cachePut.cacheManager());
    builder.setCacheResolver(cachePut.cacheResolver());

    defaultConfig.applyDefault(builder);
    CachePutOperation op = builder.build();
    validateCacheOperation(ae, op);

    return op;
  }

  /**
   * Validates the specified {@link CacheOperation}.
   *
   * <p>Throws an {@link IllegalStateException} if the state of the operation is invalid. As there
   * might be multiple sources for default values, this ensure that the operation is in a proper
   * state before being returned.
   *
   * @param ae the annotated element of the cache operation
   * @param operation the {@link CacheOperation} to validate
   */
  private void validateCacheOperation(AnnotatedElement ae, CacheOperation operation) {
    if (StringUtils.hasText(operation.getKey())
        && StringUtils.hasText(operation.getKeyGenerator())) {
      throw new IllegalStateException(
          "Invalid cache annotation configuration on '"
              + ae.toString()
              + "'. Both 'key' and 'keyGenerator' attributes have been set. "
              + "These attributes are mutually exclusive: either set the SpEL expression used to"
              + "compute the key at runtime or set the name of the KeyGenerator bean to use.");
    }
    if (StringUtils.hasText(operation.getCacheManager())
        && StringUtils.hasText(operation.getCacheResolver())) {
      throw new IllegalStateException(
          "Invalid cache annotation configuration on '"
              + ae.toString()
              + "'. Both 'cacheManager' and 'cacheResolver' attributes have been set. "
              + "These attributes are mutually exclusive: the cache manager is used to configure a"
              + "default cache resolver if none is set. If a cache resolver is set, the cache manager"
              + "won't be used.");
    }
  }

  @Override
  public boolean equals(@Nullable Object other) {
    return (other instanceof DamCacheAnnotationParser);
  }

  @Override
  public int hashCode() {
    return DamCacheAnnotationParser.class.hashCode();
  }

  /** Provides default settings for a given set of cache operations. */
  private static class DefaultCacheConfig {

    private final Class<?> target;

    @Nullable private String[] cacheNames;

    @Nullable private String keyGenerator;

    @Nullable private String cacheManager;

    @Nullable private String cacheResolver;

    private boolean initialized = false;

    public DefaultCacheConfig(Class<?> target) {
      this.target = target;
    }

    /**
     * Apply the defaults to the specified {@link CacheOperation.Builder}.
     *
     * @param builder the operation builder to update
     */
    public void applyDefault(CacheOperation.Builder builder) {
      if (!this.initialized) {
        CacheConfig annotation =
            AnnotatedElementUtils.findMergedAnnotation(this.target, CacheConfig.class);
        if (annotation != null) {
          this.cacheNames = annotation.cacheNames();
          this.keyGenerator = annotation.keyGenerator();
          this.cacheManager = annotation.cacheManager();
          this.cacheResolver = annotation.cacheResolver();
        }
        this.initialized = true;
      }

      if (builder.getCacheNames().isEmpty() && this.cacheNames != null) {
        builder.setCacheNames(this.cacheNames);
      }
      if (!StringUtils.hasText(builder.getKey())
          && !StringUtils.hasText(builder.getKeyGenerator())
          && StringUtils.hasText(this.keyGenerator)) {
        builder.setKeyGenerator(this.keyGenerator);
      }

      if (StringUtils.hasText(builder.getCacheManager())
          || StringUtils.hasText(builder.getCacheResolver())) {
        // One of these is set so we should not inherit anything
      } else if (StringUtils.hasText(this.cacheResolver)) {
        builder.setCacheResolver(this.cacheResolver);
      } else if (StringUtils.hasText(this.cacheManager)) {
        builder.setCacheManager(this.cacheManager);
      }
    }
  }
}
