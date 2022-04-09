package com.qk.dm.dataquality.constant.schedule;

/**
 * 发送策略
 *
 * @author shenpengjie
 */
public enum WarningTypeEnum {
  NONE("NONE","都不发"),
  SUCCESS("SUCCESS","成功发"),
  FAILURE("FAILURE","失败发"),
  ALL("ALL","成功或失败都发");

  String code;
  String name;

  WarningTypeEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static WarningTypeEnum fromValue(String code) {
    for (WarningTypeEnum b : WarningTypeEnum.values()) {
      if (b.code.equals(code)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected id '" + code + "'");
  }

  public String getCode() {
    return code;
  }
}
