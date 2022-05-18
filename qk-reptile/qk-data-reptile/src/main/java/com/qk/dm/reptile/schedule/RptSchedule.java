package com.qk.dm.reptile.schedule;

import com.qk.dm.reptile.enums.TimeIntervalEnum;
import com.qk.dm.reptile.service.RptBaseInfoService;
import com.qk.dm.reptile.service.RptFindSourceService;
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

    /**
     * 未设置间隔
     */
    @Scheduled(fixedDelay = 1000*60*60*3)
    public void otherSyncRpt(){
        rptBaseInfoService.timedExecution(TimeIntervalEnum.NO_SET.getName());
    }

    @Scheduled(fixedDelay = 1000*60*20)
    public void twentySyncRpt(){
        LOG.info("二十分钟间隔定时任务开始执行");
        rptBaseInfoService.timedExecution(TimeIntervalEnum.TWENTY.getName());
        LOG.info("二十分钟间隔定时任务执行结束");
    }

    @Scheduled(fixedDelay = 1000*60*30)
    public void thirtySyncRpt(){
        LOG.info("三十分钟间隔定时任务开始执行");
        rptBaseInfoService.timedExecution(TimeIntervalEnum.THIRTY.getName());
        LOG.info("三十分钟间隔定时任务执行结束");
    }

    @Scheduled(fixedDelay = 1000*60*60)
    public void sixtySyncRpt(){
        LOG.info("六十分钟间隔定时任务开始执行");
        rptBaseInfoService.timedExecution(TimeIntervalEnum.SIXTY.getName());
        LOG.info("六十分钟间隔定时任务执行结束");
    }

    @Scheduled(fixedDelay = 1000*60*60*2)
    public void twoHourSyncRpt(){
        LOG.info("两个小时间隔定时任务开始执行");
        rptBaseInfoService.timedExecution(TimeIntervalEnum.TWO_HOUR.getName());
        LOG.info("两个小时间隔定时任务执行结束");
    }

    @Scheduled(fixedDelay = 1000*60*60*3)
    public void threeHourSyncRpt(){
        LOG.info("三个小时间隔定时任务开始执行");
        rptBaseInfoService.timedExecution(TimeIntervalEnum.THREE_HOUR.getName());
        LOG.info("三个小时间隔定时任务执行结束");
    }

    @Scheduled(fixedDelay = 1000*60*60*6)
    public void sixHourSyncRpt(){
        LOG.info("六小时间隔定时任务开始执行");
        rptBaseInfoService.timedExecution(TimeIntervalEnum.SIX_HOUR.getName());
        LOG.info("六小时间隔定时任务执行结束");
    }

    @Scheduled(fixedDelay = 1000*60*60*12)
    public void twelveHourSyncRpt(){
        LOG.info("十二小时间隔定时任务开始执行");
        rptBaseInfoService.timedExecution(TimeIntervalEnum.TWELVE_HOUR.getName());
        LOG.info("十二小时间隔定时任务执行结束");
    }

    @Scheduled(fixedDelay = 1000*60*60*24)
    public void oneDaySyncRpt(){
        LOG.info("二十四小时间隔定时任务开始执行");
        rptBaseInfoService.timedExecution(TimeIntervalEnum.ONE_DAY.getName());
        LOG.info("二十四小时间隔定时任务执行结束");
    }
}
