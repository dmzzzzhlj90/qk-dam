package com.qk.dm.dataquality.constant.schedule;

public enum FailureStrategyEnum {
  CONTINUE(1, "CONTINUE"),
  END(2, "END");

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
