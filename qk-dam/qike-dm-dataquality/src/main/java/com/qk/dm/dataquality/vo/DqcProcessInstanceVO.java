package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

/** @author shenpengjie */
@Data
public class DqcProcessInstanceVO {
  private int id;
  /** 流程id */
  private int processDefinitionId;
  /** 状态 */
  private String state;
  /** 容错标示 */
  private String recovery;
  /** 开始时间  */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date startTime;
  /** 结束时间  */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endTime;
  /** 运行次数 */
  private int runTimes;
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
