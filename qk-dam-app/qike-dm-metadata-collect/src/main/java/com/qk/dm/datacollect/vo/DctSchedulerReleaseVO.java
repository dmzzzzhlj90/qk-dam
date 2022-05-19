package com.qk.dm.datacollect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
    @NotNull(message = "code为必填项")
    Long code;
    /**
     * 状态：ONLINE-上线 OFFLINE-下线
     */
    @NotNull(message = "状态为必填项")
    String releaseState;

//    public void setReleaseState(String releaseState) {
//        this.releaseState = ProcessDefinition.ReleaseStateEnum.fromValue(releaseState);
//    }
}
