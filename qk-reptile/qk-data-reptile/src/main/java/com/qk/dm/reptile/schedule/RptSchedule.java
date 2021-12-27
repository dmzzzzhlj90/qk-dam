package com.qk.dm.reptile.schedule;

import com.qk.dm.reptile.service.RptBaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RptSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(RptSchedule.class);
    private final RptBaseInfoService rptBaseInfoService;

    public RptSchedule(RptBaseInfoService rptBaseInfoService){
        this.rptBaseInfoService = rptBaseInfoService;
    }
    @Scheduled(fixedDelay = 1000*60*60*3)
    public void syncRpt(){
        LOG.info("定时任务开始执行");
        rptBaseInfoService.timedExecution();
        LOG.info("定时任务执行结束");
    }
}
