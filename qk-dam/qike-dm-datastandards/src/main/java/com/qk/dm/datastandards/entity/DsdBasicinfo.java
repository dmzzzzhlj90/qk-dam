package com.qk.dm.datastandards.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dsd_basicinfo")
public class DsdBasicinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标准名称
     */
    @Column(name = "dsd_name", nullable = false)
    private String dsdName;

    /**
     * 标准编码
     */
    @Column(name = "dsd_code", nullable = false)
    private String dsdCode;

    /**
     * 字段名称
     */
    @Column(name = "col_name", nullable = false)
    private String colName;

    /**
     * 数据类型
     */
    @Column(name = "data_type", nullable = false)
    private String dataType;

    /**
     * 数据容量
     */
    @Column(name = "data_capacity", nullable = false)
    private String dataCapacity;

    /**
     * 引用码表
     */
    @Column(name = "use_code_id")
    private String useCodeId;

    /**
     * 码表字段
     */
    @Column(name = "code_col")
    private String codeCol;

    /**
     * 标准层级
     */
    @Column(name = "dsd_level", nullable = false)
    private String dsdLevel;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified = LocalDateTime.now();

    /**
     * 是否删除；0逻辑删除，1物理删除；
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

}
