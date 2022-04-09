package com.qk.dm.dataquality.config;

import com.qk.dm.dataquality.biz.CacheBiz;
import com.qk.dm.dataquality.biz.RedisBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
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
public class ScheduleConfig implements ApplicationListener<ApplicationReadyEvent> {
    private final CacheBiz cacheBiz;
    private final RedisBiz redisBiz;

    public ScheduleConfig(
            CacheBiz cacheBiz, RedisBiz redisBiz) {
        this.cacheBiz = cacheBiz;
        this.redisBiz = redisBiz;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        extracted();
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void scheduleStatistics() {
        extracted();
    }

    private void extracted() {
        try {
            //调用@Cacheable方法
            long startTime = System.currentTimeMillis();
            log.info("===== 定时开始执行,{} =====", startTime);
            redisBiz.redisInstanceList();
            redisBiz.redisTaskInstanceList();
            cacheBiz.summary(null);
            cacheBiz.dimension(null);
            cacheBiz.dir(null);
            log.info("===== 定时结束执行,{} =====", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("===== 定时执行出错,{} =====", e.getMessage());
        }
    }

}
