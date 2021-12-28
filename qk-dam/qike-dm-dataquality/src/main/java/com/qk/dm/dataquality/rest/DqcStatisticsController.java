package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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

    @GetMapping("/summary")
    public DefaultCommonResult statistics() {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dqcStatisticsService.statistics());
    }

    @GetMapping("/dimension")
    public DefaultCommonResult dimensionStatistics() {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dqcStatisticsService.dimensionStatistics());
    }

    @GetMapping("/dir")
    public DefaultCommonResult dirStatistics() {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dqcStatisticsService.dirStatistics());
    }
}
