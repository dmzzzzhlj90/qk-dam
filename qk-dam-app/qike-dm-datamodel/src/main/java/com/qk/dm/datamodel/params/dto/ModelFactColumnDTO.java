package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ModelFactColumnDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事实id
     */
    private Long factId;
    /**
     * 维度id,添加字段为维度时，对应维度id
     */
    private Long dimId;

    /**
     * 属性类型 0 维度 1度量
     */
    private Integer attributeType;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 标准id
     */
    private Long standardsId;

    /**
     * 标准名称
     */
    private String standardsName;

    /**
     * 是否是主键 0否 1是
     */
    private Integer itsPrimaryKey;

    /**
     * 是否分区 0否 1是
     */
    private Integer itsPartition;

    /**
     * 是否为空 0否 1是
     */
    private Integer itsNull;

    /**
     * 描述
     */
    private String description;



}
