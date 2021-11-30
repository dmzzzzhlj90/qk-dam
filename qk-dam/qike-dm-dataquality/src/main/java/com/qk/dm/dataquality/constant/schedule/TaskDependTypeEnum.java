package com.qk.dm.dataquality.constant.schedule;

/**
 * 任务依赖类型
 *
 * @author shenpengjie
 */
public enum TaskDependTypeEnum {
  TASK_ONLY("TASK_ONLY"),
  TASK_PRE("TASK_PRE"),
  TASK_POST("TASK_POST");

  String value;

  TaskDependTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
