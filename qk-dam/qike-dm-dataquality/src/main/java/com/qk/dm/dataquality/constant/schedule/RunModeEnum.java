package com.qk.dm.dataquality.constant.schedule;

/**
 * 运行模式
 * @author shenpengjie
 */
public enum RunModeEnum {
  RUN_MODE_SERIAL("RUN_MODE_SERIAL"),
  RUN_MODE_PARALLEL("RUN_MODE_SERIAL");

  String value;

  RunModeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
