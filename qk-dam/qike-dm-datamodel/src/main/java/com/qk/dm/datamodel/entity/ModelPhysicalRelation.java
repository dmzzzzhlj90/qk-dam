package com.qk.dm.datamodel.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_model_physical_relation")
@Where(clause = "del_flag = 0 ")
public class ModelPhysicalRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 物理表id
     */
    @Column(name = "table_id", nullable = false)
    private Long tableId;

    /**
     * 字段名称
     */
    @Column(name = "column_name", nullable = false)
    private String columnName;

    /**
     * 子表名称
     */
    @Column(name = "child_table_name", nullable = false)
    private String childTableName;

    /**
     * 子表字段
     */
    @Column(name = "child_table_column")
    private String childTableColumn;

    /**
     * 子表关联方式  1对多 1对1 多对多等
     */
    @Column(name = "child_connection_way")
    private String childConnectionWay;

    /**
     * 父表名称
     */
    @Column(name = "father_table_name")
    private String fatherTableName;

    /**
     * 父表关联字段
     */
    @Column(name = "father_table_column")
    private String fatherTableColumn;

    /**
     * 父表关联方式  1对多 1对1 多对多等
     */
    @Column(name = "father_connection_way")
    private String fatherConnectionWay;

    /**
     * 创建时间
     */
    @CollectionTable
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @UpdateTimestamp
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

}
