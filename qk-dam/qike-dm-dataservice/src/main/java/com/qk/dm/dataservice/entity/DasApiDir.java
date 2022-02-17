package com.qk.dm.dataservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据服务_API目录
 */
@Data
@Entity
@Table(name = "qk_das_api_dir")
public class DasApiDir implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * API目录ID
     */
    @Column(name = "api_dir_id", nullable = false)
    private String apiDirId;

    /**
     * API目录名称
     */
    @Column(name = "api_dir_name", nullable = false)
    private String apiDirName;

    /**
     * 目录父级ID
     */
    @Column(name = "parent_id", nullable = false)
    private String parentId;

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
    private Integer delFlag;

}