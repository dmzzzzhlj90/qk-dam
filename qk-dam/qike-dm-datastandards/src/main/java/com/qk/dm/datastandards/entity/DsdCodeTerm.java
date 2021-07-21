package com.qk.dm.datastandards.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dsd_code_term")
public class DsdCodeTerm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 中文表名称
     */
    @Column(name = "code_table_chn_name", nullable = false)
    private String codeTableChnName;

    /**
     * 英文表名称
     */
    @Column(name = "code_table_en_name", nullable = false)
    private String codeTableEnName;

    /**
     * 码表编码
     */
    @Column(name = "code_id", nullable = false)
    private String codeId;

    /**
     * 码表名称
     */
    @Column(name = "code_name", nullable = false)
    private String codeName;

    /**
     * 码表分类id
     */
    @Column(name = "code_dir_id", nullable = false)
    private String codeDirId;

    /**
     * 码表层级
     */
    @Column(name = "code_dir_level", nullable = false)
    private String codeDirLevel;

    /**
     * 数据类型id
     */
    @Column(name = "term_id")
    private Integer termId;

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

}
