package com.qk.dm.dataquality.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据质量_规则结果集入参对象
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerResultParamsVO {

    private Pagination pagination;

    /**
     * 作业名称
     */
    private String jobId;

    /**
     * 作业名称
     */
    private String jobName;

    /**
     * 规则id
     */
    private String ruleId;

    /**
     * 工作流实例task_code绑定规则
     */
    private Long taskCode;

    /**
     * 开始时间
     */
    private String beginDay;

    /**
     * 结束时间
     */
    private String endDay;
}
