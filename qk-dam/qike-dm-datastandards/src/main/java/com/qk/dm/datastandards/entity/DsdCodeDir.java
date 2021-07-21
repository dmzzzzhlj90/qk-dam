package com.qk.dm.datastandards.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dsd_code_dir")
public class DsdCodeDir implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 码表标准分类ID
     */
    @Column(name = "code_dir_id", nullable = false)
    private String codeDirId;

    /**
     * 码表标准分类名称
     */
    @Column(name = "code_dir_name", nullable = false)
    private String codeDirName;

    /**
     * 父级ID
     */
    @Column(name = "parent_id", nullable = false)
    private String parentId;

    /**
     * 码表目录层级
     */
    @Column(name = "code_dir_level", nullable = false)
    private String codeDirLevel;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 修改时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 创建时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 是否删除；0逻辑删除，1物理删除；
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag =0 ;

}
