package com.qk.dm.dataquality.constant;

/** @author shenpengjie */
public enum SchedulerStateEnum {
  // 处理默认状态-未启动
  NOT_STARTED(0, "未启动"),
  SCHEDULING(1, "调度中");

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

  public Integer getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }
}
