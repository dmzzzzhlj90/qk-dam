package com.qk.dm.datamodel.params.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 关系建模字段配置入参类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelPhysicalColumnDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 物理表id(基础表id)
     */
    private Long tableId;

    /**
     * 字段名称
     */
    @NotBlank(message = "字段名称")
    private String columnName;

    /**
     * 字段类型
     */
    @NotBlank(message = "字段类型")
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
    @NotBlank(message = "是否是主键")
    private String itsPrimaryKey;

    /**
     * 是否分区 0否 1是
     */
    @NotBlank(message = "是否分区")
    private String itsPartition;

    /**
     * 是否为空 0否 1是
     */
    @NotBlank(message = "是否为空")
    private String itsNull;

    /**
     * 描述
     */
    private String description;

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
