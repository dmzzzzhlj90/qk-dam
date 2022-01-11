package com.qk.dm.dataquality.config;

import com.qk.dm.dataquality.biz.InstanceBiz;
import com.qk.dm.dataquality.biz.TaskInstanceBiz;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author shenpj
 * @date 2021/12/25 3:10 下午
 * @since 1.0.0
 */
@Component
@Order(1)
@Slf4j
public class ScheduleConfig {
    private final DqcStatisticsService dqcStatisticsService;
    private final InstanceBiz instanceBiz;
    private final TaskInstanceBiz taskInstanceBiz;

    public ScheduleConfig(DqcStatisticsService dqcStatisticsService, InstanceBiz instanceBiz, TaskInstanceBiz taskInstanceBiz) {
        this.dqcStatisticsService = dqcStatisticsService;
        this.instanceBiz = instanceBiz;
        this.taskInstanceBiz = taskInstanceBiz;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void scheduleStatistics(){
        long startTime = System.currentTimeMillis();
        log.info("===== 定时开始执行,{} =====",startTime);
        instanceBiz.getDolphinInstanceList();
        taskInstanceBiz.getDolphinTaskInstanceList();
        dqcStatisticsService.summary();
        dqcStatisticsService.dimension();
        dqcStatisticsService.dir();
        log.info("===== 定时结束执行,{} =====",System.currentTimeMillis() - startTime);
    }
}
