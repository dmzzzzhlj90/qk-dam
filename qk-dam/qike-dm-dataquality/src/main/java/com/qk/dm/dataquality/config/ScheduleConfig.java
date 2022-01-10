package com.qk.dm.dataquality.config;

import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.vo.statistics.handler.InstanceHandler;
import com.qk.dm.dataquality.vo.statistics.handler.TaskInstanceHandler;
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
    private final InstanceHandler instanceHandler;
    private final TaskInstanceHandler taskInstanceHandler;

    public ScheduleConfig(DqcStatisticsService dqcStatisticsService, InstanceHandler instanceHandler, TaskInstanceHandler taskInstanceHandler) {
        this.dqcStatisticsService = dqcStatisticsService;
        this.instanceHandler = instanceHandler;
        this.taskInstanceHandler = taskInstanceHandler;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void scheduleStatistics(){
        log.info("===== 定时开始执行 =====");
        instanceHandler.getDolphinInstanceList();
        taskInstanceHandler.getDolphinTaskInstanceList();
        dqcStatisticsService.summary();
        dqcStatisticsService.dimension();
        dqcStatisticsService.dir();
        log.info("===== 定时结束执行 =====");
    }
}
