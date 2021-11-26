package com.qk.dm.dataquality.dolphinapi.builder;

import lombok.Data;

/**
 * 定时信息
 * @author shenpengjie
 */
@Data
public class ScheduleData {
  private Integer id;
  private String createTime;
  private String crontab;
  private String definitionDescription;
  private String endTime;
  private String failureStrategy;
  private Integer processDefinitionId;
  private String processDefinitionName;
  private String processInstancePriority;
  private String projectName;
  private String releaseState;
  private String startTime;
  private String updateTime;
  private Integer userId;
  private String userName;
  private Integer warningGroupId;
  private String warningType;
  private String workerGroup;
}
