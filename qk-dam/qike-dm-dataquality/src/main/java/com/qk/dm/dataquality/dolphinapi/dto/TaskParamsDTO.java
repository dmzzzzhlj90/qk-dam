package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 工作流实例_执行参数信息
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class TaskParamsDTO {

    /**
     * taskParams resourceList
     */
    private List<ResourceDTO> resourceList;

    /**
     * taskParams localParams
     */
    private List<String> localParams;

    /**
     * taskParams rawScript
     */
    private String rawScript;

    /**
     * taskParams dependence
     */
    private Map<String, String> dependence;

    /**
     * taskParams conditionResult
     */
    private ConditionResultDTO conditionResult;


    /**
     * taskParams waitStartTimeout
     */
    private Map<String, String> waitStartTimeout;

    /**
     * taskParams switchResult
     */
    private Map<String, String> switchResult;

}