package com.qk.dm.datacollect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class DctSchedulerBasicInfoVO {

    /**
     * 调度流程实例code,修改时必填
     */
    private Long processDefinitionCode;

    /**
     * 作业名称
     */
    @NotBlank(message = "作业名称不能为空！")
    private String jobName;

    /**
     * 分类目录
     */
    @NotNull(message = "分类目录不能为空！")
    private String dirId;

    /**
     * 描述
     */
    private String description;

    /**
     * 调度_规则信息
     */
    @Valid
    private DctSchedulerRulesVO schedulerRules;

    /**
     * 调度_配置信息
     */
    @Valid
    private DctSchedulerConfigVO schedulerConfig;


//
//    /**
//     * 调度状态 "OFFLINE":"下线","ONLINE":"上线"
//     */
//    private String schedulerState;
//
//    /**
//     * 运行实例状态 0-初始状态 1-运行中 2-停止 3-成功 4-失败
//     */
//    private Integer runInstanceState;
}
