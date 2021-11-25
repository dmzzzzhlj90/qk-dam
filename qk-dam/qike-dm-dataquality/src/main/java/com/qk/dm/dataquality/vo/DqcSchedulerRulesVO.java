package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String jobId;

    /**
     * 规则模版id
     */
    private Long ruleTempId;

    /**
     * 规则类型 "RULE_TYPE_FIELD", "字段级别规则","RULE_TYPE_TABLE", "表级别规则","RULE_TYPE_DB", "库级别规则";
     */
    private String ruleType;

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
     * 规则类型 "RULE_TYPE_FIELD", "字段级别规则","RULE_TYPE_TABLE", "表级别规则","RULE_TYPE_DB", "库级别规则";
     */
    private String scanType;

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
    private String createUserid;

    /**
     * 修改人
     */
    private String updateUserid;

    /**
     * 删除标识(0-保留 1-删除)
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

}
