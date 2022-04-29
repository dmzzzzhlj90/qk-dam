package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataingestion.service.DisStatisticsService;
import com.qk.dm.dataingestion.vo.DisStatisticsVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据引入总览
 * @author wangzp
 * @date 2022/04/25 17:29
 * @since 1.0.0
 */
@RestController
@RequestMapping("/statistics")
public class DisStatisticsController {

    private final DisStatisticsService disStatisticsService;

    public DisStatisticsController(DisStatisticsService disStatisticsService) {
        this.disStatisticsService = disStatisticsService;
    }

    /**
     * 获取任务总览
     * @return DefaultCommonResult<DisStatisticsVO>
     */
    @GetMapping("")
    public DefaultCommonResult<DisStatisticsVO> getDetail(){
        return DefaultCommonResult.success(ResultCodeEnum.OK,disStatisticsService.getDetail());
    }


}
