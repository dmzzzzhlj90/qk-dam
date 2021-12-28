package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.util.Map;

/**
 * 工作流实例_任务节点信息
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
@Data
public class TaskDefinitionDTO {

    /**
     * task node code
     */
    private Long code;

    /**
     * task node name
     */
    private String name;

    /**
     * task node version
     */
    private Integer version;


    /**
     * task node description
     */
    private String description;

    /**
     * task node taskType
     */
    private String taskType;

    /**
     * params information
     */
    private TaskParamsDTO taskParams;

    /**
     * task node flag
     */
    private String flag;

    /**
     * task node taskPriority
     */
    private String taskPriority;

    /**
     * task node workerGroup
     */
    private String workerGroup;

    /**
     * task node failRetryTimes
     */
    private Integer failRetryTimes;

    /**
     * task node failRetryInterval
     */
    private Integer failRetryInterval;

    /**
     * task node timeoutFlag
     */
    private String timeoutFlag;

    /**
     * task node timeoutNotifyStrategy
     */
    private String timeoutNotifyStrategy;

    /**
     * task node timeout
     */
    private Integer timeout;

    /**
     * task node delayTime
     */
    private Integer delayTime;

    /**
     * task node environmentCode
     */
    private Integer environmentCode;

}