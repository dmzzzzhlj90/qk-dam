package com.qk.dm.dataquality.constant.schedule;

/**
 * 指令类型
 * @author shenpengjie
 */
public enum ExecTypeEnum {
  START_PROCESS("START_PROCESS",""),
  START_CURRENT_TASK_PROCESS("START_CURRENT_TASK_PROCESS",""),
  RECOVER_TOLERANCE_FAULT_PROCESS("RECOVER_TOLERANCE_FAULT_PROCESS",""),
  RECOVER_SUSPENDED_PROCESS("RECOVER_SUSPENDED_PROCESS",""),
  START_FAILURE_TASK_PROCESS("START_FAILURE_TASK_PROCESS",""),
  COMPLEMENT_DATA("COMPLEMENT_DATA",""),
  SCHEDULER("SCHEDULER",""),
  REPEAT_RUNNING("REPEAT_RUNNING",""),
  PAUSE("PAUSE",""),
  STOP("STOP",""),
  RECOVER_WAITTING_THREAD("RECOVER_WAITTING_THREAD","");

  String code;
  String name;

  ExecTypeEnum(String code,  String name) {
    this.code = code;
    this.name = name;
  }

  public static ExecTypeEnum fromValue(String code) {
    for (ExecTypeEnum b : ExecTypeEnum.values()) {
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
