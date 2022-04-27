package com.qk.dam.cache;

import java.time.Duration;

/**
 * 缓存管理器
 *
 * @author zhudaoming
 */

public enum CacheManagerEnum {
    /**
     * TEST
     */
    TEST("test", Duration.ofSeconds(1)),
    /**
     * 数据质量
     */
    DQC("dqc", Duration.ofHours(8)),
    /**
     * 数据质量 规则
     */
    DQC_RULE("dqc:rule", Duration.ofHours(12)),
    CACHE_NAME_DAS_QUERY("dataservice:query", Duration.ofMinutes(15)),
    /**
     * 数据质量 统计
     */
    DQC_STATISTICS("dqc:statistics", Duration.ofHours(24)),
    /**
     * 数据标准
     */
    DSD("dsd", Duration.ofHours(8)),
    /**
     * 元数据
     */
    MTD("mtd", Duration.ofHours(8)),
    /**
     * 数据模型
     */
    DATA_MODEL("datamodel", Duration.ofHours(8)),
    /**
     * 数据服务
     */
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

    public Duration getDuration() {
        return duration;
    }


    @Override
    public String toString() {
        return name;
    }
}
