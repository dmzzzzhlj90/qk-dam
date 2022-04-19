package com.qk.dm.dataingestion.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 前端高级属性展示表
 */
@Data
@Entity
@Table(name = "qk_dis_attr_view")
public class DisAttrView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类型 source 源 target 目标
     */
    @Column(name = "type")
    private String type;

    /**
     * 数据库类型 如 mysql  hive
     */
    @Column(name = "connect_type", nullable = false)
    private String connectType;

    /**
     * 字段前端显示名称
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 前端请求后端接口传递的参数名称
     */
    @Column(name = "data_index")
    private String dataIndex;

    /**
     * 是否必填
     */
    @Column(name = "required")
    private Boolean required;

    /**
     * 前端展示的数据类型  例如 text、select
     */
    @Column(name = "value_type")
    private String valueType;

    /**
     * 默认值
     */
    @Column(name = "default_value")
    private String defaultValue;

    /**
     * 是否删除；0未删除，1已删除；
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

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

}
