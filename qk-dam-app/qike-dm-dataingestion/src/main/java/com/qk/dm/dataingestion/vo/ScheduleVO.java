package com.qk.dm.dataingestion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleVO {
    private Integer id;
    private Long environmentCode;
    private Long processDefinitionCode;
    private String processDefinitionName;
    private Long processDefinitionId;
    private String projectName;
    private String definitionDescription;
    private String startTime;
    private String endTime;
    private String timezoneId;
    private String crontab;
    private String failureStrategy;
    private String warningType;
    private String createTime;
    private String updateTime;
    private Long userId;
    private String userName;
    private String releaseState;
    private Long warningGroupId;
    private String processInstancePriority;
    private String workerGroup;
}
