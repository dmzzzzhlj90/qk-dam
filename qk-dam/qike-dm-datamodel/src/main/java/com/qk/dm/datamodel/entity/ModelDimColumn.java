package com.qk.dm.datamodel.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_model_dim_column")
@Where(clause = "del_flag = 0 ")
public class ModelDimColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 维度id
     */
    @Column(name = "dim_id", nullable = false)
    private Long dimId;

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
     * 标准编码
     */
    @Column(name = "standards_code")
    private String standardsCode;

    /**
     * 是否是主键 0否 1是
     */
    @Column(name = "its_primary_key")
    private String itsPrimaryKey = "0";

    /**
     * 是否分区 0否 1是
     */
    @Column(name = "its_partition")
    private String itsPartition = "0";

    /**
     * 是否为空 0否 1是
     */
    @Column(name = "its_null")
    private String itsNull = "0";

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

    /**
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

}
