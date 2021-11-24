package com.qk.dm.dataquality.constant.schedule;

import com.qk.dm.dataquality.constant.SchedulerStateEnum;

/** @author shenpengjie */
public enum InstanceStateTypeEnum {
  // 调度运行状态
  SUBMITTED_SUCCESS("SUBMITTED_SUCCESS", "提交成功", "运行中", SchedulerStateEnum.RUNING),
  RUNNING_EXECUTION("RUNNING_EXECUTION", "正在运行", "运行中", SchedulerStateEnum.RUNING),
  READY_PAUSE("READY_PAUSE", "准备暂停", "运行中", SchedulerStateEnum.RUNING),
  PAUSE("PAUSE", "暂停", "失败", SchedulerStateEnum.RUN_FAIL),
  READY_STOP("READY_STOP", "准备停止", "运行中", SchedulerStateEnum.RUNING),
  STOP("STOP", "停止", "失败", SchedulerStateEnum.RUN_FAIL),
  FAILURE("FAILURE", "失败", "失败", SchedulerStateEnum.RUN_FAIL),
  SUCCESS("SUCCESS", "成功", "成功", SchedulerStateEnum.RUN_SUCCEED),
  NEED_FAULT_TOLERANCE("NEED_FAULT_TOLERANCE", "需要容错", "运行中", SchedulerStateEnum.RUNING),
  KILL("KILL", "kill", "失败", SchedulerStateEnum.RUN_FAIL),
  WAITTING_THREAD("WAITTING_THREAD", "等待线程", "运行中", SchedulerStateEnum.RUNING),
  WAITTING_DEPEND("WAITTING_DEPEND", "等待依赖完成", "运行中", SchedulerStateEnum.RUNING);

  String code;
  String value;
  String notes;
  SchedulerStateEnum schedulerState;

  InstanceStateTypeEnum(
      String code, String value, String notes, SchedulerStateEnum schedulerState) {
    this.code = code;
    this.value = value;
    this.notes = notes;
    this.schedulerState = schedulerState;
  }

  public static InstanceStateTypeEnum fromValue(String code) {
    for (InstanceStateTypeEnum b : InstanceStateTypeEnum.values()) {
      if (b.code.equals(code)) {
        return b;
      }
    }
    return null;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public String getNotes() {
    return notes;
  }

  public SchedulerStateEnum getSchedulerState() {
    return schedulerState;
  }
}
