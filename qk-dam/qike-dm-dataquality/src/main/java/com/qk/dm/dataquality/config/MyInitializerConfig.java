package com.qk.dm.dataquality.config;

import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.vo.statistics.handler.InstanceHandler;
import com.qk.dm.dataquality.vo.statistics.handler.TaskInstanceHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author shenpengjie
 */
@Component
@Slf4j
public class MyInitializerConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DqcStatisticsService dqcStatisticsService;
    private final InstanceHandler instanceHandler;
    private final TaskInstanceHandler taskInstanceHandler;

    public MyInitializerConfig(DqcStatisticsService dqcStatisticsService, InstanceHandler instanceHandler, TaskInstanceHandler taskInstanceHandler) {
        this.dqcStatisticsService = dqcStatisticsService;
        this.instanceHandler = instanceHandler;
        this.taskInstanceHandler = taskInstanceHandler;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //调用@Cacheable方法
        log.info("===== 程序启动后执行缓存开始 =====");
        instanceHandler.getDolphinInstanceList();
        taskInstanceHandler.getDolphinTaskInstanceList();
        dqcStatisticsService.summary();
        dqcStatisticsService.dimension();
        dqcStatisticsService.dir();
        log.info("===== 程序启动后执行缓存结束 =====");
    }
}