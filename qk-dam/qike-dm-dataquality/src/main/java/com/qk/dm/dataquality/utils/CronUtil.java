package com.qk.dm.dataquality.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.constant.SchedulerCycleEnum;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;

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

  public static String createCron(String type, String interval, String time) {
    StringBuffer cronExp = new StringBuffer("");

    if (null == type) {
      // 执行周期未配置
      throw new BizException("执行周期未配置");
    }
    switch (SchedulerCycleEnum.fromValue(type)) {
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
    common(time, cronExp);
    cronExp.append("?");
  }

  private static void weeks(String interval, String time, StringBuffer cronExp) {
    common(time, cronExp);
    cronExp.append(interval);
  }

  private static void common(String time, StringBuffer cronExp) {
    DateTime parse = DateUtil.parse(time, "HH:mm");
    cronExp.append("0 ");
    cronExp.append(DateUtil.minute(parse)).append(" ");
    cronExp.append(DateUtil.hour(parse, true)).append(" ");
    cronExp.append("* ");
    cronExp.append("* ");
  }

  // 参考例子
  public static void main(String[] args) {
    // 执行时间：每天的12时12分12秒 start
    DqcSchedulerConfigVO dqcSchedulerConfigVO = new DqcSchedulerConfigVO();
    dqcSchedulerConfigVO.setSchedulerCycle("day");
    dqcSchedulerConfigVO.setSchedulerIntervalTime("1");
    dqcSchedulerConfigVO.setSchedulerTime("01:05");
    System.out.println(createCron(dqcSchedulerConfigVO));
  }
}
