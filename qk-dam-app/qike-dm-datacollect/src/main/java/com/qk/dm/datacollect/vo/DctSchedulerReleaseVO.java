package com.qk.dm.datacollect.vo;

import com.qk.datacenter.model.ProcessDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2022/4/21 21:42
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DctSchedulerReleaseVO {
    Long processDefinitionCode;
    ProcessDefinition.ReleaseStateEnum releaseState;

    public void setReleaseState(String releaseState) {
        this.releaseState = ProcessDefinition.ReleaseStateEnum.fromValue(releaseState);
    }
}
