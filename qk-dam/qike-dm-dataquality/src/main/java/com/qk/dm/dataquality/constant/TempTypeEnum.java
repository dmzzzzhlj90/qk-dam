package com.qk.dm.dataquality.constant;

/** @author shenpengjie */
public enum TempTypeEnum {
  // 系统内置
  SYSTEM(1, "系统内置"),
  // 自定义
  CUSTOMIZE(2, "自定义");

  Integer code;
  String value;

  TempTypeEnum(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public static TempTypeEnum fromValue(Integer code) {
    for (TempTypeEnum b : TempTypeEnum.values()) {
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
