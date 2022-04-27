package com.qk.dm.datacollect.dolphin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shenpj
 * @date 2021/12/7 6:02 下午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskInstanceSearchDTO implements Serializable {
    /**
     * queryTaskListPaging
     * QUERY_TASK_INSTANCE_LIST_PAGING_NOTES
     *
     * @param projectName 项目名称 (required)
     * @param endDate 结束时间 (optional)
     * @param executorName EXECUTOR_NAME (optional)
     * @param host 运行任务的主机IP地址 (optional)
     * @param pageNo 页码号 (optional)
     * @param pageSize 页大小 (optional)
     * @param processInstanceId 流程实例ID (optional)
     * @param searchVal 搜索值 (optional)
     * @param startDate 开始时间 (optional)
     * @param stateType 工作流和任务节点的运行状态 (optional)
     * @param taskName 任务实例名 (optional)
     * @return Result
     */
    /**
     * queryTaskListPaging
     * 分页查询任务实例列表
     * @param pageNo 页码号 (required)
     * @param pageSize 页大小 (required)
     * @param projectCode PROJECT_CODE (required)
     * @param endDate 结束时间 (optional)
     * @param executorName 流程名称 (optional)
     * @param host 运行任务的主机IP地址 (optional)
     * @param processInstanceId 流程实例ID (optional)
     * @param processInstanceName 流程实例名称 (optional)
     * @param searchVal 搜索值 (optional)
     * @param startDate 开始时间 (optional)
     * @param stateType 工作流和任务节点的运行状态 (optional)
     * @param taskName 任务实例名 (optional)
     */
    Integer pageNo;
    Integer pageSize;
    String endDate;
    String executorName;
    String host;
    Integer processInstanceId;
    String processInstanceName;
    String searchVal;
    String startDate;
    String stateType;
    String taskName;
}
