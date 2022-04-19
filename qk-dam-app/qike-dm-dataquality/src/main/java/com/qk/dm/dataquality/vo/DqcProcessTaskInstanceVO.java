package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

/** @author shenpengjie */
@Data
public class DqcProcessTaskInstanceVO {
  private Long id;
  /** 流程id */
  private Long processDefinitionId;
  /** 实例id */
  private Long processInstanceId;
  /** 工作流名称 */
  private String processInstanceName;
  /** 任务实例名称 */
  private String name;
  /** 执行用户 */
  private String executorName;
  /** 节点类型 */
  private String taskType;
  /** 状态 */
  private String state;
  /** 提交时间  */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date submitTime;
  /** 开始时间  */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date startTime;
  /** 结束时间  */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endTime;
  /** 机器地址 */
  private String host;
  /** 运行时长 */
  private String duration;
  /** 重试次数 */
  private int retryTimes;

  /** 状态名称 */
  String stateName;

  public void setState(String state) {
    this.state = state;
    this.stateName = Objects.requireNonNull(InstanceStateTypeEnum.fromValue(state)).getValue();
  }
}
