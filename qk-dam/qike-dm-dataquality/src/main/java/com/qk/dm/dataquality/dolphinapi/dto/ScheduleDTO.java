package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 定时信息
 * @author shenpj
 * @date 2021/11/26 4:16 下午
 * @since 1.0.0
 */
@Data
public class ScheduleDTO implements Serializable {
    private int id;
    private int processDefinitionId;
    private String processDefinitionName;
    private String projectName;
    private String definitionDescription;
    private String startTime;
    private String endTime;
    private String crontab;
    private String failureStrategy;
    private String warningType;
    private String createTime;
    private String updateTime;
    private int userId;
    private String userName;
    private String releaseState;
    private int warningGroupId;
    private String processInstancePriority;
    private String workerGroup;
}
