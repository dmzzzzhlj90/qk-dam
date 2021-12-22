package com.qk.dm.dataquality.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author shenpj
 * @date 2021/12/21 5:31 下午
 * @since 1.0.0
 */
public interface DqcStatisticsService {
    Map<String, Long> ruleTemplateStatistics();

    Map<String, Long> basicInfoStatistics();

    Map<String, Integer> instanceStatistics();

    Map<String, Map<String, Integer>> dimensionStatistics();

    Map<String, BigDecimal> dirStatistics();
}
