package com.qk.dm.datacollect.dolphin.service.cron;

import com.qk.dm.datacollect.vo.DctSchedulerConfigVO;

/**
 * @author shenpj
 * @date 2022/4/21 10:09
 * @since 1.0.0
 */
public interface CronService {
    String createCronExpression(DctSchedulerConfigVO taskScheduleModel);
}
