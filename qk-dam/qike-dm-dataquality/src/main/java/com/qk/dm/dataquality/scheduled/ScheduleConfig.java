package com.qk.dm.dataquality.scheduled;

import com.qk.dm.dataquality.service.DqcStatisticsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author shenpj
 * @date 2021/12/25 3:10 下午
 * @since 1.0.0
 */
@Component
public class ScheduleConfig {
    private final DqcStatisticsService dqcStatisticsService;

    public ScheduleConfig(DqcStatisticsService dqcStatisticsService) {
        this.dqcStatisticsService = dqcStatisticsService;
    }

    @PostConstruct
//    @Scheduled(cron = "0 0/15 * * * ?")
    public void schedule(){
        dqcStatisticsService.timeToReis();
    }
}
