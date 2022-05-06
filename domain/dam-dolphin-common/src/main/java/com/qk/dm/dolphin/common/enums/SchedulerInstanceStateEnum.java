package com.qk.dm.dolphin.common.enums;

/** @author shenpengjie */
public enum SchedulerInstanceStateEnum {
  // 处理默认状态-未启动
  INIT(0, "初始状态"),
  RUNING(1, "运行中"),
  STOP(2, "停止"),
  RUN_SUCCEED(3, "成功"),
  RUN_FAIL(4, "失败");

  Integer code;
  String value;

  SchedulerInstanceStateEnum(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public static SchedulerInstanceStateEnum fromValue(Integer code) {
    for (SchedulerInstanceStateEnum b : SchedulerInstanceStateEnum.values()) {
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
