package com.qk.dm.datastandards.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "qk_dsd_term")
public class DsdTerm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 中文名称
     */
    @Column(name = "chinese_name", nullable = false)
    private String chineseName;

    /**
     * 英文名称
     */
    @Column(name = "english_name", nullable = false)
    private String englishName;

    /**
     * 英文简称
     */
    @Column(name = "short_english_name", nullable = false)
    private String shortEnglishName;

    /**
     * 词根名称
     */
    @Column(name = "root_name", nullable = false)
    private String rootName;

    /**
     * 状态
     */
    @Column(name = "state")
    private Integer state;

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
