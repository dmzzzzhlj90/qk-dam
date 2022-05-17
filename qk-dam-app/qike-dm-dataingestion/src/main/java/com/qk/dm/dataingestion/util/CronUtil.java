package com.qk.dm.dataingestion.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataingestion.enums.SchedulerCycle;
import com.qk.dm.dataingestion.enums.SchedulerType;
import com.qk.dm.dataingestion.vo.DisSchedulerConfigVO;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author shenpj
 * @date 2021/11/15 5:19 下午
 * @since 1.0.0
 */
public class CronUtil {

    private static final String SCHEDULE_TIME_START = "startTime";
    private static final String SCHEDULE_TIME_END = "endTime";
    private static final String SCHEDULE_CRON = "crontab";

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
        cronExp.append("? "); // 周
        cronExp.append("*"); // 年
    }

    private static void hour(String interval, StringBuffer cronExp) {
        cronExp.append("0 ");
        cronExp.append("0 ");
        cronExp.append("0/").append(interval).append(" ");
        cronExp.append("* ");
        cronExp.append("* ");
        cronExp.append("? ");
        cronExp.append("*");
    }

    private static void day(String time, StringBuffer cronExp) {
        Date parse = DateUtil.parseDate(time, "HH:mm");
        cronExp.append("0 ");
        cronExp.append(DateUtil.minute(parse)).append(" ");
        cronExp.append(DateUtil.hour(parse)).append(" ");
        cronExp.append("* ");
        cronExp.append("* ");
        cronExp.append("? ");
        cronExp.append("*");
    }

    private static void weeks(String interval, String time, StringBuffer cronExp) {
        //MON TUE WED THU FRI SAT SUN
        Date parse = DateUtil.parseDate(time, "HH:mm");
        cronExp.append("0 ");
        cronExp.append(DateUtil.minute(parse)).append(" ");
        cronExp.append(DateUtil.hour(parse)).append(" ");
        cronExp.append("? ");
        cronExp.append("* ");
        cronExp.append(interval.split(",").length == 7?cronExp.append("*"):interval);
        cronExp.append(" *");
    }

    public static String schedule(Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> m = Map.of(SCHEDULE_TIME_START, DateUtil.toStr(effectiveTimeStart),
                SCHEDULE_TIME_END, DateUtil.toStr(effectiveTimeEnt),
                SCHEDULE_CRON, cron,
                "timezoneId", "Asia/Shanghai");
        try {
            return objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建Cron表达式
     */
    public static String createCron(DisSchedulerConfigVO disSchedulerConfig) {
        //判断是否是周期调度
        if(Objects.equals(disSchedulerConfig.getSchedulerType(), SchedulerType.CYCLE.getCode())) {
            return CronUtil.createCron(disSchedulerConfig.getSchedulerCycle(),
                    disSchedulerConfig.getSchedulerIntervalTime(),
                    disSchedulerConfig.getSchedulerTime());
        }
        return null;
    }
}
