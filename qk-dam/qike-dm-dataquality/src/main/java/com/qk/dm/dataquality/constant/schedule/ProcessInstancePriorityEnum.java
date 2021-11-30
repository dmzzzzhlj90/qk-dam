package com.qk.dm.dataquality.constant.schedule;

/**
 * 流程实例优先级
 *
 * @author shenpengjie
 */
public enum ProcessInstancePriorityEnum {
  // 优先级
  HIGHEST("HIGHEST"),
  HIGH("HIGH"),
  MEDIUM("MEDIUM"),
  LOW("LOW"),
  LOWEST("LOWEST");

  String value;

  ProcessInstancePriorityEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
