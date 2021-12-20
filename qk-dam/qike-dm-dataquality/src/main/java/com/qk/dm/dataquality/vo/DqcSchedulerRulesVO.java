package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

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
     * 规则id
     */
    @NotBlank(message = "规则id不能为空！")
    private String ruleId;

    /**
     * 规则名称
     */
    @NotBlank(message = "规则名称不能为空！")
    private String ruleName;

    /**
     * 作业id
     */
    @NotBlank(message = "作业id不能为空！")
    private String jobId;

    /**
     * 规则模版id
     */
    @NotBlank(message = "规则模版id不能为空！")
    private Long ruleTempId;

    /**
     * 规则类型 "RULE_TYPE_FIELD":"字段级别规则","RULE_TYPE_TABLE":"表级别规则","RULE_TYPE_DB":"库级别规则";
     */
    @NotBlank(message = "规则类型不能为空！")
    private String ruleType;

    /**
     * 适用引擎 MYSQL HIVE ORACLE
     */
    @NotBlank(message = "适用引擎不能为空！")
    private String engineType;

    /**
     * 数据连接
     */
    @NotBlank(message = "数据连接不能为空！")
    private String dataSourceName;

    /**
     * 数据库名称
     */
    @NotBlank(message = "数据库名称不能为空！")
    private String databaseName;

    /**
     * 表名称
     */
    private List<String> tableList;

    /**
     * 字段名称
     */
    private List<String> fieldList;

    /**
     * 扫描范围 "FULL_TABLE":"全表", "CONDITION":"条件";
     */
    @NotBlank(message = "主题不能为空！")
    private String scanType;

    /**
     * 扫描条件SQL
     */
    private String scanSql;

    /**
     * 执行sql
     */
    private String executeSql;

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
