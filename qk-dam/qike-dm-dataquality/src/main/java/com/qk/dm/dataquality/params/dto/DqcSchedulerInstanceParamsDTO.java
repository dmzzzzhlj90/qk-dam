package com.qk.dm.dataquality.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

/**
 * @author shenpj
 * @date 2021/12/4 4:36 下午
 * @since 1.0.0
 */
@Data
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
     * 搜索值
     */
    String searchVal;
}
