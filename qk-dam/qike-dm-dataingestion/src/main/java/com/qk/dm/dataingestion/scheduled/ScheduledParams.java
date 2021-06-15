package com.qk.dm.dataingestion.scheduled;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;

/**
 * TODO 动态获取定时器参数设置(备用)
 *
 * @author wjq
 * @date 20210615
 * @since 1.0.0
 */
public class ScheduledParams implements SchedulingConfigurer {
    private static final Log LOG = LogFactory.get("定期同步批次数据文件");

    public static String DEFAULT_CORN = "0/3 * * * * *";
    //##动态传参要给默认值。
    public static String corn = DEFAULT_CORN;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                //TODO
                // logger.info("定时任务逻辑");
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //任务触发，可修改任务的执行周期
                CronTrigger cronTrigger = new CronTrigger(corn);
                Date date = cronTrigger.nextExecutionTime(triggerContext);
                return date;
            }
        });
    }
}
