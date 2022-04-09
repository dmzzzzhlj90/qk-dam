package com.qk.dm.dataquality.constant.schedule;

/**
 * 任务依赖类型
 *
 * @author shenpengjie
 */
public enum TaskDependTypeEnum {
  TASK_ONLY("TASK_ONLY",""),
  TASK_PRE("TASK_PRE",""),
  TASK_POST("TASK_POST","");

  String code;
  String value;

  TaskDependTypeEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public static TaskDependTypeEnum fromValue(String code) {
    for (TaskDependTypeEnum b : TaskDependTypeEnum.values()) {
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
