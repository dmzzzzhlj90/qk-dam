package com.qk.dm.dataquality.constant.schedule;

/**
 * 运行模式(执行方式)
 *
 * @author shenpengjie
 */
public enum RunModeEnum {
  //
  RUN_MODE_SERIAL(1, "RUN_MODE_SERIAL", "串行"),
  RUN_MODE_PARALLEL(2, "RUN_MODE_PARALLEL", "并行");

  Integer code;
  String value;
  String name;

  RunModeEnum(Integer code, String value, String name) {
    this.code = code;
    this.value = value;
    this.name = name;
  }

  public static RunModeEnum fromValue(Integer code) {
    for (RunModeEnum b : RunModeEnum.values()) {
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
