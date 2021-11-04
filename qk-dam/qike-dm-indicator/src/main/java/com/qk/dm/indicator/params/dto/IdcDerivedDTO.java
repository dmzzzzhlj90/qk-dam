package com.qk.dm.indicator.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdcDerivedDTO {


    /**
     * 衍生指标名称
     */
    @NotBlank(message = "衍生指标名称不能为空！")
    private String derivedIndicatorName;

    /**
     * 衍生指标编码
     */
    private String derivedIndicatorCode;

    /**
     * 数据表
     */
    @NotBlank(message = "数据表不能为空！")
    private String dataSheet;

    /**
     * 所属主题
     */
    @NotBlank(message = "主题不能为空！")
    private String themeCode;

    /**
     * 原子指标
     */
    @NotBlank(message = "原子指标不能为空！")
    private String atomIndicatorCode;

    /**
     * 时间限定
     */
    private String timeLimit;

    /**
     * 关联的字段
     */
    private String associatedFields;

    /**
     * 通用限定
     */
    private String generalLimit;

    /**
     * 指标状态  0草稿 1已上线 2已下线
     */
    private Integer indicatorStatus;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * sql语句
     */
    private String sqlSentence;
}
