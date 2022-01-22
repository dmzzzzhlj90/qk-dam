package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "字段名称参数不能为空")
    private String columnName;

    /**
     * 字段类型
     */
    @NotBlank(message = "字段类型参数不能为空")
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
    @NotBlank(message = "itsPrimaryKey参数不能为空")
    private String itsPrimaryKey;

    /**
     * 是否分区 0否 1是
     */
    @NotBlank(message = "itsPartition参数不能为空")
    private String itsPartition;

    /**
     * 是否为空 0否 1是
     */
    @NotBlank(message = "itsNull参数不能为空")
    private String itsNull;

    /**
     * 描述
     */
    private String description;


}