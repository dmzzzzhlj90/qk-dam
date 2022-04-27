package com.qk.dm.datacollect.vo;

import com.qk.dm.datacollect.dolphin.dto.InstanceStateTypeEnum;
import lombok.Data;

import java.util.Objects;

/** @author shenpengjie */
@Data
public class ProcessInstanceVO {
  private Long id;
  /** 流程code */
  private Long processDefinitionCode;
  /** 状态 */
  private String state;
  /** 容错标示 */
  private String recovery;
  /** 开始时间  */
  private String startTime;
  /** 结束时间  */
  private String endTime;
  /** 运行次数 */
  private Long runTimes;
  /** 工作流名称 */
  private String name;
  /** 机器地址 */
  private String host;
  /** 运行类型 */
  private String commandType;
  /** 执行用户 */
  private String executorName;
  /** 运行时长 */
  private String duration;

  /** 状态名称 */
  String stateName;

  private String dependenceScheduleTimes;

  public void setState(String state) {
    this.state = state;
    this.stateName = Objects.requireNonNull(InstanceStateTypeEnum.fromValue(state)).getValue();
  }
}
