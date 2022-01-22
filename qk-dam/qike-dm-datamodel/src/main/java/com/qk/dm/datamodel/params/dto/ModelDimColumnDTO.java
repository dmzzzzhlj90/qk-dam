package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ModelDimColumnDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    /**
     * 字段名称
     */
    @NotNull(message = "字段名称不能为空")
    private String columnName;

    /**
     * 字段类型
     */
    @NotNull(message = "字段类型不能为空")
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
     * 标准编码
     */
    private String standardsCode;

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
