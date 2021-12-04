package com.qk.dm.dataquality.constant.schedule;

/**
 * 运行模式(执行方式)
 *
 * @author shenpengjie
 */
public enum RunModeEnum {
  RUN_MODE_SERIAL( "RUN_MODE_SERIAL", "串行"),
  RUN_MODE_PARALLEL( "RUN_MODE_PARALLEL", "并行");

  String code;
  String name;

  RunModeEnum(String code,  String name) {
    this.code = code;
    this.name = name;
  }

  public static RunModeEnum fromValue(String code) {
    for (RunModeEnum b : RunModeEnum.values()) {
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
