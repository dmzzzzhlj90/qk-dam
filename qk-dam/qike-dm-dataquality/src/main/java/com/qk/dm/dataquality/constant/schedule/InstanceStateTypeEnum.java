package com.qk.dm.dataquality.constant.schedule;

import com.qk.dm.dataquality.constant.SchedulerStateEnum;

/** @author shenpengjie */
public enum InstanceStateTypeEnum {
  // 调度运行状态
  SUBMITTED_SUCCESS("SUBMITTED_SUCCESS", "提交成功", SchedulerStateEnum.RUNING),
  RUNNING_EXECUTION("RUNNING_EXECUTION", "正在运行", SchedulerStateEnum.RUNING),
  READY_PAUSE("READY_PAUSE", "准备暂停", SchedulerStateEnum.RUNING),
  PAUSE("PAUSE", "暂停", SchedulerStateEnum.RUN_FAIL),
  READY_STOP("READY_STOP", "准备停止", SchedulerStateEnum.RUNING),
  STOP("STOP", "停止", SchedulerStateEnum.RUN_FAIL),
  FAILURE("FAILURE", "失败", SchedulerStateEnum.RUN_FAIL),
  SUCCESS("SUCCESS", "成功", SchedulerStateEnum.RUN_SUCCEED),
  NEED_FAULT_TOLERANCE("NEED_FAULT_TOLERANCE", "需要容错", SchedulerStateEnum.RUNING),
  KILL("KILL", "kill", SchedulerStateEnum.RUN_FAIL),
  WAITTING_THREAD("WAITTING_THREAD", "等待线程", SchedulerStateEnum.RUNING),
  WAITTING_DEPEND("WAITTING_DEPEND", "等待依赖完成", SchedulerStateEnum.RUNING);

  String code;
  String value;
  SchedulerStateEnum schedulerState;

  InstanceStateTypeEnum(String code, String value, SchedulerStateEnum schedulerState) {
    this.code = code;
    this.value = value;
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

  public SchedulerStateEnum getSchedulerState() {
    return schedulerState;
  }
}
