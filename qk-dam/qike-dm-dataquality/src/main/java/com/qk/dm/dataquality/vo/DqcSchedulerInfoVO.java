package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 数据质量_规则调度信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerInfoVO {

    /**
     * 数据质量_规则调度_基础信息
     */
    private DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO;

    /**
     * 数据质量_规则调度_规则信息VO
     */
    private List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList;

    /**
     * 数据质量_规则调度_配置信息
     */
    private DqcSchedulerConfigVO dqcSchedulerConfigVO;


}
