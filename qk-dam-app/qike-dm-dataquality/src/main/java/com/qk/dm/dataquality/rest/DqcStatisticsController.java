package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.vo.statistics.DataSummaryVO;
import com.qk.dm.dataquality.vo.statistics.DimensionVO;
import com.qk.dm.dataquality.vo.statistics.RuleDirVO;
import com.qk.dm.dataquality.vo.statistics.WarnTrendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据质量-质量总揽
 * @author shenpj
 * @date 2021/12/21 5:30 下午
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/statistics")
public class DqcStatisticsController {

    private final DqcStatisticsService dqcStatisticsService;

    public DqcStatisticsController(DqcStatisticsService dqcStatisticsService) {
        this.dqcStatisticsService = dqcStatisticsService;
    }

    /**
     * 数据总揽
     * @return DefaultCommonResult<DataSummaryVO>
     */
    @GetMapping("/summary")
    public DefaultCommonResult<DataSummaryVO> summary() {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dqcStatisticsService.summary());
    }

    /**
     * 当天规则任务按维度统计
     * @return DefaultCommonResult<List<DimensionVO>>
     */
    @GetMapping("/dimension")
    public DefaultCommonResult<List<DimensionVO>> dimension() {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dqcStatisticsService.dimension());
    }

    /**
     * 当天规则质量分类统计
     * @return DefaultCommonResult<List<RuleDirVO>>
     */
    @GetMapping("/dir")
    public DefaultCommonResult<List<RuleDirVO>> dir() {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dqcStatisticsService.dir());
    }

    /**
     * 七天内告警趋势
     * @return DefaultCommonResult<List<WarnTrendVO>>
     */
    @GetMapping("/warn/trend")
    public DefaultCommonResult<List<WarnTrendVO>> warnTrend() {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dqcStatisticsService.warnTrend());
    }
}
