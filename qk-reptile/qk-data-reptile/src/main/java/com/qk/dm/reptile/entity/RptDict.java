package com.qk.dm.reptile.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_rpt_dict")
public class RptDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区划信息id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父级挂接id
     */
    @Column(name = "pid")
    private Long pid;

    /**
     * 区划编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 区划名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    /**
     * 状态 0 正常 -2 删除 -1 停用
     */
    @Column(name = "status")
    private Boolean status;

    /**
     * 级次id 0:省/自治区/直辖市 1:市级 2:县级
     */
    @Column(name = "level")
    private Boolean level;

}
