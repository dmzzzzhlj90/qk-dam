package com.qk.dm.dataquality.utils;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author shenpj
 * @date 2021/11/15 5:19 下午
 * @since 1.0.0
 */
public class CronUtil {
  /**
   * 方法摘要：构建Cron表达式
   *
   * @param taskScheduleModel
   * @return String
   */
  public static String createCron(DqcSchedulerConfigVO taskScheduleModel) {
    return createCron(
        taskScheduleModel.getSchedulerCycle(),
        taskScheduleModel.getSchedulerIntervalTime(),
        taskScheduleModel.getSchedulerTime());
  }

  public static String createCron(Integer type, String interval, Date time) {
    StringBuffer cronExp = new StringBuffer("");

    if (null == type) {
      // 执行周期未配置
      throw new BizException("执行周期未配置");
    }
    switch (type) {
      case 1: // 分钟
        minute(interval, cronExp);
        break;
      case 2: // 小时
        hour(interval, cronExp);
        break;
      case 3: // 天
        day(time, cronExp);
        break;
      case 4: // 周
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

  private static void day(Date time, StringBuffer cronExp) {
    cronExp.append("0 ");
    cronExp.append(minuteTime(time)).append(" ");
    cronExp.append(hourTime(time)).append(" ");
    cronExp.append("* ");
    cronExp.append("* ");
    cronExp.append("?");
  }

  private static void weeks(String interval, Date time, StringBuffer cronExp) {
    cronExp.append("0 ");
    cronExp.append(minuteTime(time)).append(" ");
    cronExp.append(hourTime(time)).append(" ");
    cronExp.append("* ");
    cronExp.append("* ");
    cronExp.append(interval);
  }

  public static Date parseDate(String strDate, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return sdf.parse(strDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static int hourTime(Date dateTime) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateTime);
    return calendar.get(Calendar.HOUR_OF_DAY); // Calendar.HOUR为12小时制
  }

  private static int minuteTime(Date dateTime) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateTime);
    return calendar.get(Calendar.MINUTE);
  }

  // 参考例子
  public static void main(String[] args) {
    // 执行时间：每天的12时12分12秒 start
    DqcSchedulerConfigVO dqcSchedulerConfigVO = new DqcSchedulerConfigVO();
    dqcSchedulerConfigVO.setSchedulerCycle(2);
    dqcSchedulerConfigVO.setSchedulerIntervalTime("15");
    dqcSchedulerConfigVO.setSchedulerTime(parseDate("01:05", "HH:mm"));
    System.out.println(createCron(dqcSchedulerConfigVO));
  }
}
