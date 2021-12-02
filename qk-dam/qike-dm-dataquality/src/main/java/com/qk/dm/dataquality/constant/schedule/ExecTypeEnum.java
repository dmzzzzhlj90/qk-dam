package com.qk.dm.dataquality.constant.schedule;

/**
 * 指令类型
 * @author shenpengjie
 */
public enum ExecTypeEnum {
  NULL(0,null,"空"),
  START_PROCESS(1,"START_PROCESS",""),
  START_CURRENT_TASK_PROCESS(2,"START_CURRENT_TASK_PROCESS",""),
  RECOVER_TOLERANCE_FAULT_PROCESS(3,"RECOVER_TOLERANCE_FAULT_PROCESS",""),
  RECOVER_SUSPENDED_PROCESS(4,"RECOVER_SUSPENDED_PROCESS",""),
  START_FAILURE_TASK_PROCESS(5,"START_FAILURE_TASK_PROCESS",""),
  COMPLEMENT_DATA(6,"COMPLEMENT_DATA",""),
  SCHEDULER(7,"SCHEDULER",""),
  REPEAT_RUNNING(8,"REPEAT_RUNNING",""),
  PAUSE(9,"PAUSE",""),
  STOP(10,"STOP",""),
  RECOVER_WAITTING_THREAD(11,"RECOVER_WAITTING_THREAD","");

  Integer code;
  String value;
  String name;

  ExecTypeEnum(Integer code, String value, String name) {
    this.code = code;
    this.value = value;
    this.name = name;
  }

  public static ExecTypeEnum fromValue(Integer code) {
    for (ExecTypeEnum b : ExecTypeEnum.values()) {
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
