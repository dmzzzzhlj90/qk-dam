package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 定时信息
 * @author shenpj
 * @date 2021/11/26 4:16 下午
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO implements Serializable {
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
