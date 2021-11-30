package com.qk.dm.dataquality.constant.schedule;

/**
 * 失败策略
 *
 * @author shenpengjie
 */
public enum FailureStrategyEnum {
  // 失败策略
  CONTINUE("CONTINUE"),
  END("END");

  String value;

  FailureStrategyEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
