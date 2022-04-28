package com.qk.dam.cache;

import java.time.Duration;
import java.util.Arrays;

/**
 * 缓存管理器
 *
 * @author zhudaoming
 */
public enum CacheManagerEnum {
  /** TEST */
  TEST("test", Duration.ofSeconds(1)),
  /** 数据质量 */
  DQC("dqc", Duration.ofHours(8)),
  /** 数据质量 规则 */
  DQC_RULE("dqc:rule", Duration.ofHours(12)),
  CACHE_NAME_DAS_QUERY("dataservice:query", Duration.ofMinutes(10)),
  /** 数据查询服务===>后台查询多条记录 */
  CACHE_NAME_DAS_QUERY_LIST("dataservice:query:list", Duration.ofMinutes(15)),
  /** 数据查询服务===>后台查询单记录 */
  CACHE_NAME_DAS_QUERY_ONE("dataservice:query:one", Duration.ofMinutes(15)),
  /** 数据查询服务 前台rest模块 */
  CACHE_NAME_DAS_REST("dataservice:rest", Duration.ofMinutes(15)),
  /** 数据质量 统计 */
  DQC_STATISTICS("dqc:statistics", Duration.ofHours(24)),
  /** 数据标准 */
  DSD("dsd", Duration.ofHours(8)),
  /** 元数据 */
  MTD("mtd", Duration.ofHours(8)),
  /** 数据模型 */
  DATA_MODEL("datamodel", Duration.ofHours(8)),
  /** 数据服务 */
  DATA_SERVICE("dataservice", Duration.ofHours(8)),
  ;
  private final String name;
  private final Duration duration;

  CacheManagerEnum(String name, Duration duration) {
    this.name = name;
    this.duration = duration;
  }

  public String getName() {
    return name;
  }

  public static CacheManagerEnum getCacheManagerEnum(String name) {

    return Arrays.stream(CacheManagerEnum.values())
        .filter(cacheManagerEnum -> cacheManagerEnum.getName().equals(name))
        .findFirst()
        .orElse(null);
  }

  public Duration getDuration() {
    return duration;
  }

  @Override
  public String toString() {
    return name;
  }
}