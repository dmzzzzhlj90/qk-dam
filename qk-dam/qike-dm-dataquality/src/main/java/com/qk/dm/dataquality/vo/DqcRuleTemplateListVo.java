package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class DqcRuleTemplateListVo {

    private Long id;

    /**
     * 模板名称
     */
    private String tempName;

    /**
     * 模板类型1-系统内置 2-自定义
     */
    private Integer tempType;

    /**
     * 分类目录
     */
    private Long dirId;

    /**
     * 质量纬度id
     */
    private Integer dimensionId;

    /**
     * 质量维度
     */
    private String dimension;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    private String engineType;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空！")
    private String description;

    /**
     * 模板sql
     */
    @NotBlank(message = "模板sql不能为空！")
    private String tempSql;

    /**
     * 结果定义
     */
    private String tempResult;

    /**
     * 发布状态 -1-下线 0-草稿 1-发布
     */
    private Integer publishState;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;
}
