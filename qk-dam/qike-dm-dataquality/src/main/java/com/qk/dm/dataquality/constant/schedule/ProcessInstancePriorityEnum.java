package com.qk.dm.dataquality.constant.schedule;

/**
 * 流程实例优先级
 *
 * @author shenpengjie
 */
public enum ProcessInstancePriorityEnum {
  // 优先级
  HIGHEST("HIGHEST","最高"),
  HIGH("HIGH","高"),
  MEDIUM("MEDIUM","中等"),
  LOW("LOW","低"),
  LOWEST("LOWEST","最低");

  String code;
  String name;

  ProcessInstancePriorityEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static ProcessInstancePriorityEnum fromValue(String code) {
    for (ProcessInstancePriorityEnum b : ProcessInstancePriorityEnum.values()) {
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
