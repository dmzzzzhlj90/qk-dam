
package com.qk.dm.datacollect.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * task instance
 */

public class TaskInstanceDTO implements Serializable {
    private Boolean taskComplete;
    private String taskDefine;
    private Boolean firstRun;
    private Long dryRun;
    private String flag;
    private Long environmentCode;
    private String processInstance;
    private Long pid;
    private String taskParams;
    private String duration;
    private String processDefine;
    private String appLink;
    private String taskType;
    private Long taskCode;
    private String taskInstancePriority;
    private String logPath;
    private Boolean switchTask;
    private String host;
    private Date startTime;
    private String environmentConfig;
    private Long id;
    private String state;
    private String workerGroup;
    private Boolean conditionsTask;
    private String processInstancePriority;
    private Long processInstanceId;
    private TaskDependency dependency;
    private Long executorId;
    private String alertFlag;
    private String dependentResult;
    private String executePath;
    private Date firstSubmitTime;
    private TaskSwitchDependency switchDependency;
    private String resources;
    private Long maxRetryTimes;
    private Long retryTimes;
    private String executorName;
    private Boolean subProcess;
    private String varPool;
    private Date submitTime;
    private Boolean dependTask;
    private String name;
    private Long taskDefinitionVersion;
    private Long delayTime;
    private Long retryInterval;
    private Date endTime;
    private String processInstanceName;



}
