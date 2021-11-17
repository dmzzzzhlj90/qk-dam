package com.qk.dm.dataquality.constant.schedule;

public enum ProcessInstancePriorityEnum {
  HIGHEST(1, "HIGHEST"),
  HIGH(2, "HIGH"),
  MEDIUM(3, "MEDIUM"),
  LOW(4, "LOW"),
  LOWEST(5, "LOWEST");

  Integer code;
  String value;

  ProcessInstancePriorityEnum(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public static ProcessInstancePriorityEnum fromValue(Integer code) {
    for (ProcessInstancePriorityEnum b : ProcessInstancePriorityEnum.values()) {
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
