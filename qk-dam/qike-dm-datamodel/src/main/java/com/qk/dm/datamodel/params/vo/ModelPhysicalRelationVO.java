package com.qk.dm.datamodel.params.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物理模型表关系
 */
@Data
public class ModelPhysicalRelationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 物理表id
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
     * 修改时间
     */
    private Date gmtModified;
}
