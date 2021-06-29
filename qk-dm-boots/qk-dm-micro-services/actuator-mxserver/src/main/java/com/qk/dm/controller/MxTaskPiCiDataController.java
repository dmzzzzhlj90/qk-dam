package com.qk.dm.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.service.MxTaskPiCiDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wjq
 * @date 2021/6/22 16:51
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/metrics")
public class MxTaskPiCiDataController {
    private static final Log LOG = LogFactory.get("定时监控数据同步");

    @Value("${prometheus.timer.param}")
    private String timerParam;

    private final MxTaskPiCiDataService mxTaskPiCiDataService;

    @Autowired
    public MxTaskPiCiDataController(MxTaskPiCiDataService mxTaskPiCiDataService) {
        this.mxTaskPiCiDataService = mxTaskPiCiDataService;
    }

    @Scheduled(cron = "${prometheus.timer.param}")
    @GetMapping("/task/pici")
    public DefaultCommonResult sendTaskPiCiMetrics() {
        LOG.info("定时监控开始!");
        LOG.info("定时器时间cron为:【{}】!", timerParam);
        mxTaskPiCiDataService.sendTaskPiCiMetrics();
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
}
