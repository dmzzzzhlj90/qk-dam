package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    private Date startTime;
    private Date endTime;
    private String crontab;
    private String failureStrategy;
    private String warningType;
    private Date createTime;
    private Date updateTime;
    private int userId;
    private String userName;
    private String releaseState;
    private int warningGroupId;
    private String processInstancePriority;
    private String workerGroup;
}
