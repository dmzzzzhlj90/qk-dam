package com.qk.dm.dataquality.constant.schedule;

/**
 * 发送策略
 *
 * @author shenpengjie
 */
public enum WarningTypeEnum {
  NONE("NONE"),
  SUCCESS("SUCCESS"),
  FAILURE("FAILURE"),
  ALL("ALL");

  String value;

  WarningTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
