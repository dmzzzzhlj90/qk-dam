package com.qk.dm.datacollect.service.cron;

import com.qk.dm.datacollect.util.DateUtil;
import com.qk.dm.datacollect.vo.DctSchedulerConfigVO;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shenpj
 * @date 2022/4/21 10:13
 * @since 1.0.0
 */
@Service
public class CronDayServiceImpl implements CronService{
    @Override
    public String createCronExpression(DctSchedulerConfigVO taskScheduleModel) {
        StringBuffer cronExp = new StringBuffer();
        Date parse = DateUtil.parseDate(taskScheduleModel.getSchedulerTime(), "HH:mm:ss");
        cronExp.append(DateUtil.second(parse)).append(" ");
        cronExp.append(DateUtil.minute(parse)).append(" ");
        cronExp.append(DateUtil.hour(parse)).append(" ");
        cronExp.append("* ");
        cronExp.append("* ");
        cronExp.append("? ");
        cronExp.append("*");
        return cronExp.toString();
    }
}
