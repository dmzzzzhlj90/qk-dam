package com.qk.dm.datastandards.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dsd_code_info")
public class DsdCodeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 基础配置-表名
     */
    @Column(name = "table_name", nullable = false)
    private String tableName;

    /**
     * 基础配置-表编码
     */
    @Column(name = "table_code", nullable = false)
    private String tableCode;

    /**
     * 基础配置-描述
     */
    @Column(name = "table_desc")
    private String tableDesc;

    /**
     * 所属目录id
     */
    @Column(name = "code_dir_id", nullable = false)
    private String codeDirId;

    /**
     * 所属目录层级
     */
    @Column(name = "code_dir_level", nullable = false)
    private String codeDirLevel;

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
     * 建表配置扩展字段
     */
    @Column(name = "table_conf_fields")
    private String tableConfFields;

}
