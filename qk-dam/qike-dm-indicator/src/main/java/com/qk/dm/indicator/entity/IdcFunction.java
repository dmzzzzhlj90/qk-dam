package com.qk.dm.indicator.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_idc_function")
public class IdcFunction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 函数名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 分类
     */
    @Column(name = "classify")
    private String classify;

    /**
     * 函数
     */
    @Column(name = "function", nullable = false)
    private String function;

    /**
     * 引擎
     */
    @Column(name = "engine")
    private String engine;

    /**
     * 类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 类型名称
     */
    @Column(name = "type_name")
    private String typeName;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Integer parentId;

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
     * 是否删除；0逻辑删除，1物理删除
     */
    @Column(name = "del_flag")
    private Integer delFlag;

}
