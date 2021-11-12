package com.qk.dm.datamodel.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_model")
@Where(clause = "del_flag = 0 ")
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 模型名称
     */
    @Column(name = "model_name", nullable = false)
    private String modelName;

    /**
     * 数仓分层名称
     */
    @Column(name = "layered_name")
    private String layeredName;

    /**
     * 描述
     */
    @Column(name = "desc")
    private String desc;

    /**
     * 1逻辑模型 2 物理模型
     */
    @Column(name = "model_type", nullable = false)
    private Integer modelType;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

    /**
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

}
