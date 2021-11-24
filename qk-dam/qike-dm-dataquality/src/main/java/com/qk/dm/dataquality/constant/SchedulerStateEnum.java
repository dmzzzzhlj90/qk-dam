package com.qk.dm.dataquality.constant;

import java.util.List;

/** @author shenpengjie */
public enum SchedulerStateEnum {
  // 处理默认状态-未启动
  NOT_STARTED(0, "未启动"),
  SCHEDULING(1, "调度中"),
  RUNING(2, "运行中"),
  RUN_FAIL(3, "运行失败"),
  RUN_SUCCEED(4, "运行成功");

  Integer code;
  String value;

  SchedulerStateEnum(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public static SchedulerStateEnum fromValue(Integer code) {
    for (SchedulerStateEnum b : SchedulerStateEnum.values()) {
      if (b.code.equals(code)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected id '" + code + "'");
  }

  static List<SchedulerStateEnum> schedulerStateList;

  static {
    schedulerStateList.add(NOT_STARTED);
    schedulerStateList.add(RUN_FAIL);
    schedulerStateList.add(RUN_SUCCEED);
  }

  public static Boolean checkout(SchedulerStateEnum schedulerStateEnum) {
    return schedulerStateList.contains(schedulerStateEnum);
  }

  public Integer getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }
}
