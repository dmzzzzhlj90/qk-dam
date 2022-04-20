package com.qk.dm.dataingestion.util;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataingestion.enums.SchedulerCycle;

import java.util.Date;

/**
 * @author shenpj
 * @date 2021/11/15 5:19 下午
 * @since 1.0.0
 */
public class CronUtil {

    public static String createCron(String type, String interval, String time) {
        StringBuffer cronExp = new StringBuffer("");

        if (null == type) {
            // 执行周期未配置
            throw new BizException("执行周期未配置");
        }
        switch (SchedulerCycle.fromValue(type)) {
            case minute:
                minute(interval, cronExp);
                break;
            case hour:
                hour(interval, cronExp);
                break;
            case day:
                day(time, cronExp);
                break;
            case week:
                weeks(interval, time, cronExp);
                break;
        }
        return cronExp.toString();
    }

    private static void minute(String interval, StringBuffer cronExp) {
        cronExp.append("0 "); // 秒
        cronExp.append("0/").append(interval).append(" ");
        cronExp.append("* "); // 时
        cronExp.append("* "); // 日
        cronExp.append("* "); // 月
        cronExp.append("?"); // 周
    }

    private static void hour(String interval, StringBuffer cronExp) {
        cronExp.append("0 ");
        cronExp.append("0 ");
        cronExp.append("0/").append(interval).append(" ");
        cronExp.append("* ");
        cronExp.append("* ");
        cronExp.append("?");
    }

    private static void day(String time, StringBuffer cronExp) {
        Date parse = DateUtil.parseDate(time, "HH:mm");
        cronExp.append("0 ");
        cronExp.append(DateUtil.minute(parse)).append(" ");
        cronExp.append(DateUtil.hour(parse)).append(" ");
        cronExp.append("* ");
        cronExp.append("* ");
        cronExp.append("?");
    }

    private static void weeks(String interval, String time, StringBuffer cronExp) {
        //MON TUE WED THU FRI SAT SUN
        Date parse = DateUtil.parseDate(time, "HH:mm");
        cronExp.append("0 ");
        cronExp.append(DateUtil.minute(parse)).append(" ");
        cronExp.append(DateUtil.hour(parse)).append(" ");
        cronExp.append("? ");
        cronExp.append("* ");
        if (interval.split(",").length == 7) {
            cronExp.append("* ");
        } else {
            cronExp.append(interval);
        }
    }
}
