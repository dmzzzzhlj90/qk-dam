package com.qk.dm.reptile.schedule;

import com.qk.dm.reptile.service.RptFindSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class DataCheckSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(DataCheckSchedule.class);

    private final RptFindSourceService rptFindSourceService;
    //数据未对比状态
    public static Integer NO_CONTRAST = 0;
    //数据不存在
    public static Integer NO_EXIST = 2;

    public DataCheckSchedule(RptFindSourceService rptFindSourceService) {
        this.rptFindSourceService = rptFindSourceService;
    }

    /**
     * 对比数据库中是否存在
     */
    @Scheduled(fixedDelay = 1000*60*5)
    public void noExistDataContrast(){
        LOG.info("五分钟间隔数据对比任务开始执行");
        rptFindSourceService.dataContrast(NO_CONTRAST);
        LOG.info("五分钟间隔数据对比任务执行结束");
    }

    /**
     * 不存在的数据定时查看是否已存在
     */
    @Scheduled(fixedDelay = 1000*60*60)
    public void sixtyDataContrast(){
        LOG.info("六十分钟间隔数据对比任务开始执行");
        rptFindSourceService.dataContrast(NO_EXIST);
        LOG.info("六十分钟间隔数据对比任务执行结束");
    }
}
