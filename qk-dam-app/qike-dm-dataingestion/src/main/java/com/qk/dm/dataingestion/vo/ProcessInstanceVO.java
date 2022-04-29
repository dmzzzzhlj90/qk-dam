package com.qk.dm.dataingestion.vo;

import com.qk.dm.dataingestion.enums.IngestionStatusType;
import lombok.Data;


@Data
public class ProcessInstanceVO {
    private Long processDefinitionCode;
    private Long processDefinitionVersion;
    private String state;
    private String recovery;
    private String startTime;
    private String endTime;
    private Long runTimes;
    private String name;
    private String host;
    private String processDefinition;
    private String commandType;
    private String commandParam;
    private String taskDependType;
    private Long maxTryTimes;
    private String failureStrategy;
    private String warningType;
    private String warningGroupId;
    private String scheduleTime;
    private String commandStartTime;
    private String globalParams;
    private String dagData;
    private Long executorId;
    private String executorName;
    private String tenantCode;
    private String queue;
    private String isSubProcess;
    private String locations;
    private String historyCmd;
    private String dependenceScheduleTimes;
    private String duration;
    private String processInstancePriority;
    private String workerGroup;
    private String environmentCode;
    private Long timeout;
    private Long tenantId;
    private String varPool;
    private Long dryRun;
    private String cmdTypeIfComplement;
    private Boolean complementData;
    private Boolean processInstanceStop;
    private Integer status;

    public void setState(String state) {
        this.state = state;
        this.status = IngestionStatusType.getVal(state).getIngestionStatus().getCode();;
    }
}
