package com.qk.dm.dataquality.constant.schedule;

/**
 * 发送策略
 *
 * @author shenpengjie
 */
public enum WarningTypeEnum {
  NONE(1,"NONE"),
  SUCCESS(2,"SUCCESS"),
  FAILURE(3,"FAILURE"),
  ALL(4,"ALL");

  Integer code;
  String value;

  WarningTypeEnum(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public static WarningTypeEnum fromValue(Integer code) {
    for (WarningTypeEnum b : WarningTypeEnum.values()) {
      if (b.code.equals(code)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected id '" + code + "'");
  }

  public String getValue() {
    return value;
  }
}
