package com.qk.dm.datacollect.vo;

import com.qk.datacenter.model.ProcessDefinition;

/**
 * @author shenpj
 * @date 2022/4/21 21:42
 * @since 1.0.0
 */
public class DctSchedulerReleaseVO {
    Long processDefinitionCode;
    ProcessDefinition.ReleaseStateEnum releaseState;

    public Long getProcessDefinitionCode() {
        return processDefinitionCode;
    }

    public void setProcessDefinitionCode(Long processDefinitionCode) {
        this.processDefinitionCode = processDefinitionCode;
    }

    public ProcessDefinition.ReleaseStateEnum getReleaseState() {
        return releaseState;
    }

    public void setReleaseState(String releaseState) {
        this.releaseState = ProcessDefinition.ReleaseStateEnum.fromValue(releaseState);
    }
}
