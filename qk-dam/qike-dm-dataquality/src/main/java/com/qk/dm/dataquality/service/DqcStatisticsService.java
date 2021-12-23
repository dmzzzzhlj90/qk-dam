package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.vo.statistics.*;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/21 5:31 下午
 * @since 1.0.0
 */
public interface DqcStatisticsService {
    DataSummaryVO statistics();

    RuleTemplateVO ruleTemplateStatistics();

    JobInfoVO basicInfoStatistics();

    InstanceVO instanceStatistics();

    List<DimensionVO> dimensionStatistics();

    List<RuleDirVO> dirStatistics();
}
