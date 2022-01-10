package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModelDimTableColumnDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 维度表id
     */
    private Long dimTableId;

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
    private String itsPrimaryKey;

    /**
     * 是否分区 0否 1是
     */
    private String itsPartition;

    /**
     * 是否为空 0否 1是
     */
    private String itsNull;

    /**
     * 描述
     */
    private String description;


}