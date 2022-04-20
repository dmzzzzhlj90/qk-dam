package com.qk.dm.datamodel.params.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 物理模型表关系
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelPhysicalRelationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 物理表id(基础信息id)
     */
    private Long tableId;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 子表名称
     */
    private String childTableName;

    /**
     * 子表字段
     */
    private String childTableColumn;

    /**
     * 子表关联方式  1对多 1对1 多对多等
     */
    private String childConnectionWay;

    /**
     * 父表名称
     */
    private String fatherTableName;

    /**
     * 父表关联字段
     */
    private String fatherTableColumn;

    /**
     * 父表关联方式  1对多 1对1 多对多等
     */
    private String fatherConnectionWay;

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
