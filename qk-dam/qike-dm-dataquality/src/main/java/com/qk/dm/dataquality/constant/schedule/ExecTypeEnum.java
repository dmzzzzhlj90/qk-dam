package com.qk.dm.dataquality.constant.schedule;

/**
 * 指令类型
 * @author shenpengjie
 */

public enum ExecTypeEnum {
  START_PROCESS,
  START_CURRENT_TASK_PROCESS,
  RECOVER_TOLERANCE_FAULT_PROCESS,
  RECOVER_SUSPENDED_PROCESS,
  START_FAILURE_TASK_PROCESS,
  COMPLEMENT_DATA,
  SCHEDULER,
  REPEAT_RUNNING,
  PAUSE,
  STOP,
  RECOVER_WAITTING_THREAD;
}
