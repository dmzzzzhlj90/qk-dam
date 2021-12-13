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
public class DqcSchedulerInstanceParamsDTO {

    Pagination pagination;

    /**
     * 流程定义ID
     */
    Integer processDefinitionId;

    /**
     * 开始时间
     */
    String startDate;

    /**
     * 结束时间
     */
    String endDate;

    /**
     * 搜索值(名称)
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
     * 分类目录
     */
    String dirId;
}
