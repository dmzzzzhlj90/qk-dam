package com.qk.dm.dataquality.constant.schedule;

/**
 * 失败策略
 *
 * @author shenpengjie
 */
public enum FailureStrategyEnum {
  // 失败策略
  CONTINUE(0,"CONTINUE"),
  END(1,"END");

  Integer code;
  String value;


  FailureStrategyEnum(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public static FailureStrategyEnum fromValue(Integer code) {
    for (FailureStrategyEnum b : FailureStrategyEnum.values()) {
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
