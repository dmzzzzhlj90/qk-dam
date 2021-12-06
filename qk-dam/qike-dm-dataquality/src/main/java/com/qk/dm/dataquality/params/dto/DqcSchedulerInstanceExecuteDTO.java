package com.qk.dm.dataquality.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2021/12/6 2:36 下午
 * @since 1.0.0
 */
@Data
public class DqcSchedulerInstanceExecuteDTO {

    /**
     * 执行类型，可用值:NONE,REPEAT_RUNNING,RECOVER_SUSPENDED_PROCESS,START_FAILURE_TASK_PROCESS,STOP,PAUSE
     */
    @NotNull(message = "执行类型不能为空！")
    String executeType;

    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空！")
    Integer processInstanceId;
}
