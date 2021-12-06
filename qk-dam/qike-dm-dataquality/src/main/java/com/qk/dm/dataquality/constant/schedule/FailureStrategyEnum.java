package com.qk.dm.dataquality.constant.schedule;

/**
 * 失败策略
 *
 * @author shenpengjie
 */
public enum FailureStrategyEnum {
  // 失败策略
  CONTINUE("CONTINUE","继续"),
  END("END","结束");

  String code;
  String name;

  FailureStrategyEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static FailureStrategyEnum fromValue(String code) {
    for (FailureStrategyEnum b : FailureStrategyEnum.values()) {
      if (b.code.equals(code)) {
        return b;
      }
    }
    return null;
  }

  public String getCode() {
    return code;
  }
}
