package com.qk.dm.dataquality.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2021/12/4 4:36 下午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerTaskInstanceParamsDTO {

    Pagination pagination;

    /**
     * 流程实例ID
     */
    Integer processInstanceId;

    /**
     * 开始时间
     */
    String startDate;

    /**
     * 结束时间
     */
    String endDate;

    /**
     * 搜索值(名称：模糊搜索)
     */
    String searchVal;

    /**
     * 工作流和任务节点的运行状态
     */
    String stateType;

    /**
     * 执行用户
     */
    String executorName;

    /**
     * 任务实例名(名称：精确搜索)
     */
    String taskName;
}
