package com.qk.dm.datacollect.dolphin.service.cron;

import com.qk.dm.datacollect.vo.DctSchedulerConfigVO;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2022/4/21 10:13
 * @since 1.0.0
 */
@Service
public class CronHourServiceImpl implements CronService{
    @Override
    public String createCronExpression(DctSchedulerConfigVO taskScheduleModel) {
        StringBuffer cronExp = new StringBuffer("");
        cronExp.append("0 ");
        cronExp.append("0 ");
        cronExp.append("0/").append(taskScheduleModel.getSchedulerIntervalTime()).append(" ");
        cronExp.append("* ");
        cronExp.append("* ");
        cronExp.append("?");
        return cronExp.toString();
    }
}
