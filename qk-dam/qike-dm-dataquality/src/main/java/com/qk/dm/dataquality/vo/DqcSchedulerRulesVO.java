package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 数据质量_规则调度_规则信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerRulesVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 作业id
     */
    private String taskId;

    /**
     * 规则模版id
     */
    private Long ruleTempId;

    /**
     * 规则类型 1-库级 2-表级 3-字段级
     */
    private Integer ruleType;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    private String engineType;

    /**
     * 数据连接地址
     */
    private String databaseUrl;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 扫描范围 0-全表 1-条件
     */
    private Integer scanType;

    /**
     * 扫描条件SQL
     */
    private String scanSql;

    /**
     * 告警表达式
     */
    private String warnExpression;

    /**
     * 创建人
     */
    private Long createUserid;

    /**
     * 修改人
     */
    private Long updateUserid;

    /**
     * 删除标识(0-保留 1-删除)
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

}
