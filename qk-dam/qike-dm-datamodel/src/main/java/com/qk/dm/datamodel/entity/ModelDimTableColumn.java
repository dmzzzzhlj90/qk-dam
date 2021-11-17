package com.qk.dm.datamodel.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_model_dim_table_column")
public class ModelDimTableColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 维度表id
     */
    @Column(name = "dim_table_id", nullable = false)
    private Long dimTableId;

    /**
     * 字段名称
     */
    @Column(name = "column_name", nullable = false)
    private String columnName;

    /**
     * 字段类型
     */
    @Column(name = "column_type", nullable = false)
    private String columnType;

    /**
     * 标准id
     */
    @Column(name = "standards_id")
    private Long standardsId;

    /**
     * 标准名称
     */
    @Column(name = "standards_name")
    private String standardsName;

    /**
     * 是否是主键 0否 1是
     */
    @Column(name = "its_primary_key", nullable = false)
    private String itsPrimaryKey;

    /**
     * 是否分区 0否 1是
     */
    @Column(name = "its_partition", nullable = false)
    private String itsPartition;

    /**
     * 是否为空 0否 1是
     */
    @Column(name = "its_null", nullable = false)
    private String itsNull;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

}
