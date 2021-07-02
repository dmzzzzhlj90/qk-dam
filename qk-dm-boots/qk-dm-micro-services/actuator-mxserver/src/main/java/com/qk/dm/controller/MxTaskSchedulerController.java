package com.qk.dm.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.service.MxTaskSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监控调度dolphinscheduler任务实例情况
 *
 * @author wjq
 * @date 2021/7/2 14:29
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/task/scheduler")
public class MxTaskSchedulerController {
    private static final Log LOG = LogFactory.get("监控调度任务实例状态情况");

    private final MxTaskSchedulerService mxTaskSchedulerService;

    @Autowired
    public MxTaskSchedulerController(MxTaskSchedulerService mxTaskSchedulerService) {
        this.mxTaskSchedulerService = mxTaskSchedulerService;
    }


    @GetMapping("/process/instance/state")
    public DefaultCommonResult sendProcessInstanceState() {
        LOG.info("定时监控开始!");
//        LOG.info("定时器时间cron为:【{}】!", timerParam);
        mxTaskSchedulerService.sendProcessInstanceState();
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
}
