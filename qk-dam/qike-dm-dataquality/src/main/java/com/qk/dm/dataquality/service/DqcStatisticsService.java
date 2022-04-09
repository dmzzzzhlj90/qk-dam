package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.statistics.DataSummaryVO;
import com.qk.dm.dataquality.vo.statistics.DimensionVO;
import com.qk.dm.dataquality.vo.statistics.RuleDirVO;
import com.qk.dm.dataquality.vo.statistics.WarnTrendVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/21 5:31 下午
 * @since 1.0.0
 */
public interface DqcStatisticsService {
    DataSummaryVO summary();

    List<DimensionVO> dimension();

    List<RuleDirVO> dir();

    List<DqcProcessInstanceVO> instanceList();

    List<WarnTrendVO> warnTrend();
}
