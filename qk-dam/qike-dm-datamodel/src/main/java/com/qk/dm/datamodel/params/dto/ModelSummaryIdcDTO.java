package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModelSummaryIdcDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 汇总表id
     */
    private Long summaryId;

    /**
     * 指标类型
     */
    private Integer indicatorsType;

    /**
     * 指标名称
     */
    private String indicatorsName;

    /**
     * 指标编码
     */
    private String indicatorsCode;

    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 是否为空 0为空 1不为空
     */
    private String itsNull;

    /**
     * 描述
     */
    private String desc;

}
