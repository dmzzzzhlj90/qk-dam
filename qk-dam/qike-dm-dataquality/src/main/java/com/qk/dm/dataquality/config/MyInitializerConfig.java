package com.qk.dm.dataquality.config;

import com.qk.dm.dataquality.biz.InstanceBiz;
import com.qk.dm.dataquality.biz.TaskInstanceBiz;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author shenpengjie
 */
//@Component
@Slf4j
public class MyInitializerConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DqcStatisticsService dqcStatisticsService;
    private final InstanceBiz instanceBiz;
    private final TaskInstanceBiz taskInstanceBiz;

    public MyInitializerConfig(DqcStatisticsService dqcStatisticsService, InstanceBiz instanceBiz, TaskInstanceBiz taskInstanceBiz) {
        this.dqcStatisticsService = dqcStatisticsService;
        this.instanceBiz = instanceBiz;
        this.taskInstanceBiz = taskInstanceBiz;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //调用@Cacheable方法
        try {
            log.info("===== 程序启动后执行缓存开始 =====");
            instanceBiz.getDolphinInstanceList();
            taskInstanceBiz.getDolphinTaskInstanceList();
            dqcStatisticsService.summary();
            dqcStatisticsService.dimension();
            dqcStatisticsService.dir();
            log.info("===== 程序启动后执行缓存结束 =====");
        } catch (Exception e) {
            log.error("===== 程序启动后执行缓存出错,{} =====", e.getMessage());
        }
    }
}