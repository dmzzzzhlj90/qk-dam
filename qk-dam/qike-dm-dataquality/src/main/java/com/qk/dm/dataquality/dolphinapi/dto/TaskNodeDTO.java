package com.qk.dm.dataquality.dolphinapi.dto;

import com.qk.dm.dataquality.dolphinapi.constant.Priority;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 工作流实例_任务节点信息
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
@Data
public class TaskNodeDTO {

    /**
     * task node id
     */
    private String id;

    /**
     * task node name
     */
    private String name;

    /**
     * task node description
     */
    private String description;

    /**
     * task node type
     */
    private String type;

    /**
     * the run flag has two states, NORMAL or FORBIDDEN
     */
    private String runFlag;

//    /**
//     * the front field
//     */
//    private String loc;

    /**
     * maximum number of retries
     */
    private int maxRetryTimes;

    /**
     * Unit of retry interval: points
     */
    private int retryInterval;

    /**
     * params information
     */
    private ShellParameters params;

    /**
     * inner dependency information
     */
    private List<String> preTasks;

//    /**
//     * users store additional information
//     */
//    private String extras;

//    /**
//     * node dependency list
//     */
//    private List<String> depList;

    /**
     * outer dependency information
     */
    private Map<String, String> dependence;


    private ConditionResultDTO conditionResult;

    /**
     * task instance priority
     */
    private Priority taskInstancePriority;

    /**
     * worker group
     */
    private String workerGroup;

//    /**
//     * worker group id
//     */
//    private Integer workerGroupId;


    /**
     * task time out
     */
    private TimeOutDTO timeout;

}