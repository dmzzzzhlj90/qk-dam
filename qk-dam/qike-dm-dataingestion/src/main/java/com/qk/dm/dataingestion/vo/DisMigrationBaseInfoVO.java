package com.qk.dm.dataingestion.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisMigrationBaseInfoVO {

    private Long id;

    /**
     * 规则分类id
     */
    private Long ruleClassId;

    /**
     * 作业id
     */
    private String jobId;

    /**
     * 作业名称
     */
    private String jobName;

    /**
     * 源连接
     */
    private String sourceConnect;

    /**
     * 源数据库
     */
    private String sourceDatabase;

    /**
     * 源表
     */
    private String sourceTable;

    /**
     * 目标连接
     */
    private String targetConnect;

    /**
     * 目标数据库
     */
    private String targetDatabase;

    /**
     * 表不存在时是否自动创建表
     */
    private String autoCreate;

    /**
     * 目标表
     */
    private String targetTable;

    /**
     * 导入前类型（导入前是否清除数据）
     */

    private String beforImportType;

    /**
     * 主键或分区字段
     */
    private String primaryField;

    /**
     * 耗时（单位秒）
     */
    private Integer timeConsuming;

    /**
     * 待迁移数量
     */
    private Integer waitNumber;

    /**
     * 迁移中数量
     */
    private Integer migrationNumber;

    /**
     * 状态 0未运行 1运行中2运行成功3失败
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUserid;

    /**
     * 修改人
     */
    private String updateUserid;

    /**
     * 常见时间
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
