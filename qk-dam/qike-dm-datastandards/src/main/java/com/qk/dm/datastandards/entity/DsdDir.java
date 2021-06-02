package com.qk.dm.datastandards.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "qk_dsd_dir")
public class DsdDir implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据标准分类
     */
    @Column(name = "dir_dsd__id", nullable = false)
    private Integer dirDsdid;

    /**
     * 数据标准分类名称
     */
    @Column(name = "dir_dsd_name", nullable = false)
    private String dirDsdName;

    /**
     * 父级id
     */
    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

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
