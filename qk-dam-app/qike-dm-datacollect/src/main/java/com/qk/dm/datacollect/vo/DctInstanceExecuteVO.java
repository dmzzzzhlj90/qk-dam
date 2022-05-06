package com.qk.dm.datacollect.vo;

import com.qk.datacenter.model.ProcessInstance;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2021/12/6 2:36 下午
 * @since 1.0.0
 */
@Data
public class DctInstanceExecuteVO {

    /**
     * 执行类型，REPEAT_RUNNING-重跑 STOP-停止 PAUSE-暂停
     * START_FAILURE_TASK_PROCESS-失败后恢复
     */
    @NotNull(message = "执行类型不能为空！")
    ProcessInstance.CmdTypeIfComplementEnum executeType;

    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空！")
    Integer instanceId;
}
