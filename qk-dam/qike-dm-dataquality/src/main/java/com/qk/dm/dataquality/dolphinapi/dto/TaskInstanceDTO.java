
package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * task instance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskInstanceDTO implements Serializable {
    private int id;
    private boolean taskComplete;
    private String flag;
    private String processInstance;
    private int pid;
    private String duration;
    private String processDefine;
    private String appLink;
    private String taskType;
    private String taskInstancePriority;
    private String logPath;
    private String host;
    private Date startTime;
    private String state;
    private String workerGroup;
    private boolean conditionsTask;
    private String processInstancePriority;
    private int processDefinitionId;
    private int processInstanceId;
    private String dependency;
    private int executorId;
    private String alertFlag;
    private String dependentResult;
    private String executePath;
    private String resources;
    private int maxRetryTimes;
    private int retryTimes;
    private String executorName;
    private boolean subProcess;
    private Date submitTime;
    private boolean dependTask;
    private String name;
    private int retryInterval;
    private Date endTime;
    private String processInstanceName;



}
