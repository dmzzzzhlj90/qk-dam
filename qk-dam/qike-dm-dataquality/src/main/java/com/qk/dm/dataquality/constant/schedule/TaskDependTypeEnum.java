package com.qk.dm.dataquality.constant.schedule;

/**
 * 任务依赖类型
 *
 * @author shenpengjie
 */
public enum TaskDependTypeEnum {
  TASK_ONLY(1,"TASK_ONLY"),
  TASK_PRE(2,"TASK_PRE"),
  TASK_POST(3,"TASK_POST");

  Integer code;
  String value;

  TaskDependTypeEnum(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public static TaskDependTypeEnum fromValue(Integer code) {
    for (TaskDependTypeEnum b : TaskDependTypeEnum.values()) {
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
