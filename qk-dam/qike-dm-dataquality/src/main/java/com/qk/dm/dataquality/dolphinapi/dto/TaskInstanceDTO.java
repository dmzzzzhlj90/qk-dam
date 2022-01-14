
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
    private Boolean taskComplete;
    private String taskDefine;
    private Boolean firstRun;
    private Integer dryRun;
    private String flag;
    private Integer environmentCode;
    private String processInstance;
    private Integer pid;
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
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    private String environmentConfig;
    private Integer id;
    private String state;
    private String workerGroup;
    private Boolean conditionsTask;
    private String processInstancePriority;
    private Integer processInstanceId;
    private TaskDependency dependency;
    private Integer executorId;
    private String alertFlag;
    private String dependentResult;
    private String executePath;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date firstSubmitTime;
    private TaskSwitchDependency switchDependency;
    private String resources;
    private Integer maxRetryTimes;
    private Integer retryTimes;
    private String executorName;
    private Boolean subProcess;
    private String varPool;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitTime;
    private Boolean dependTask;
    private String name;
    private Integer taskDefinitionVersion;
    private Integer delayTime;
    private Integer retryInterval;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private String processInstanceName;



}
