package com.qk.dm.datacollect.dto;

import java.util.HashMap;
import java.util.Map;

/** @author shenpengjie */
public enum InstanceStateTypeEnum {
  // 调度运行状态
  SUBMITTED_SUCCESS("SUBMITTED_SUCCESS", "提交成功", SchedulerInstanceStateEnum.RUNING),
  RUNNING_EXECUTION("RUNNING_EXECUTION", "正在运行", SchedulerInstanceStateEnum.RUNING),
  READY_PAUSE("READY_PAUSE", "准备暂停", SchedulerInstanceStateEnum.RUNING),
  PAUSE("PAUSE", "暂停", SchedulerInstanceStateEnum.RUNING),
  READY_STOP("READY_STOP", "准备停止", SchedulerInstanceStateEnum.RUNING),
  STOP("STOP", "停止", SchedulerInstanceStateEnum.STOP),
  FAILURE("FAILURE", "失败", SchedulerInstanceStateEnum.RUN_FAIL),
  SUCCESS("SUCCESS", "成功", SchedulerInstanceStateEnum.RUN_SUCCEED),
  NEED_FAULT_TOLERANCE("NEED_FAULT_TOLERANCE", "需要容错", SchedulerInstanceStateEnum.RUNING),
  KILL("KILL", "kill", SchedulerInstanceStateEnum.STOP),
  WAITTING_THREAD("WAITTING_THREAD", "等待线程", SchedulerInstanceStateEnum.RUNING),
  WAITTING_DEPEND("WAITTING_DEPEND", "等待依赖完成", SchedulerInstanceStateEnum.RUNING),
  WARN("WARN","告警", SchedulerInstanceStateEnum.RUN_SUCCEED);

  String code;
  String value;
  SchedulerInstanceStateEnum schedulerInstanceStateEnum;

  InstanceStateTypeEnum(
      String code, String value, SchedulerInstanceStateEnum schedulerInstanceStateEnum) {
    this.code = code;
    this.value = value;
    this.schedulerInstanceStateEnum = schedulerInstanceStateEnum;
  }

  public static InstanceStateTypeEnum fromValue(String code) {
    for (InstanceStateTypeEnum b : InstanceStateTypeEnum.values()) {
      if (b.code.equals(code)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected id '" + code + "'");
  }

  public static Map<String, String> getAllValue() {
    Map<String, String> val = new HashMap<>();
    for (InstanceStateTypeEnum enums : InstanceStateTypeEnum.values()) {
      val.put(enums.code, enums.value);
    }
    return val;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public SchedulerInstanceStateEnum getSchedulerInstanceStateEnum() {
    return schedulerInstanceStateEnum;
  }
}
