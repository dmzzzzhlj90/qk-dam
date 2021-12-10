package com.qk.dm.dataquality.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

/**
 * 数据质量_调度结果集信息(列表)
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerResultVO {

    /**
     * 作业名称
     */
    private String jobName;

    /**
     * 规则id
     */
    private String ruleId;


    /**
     * 数据质量_调度结果集_定义结果(表头)
     */
    private List<DqcSchedulerResultTitleVO> resultTitleList;

    /**
     * 数据质量_调度结果集信息(列表)
     */
    private List<Map<String, Object>> resultDataList;

}