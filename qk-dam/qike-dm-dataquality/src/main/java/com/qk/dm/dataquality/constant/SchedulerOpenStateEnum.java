package com.qk.dm.dataquality.constant;

/** @author shenpengjie */
public enum SchedulerOpenStateEnum {
  // 处理默认状态-未启动
  CLOSE(0, "关闭"),
  OPEN(1, "开启");

  Integer code;
  String value;

  SchedulerOpenStateEnum(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public static SchedulerOpenStateEnum fromValue(Integer code) {
    for (SchedulerOpenStateEnum b : SchedulerOpenStateEnum.values()) {
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
