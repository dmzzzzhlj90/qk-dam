package com.qk.dm.dataquality.enums;

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
  public static String createCronExpression(DqcSchedulerConfigVO taskScheduleModel) {
    StringBuffer cronExp = new StringBuffer("");

    if (null == taskScheduleModel.getSchedulerCycle()) {
      System.out.println("执行周期未配置"); // 执行周期未配置
    }

    switch (taskScheduleModel.getSchedulerCycle()) {
      case 1: // 分钟
        minute(taskScheduleModel, cronExp);
        break;
      case 2: // 小时
        hour(taskScheduleModel, cronExp);
        break;
      case 3: // 天
        day(taskScheduleModel, cronExp);
        break;
      case 4: // 周
        weeks(taskScheduleModel, cronExp);
        break;
      default:
    }
    return cronExp.toString();
  }

  private static void minute(DqcSchedulerConfigVO taskScheduleModel, StringBuffer cronExp) {
    cronExp.append("0 "); // 秒
    cronExp.append("0/").append(taskScheduleModel.getSchedulerIntervalTime()).append(" ");
    cronExp.append("* "); // 时
    cronExp.append("* "); // 日
    cronExp.append("* "); // 月
    cronExp.append("? "); // 周
  }

  private static void hour(DqcSchedulerConfigVO taskScheduleModel, StringBuffer cronExp) {
    cronExp.append("0 ");
    cronExp.append("0 ");
    cronExp.append("0/").append(taskScheduleModel.getSchedulerIntervalTime()).append(" ");
    cronExp.append("* ");
    cronExp.append("* ");
    cronExp.append("? ");
  }

  private static void day(DqcSchedulerConfigVO taskScheduleModel, StringBuffer cronExp) {
    cronExp.append("0 ");
    cronExp.append(dateTime(taskScheduleModel.getSchedulerTimeStart(), 2)).append(" ");
    cronExp.append(dateTime(taskScheduleModel.getSchedulerTimeStart(), 1)).append(" ");
    cronExp.append("* ");
    cronExp.append("* ");
    cronExp.append("? ");
  }

  private static void weeks(DqcSchedulerConfigVO taskScheduleModel, StringBuffer cronExp) {
    cronExp.append("0 ");
    cronExp.append(dateTime(taskScheduleModel.getSchedulerTimeStart(), 2)).append(" ");
    cronExp.append(dateTime(taskScheduleModel.getSchedulerTimeStart(), 1)).append(" ");
    cronExp.append("* ");
    cronExp.append("* ");
    cronExp.append(taskScheduleModel.getSchedulerIntervalTime()).append(" ");
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

  private static int dateTime(Date dateTime, int type) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateTime);
    int hour = calendar.get(Calendar.HOUR_OF_DAY); // Calendar.HOUR为12小时制
    int minute = calendar.get(Calendar.MINUTE);
    if (type == 1) {
      return hour;
    } else if (type == 2) {
      return minute;
    }
    return 0;
  }

  // 参考例子
  public static void main(String[] args) {
    // 执行时间：每天的12时12分12秒 start
    DqcSchedulerConfigVO dqcSchedulerConfigVO = new DqcSchedulerConfigVO();
    dqcSchedulerConfigVO.setSchedulerCycle(1);
    dqcSchedulerConfigVO.setEffectiveTimeStart(parseDate("2021-02-01", "yyyy-MM-dd"));
    dqcSchedulerConfigVO.setEffectiveTimeEnt(parseDate("2021-10-01", "yyyy-MM-dd"));
    dqcSchedulerConfigVO.setSchedulerIntervalTime("15");
    dqcSchedulerConfigVO.setSchedulerTimeStart(parseDate("01:05", "HH:mm"));
    System.out.println(createCronExpression(dqcSchedulerConfigVO));
  }
}
