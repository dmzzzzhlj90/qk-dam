package com.qk.dm.datastandards.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
     * 码表分类id
     */
    @Column(name = "code_dir_id", nullable = false)
    private String codeDirId;

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
