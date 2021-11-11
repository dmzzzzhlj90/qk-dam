package com.qk.dm.dataquality.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据质量_调度规则查询入参对象
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerRulesParamsVO {

    private Pagination pagination;

    /**
     * 作业id
     */
    private String taskId;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    private String engineType;

    /**
     * 规则类型 1-库级 2-表级 3-字段级
     */
    private Integer ruleType;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 开始时间
     */
    private String beginDay;

    /**
     * 结束时间
     */
    private String endDay;
}
