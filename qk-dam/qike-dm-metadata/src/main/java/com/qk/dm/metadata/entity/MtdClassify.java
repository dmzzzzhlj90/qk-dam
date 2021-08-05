package com.qk.dm.metadata.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_mtd_classify")
public class MtdClassify implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

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
     * 是否删除；0逻辑删除，1物理删除；
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

    /**
     * 同步状态 -1删除；0修改；1已同步；2新增；
     */
    @Column(name = "synch_status", nullable = false)
    private Integer synchStatus;

}
