package com.qk.dm.datacollect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据质量_规则调度_基础信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DctSchedulerInfoVO {

    /**
     * 调度流程实例code,修改时必填
     */
    private Long code;

    /**
     * 作业名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private String releaseState;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 创建人
     */
    private String userName;
}
