package com.qk.dm.datastandards.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "qk_dsd_basicinfo")
public class DsdBasicinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 标准代码
     */
    @Column(name = "dsd_id", nullable = false)
    private String dsdId;

    /**
     * 标准名称
     */
    @Column(name = "dsd_name", nullable = false)
    private String dsdName;

    /**
     * 数据容量
     */
    @Column(name = "data_capacity", nullable = false)
    private String dataCapacity;

    /**
     * 引用码表
     */
    @Column(name = "use_code_id", nullable = false)
    private String useCodeId;

    /**
     * 码表字段
     */
    @Column(name = "code_col", nullable = false)
    private String codeCol;

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
    private Integer delFlag=0;

}
