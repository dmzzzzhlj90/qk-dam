
package com.qk.dm.dataquality.dolphinapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    private boolean taskComplete;
    private String taskDefine;
    private boolean firstRun;
    private int dryRun;
    private String flag;
    private int environmentCode;
    private String processInstance;
    private int pid;
    private String taskParams;
    private String duration;
    private String processDefine;
    private String appLink;
    private String taskType;
    private long taskCode;
    private String taskInstancePriority;
    private String logPath;
    private boolean switchTask;
    private String host;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    private String environmentConfig;
    private int id;
    private String state;
    private String workerGroup;
    private boolean conditionsTask;
    private String processInstancePriority;
    private int processInstanceId;
    private TaskDependency dependency;
    private int executorId;
    private String alertFlag;
    private String dependentResult;
    private String executePath;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date firstSubmitTime;
    private TaskSwitchDependency switchDependency;
    private String resources;
    private int maxRetryTimes;
    private int retryTimes;
    private String executorName;
    private boolean subProcess;
    private String varPool;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitTime;
    private boolean dependTask;
    private String name;
    private int taskDefinitionVersion;
    private int delayTime;
    private int retryInterval;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private String processInstanceName;



}
