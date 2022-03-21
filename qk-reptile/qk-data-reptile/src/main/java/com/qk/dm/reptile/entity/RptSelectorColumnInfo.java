package com.qk.dm.reptile.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_rpt_selector_column_info")
public class RptSelectorColumnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 配置表id
     */
    @Column(name = "config_id")
    private Long configId;

    /**
     * 维度字段名称
     */
    @Column(name = "column_name", nullable = false)
    private String columnName;

    /**
     * 维度字段编码
     */
    @Column(name = "column_code", nullable = false)
    private String columnCode;

    /**
     * 选择器类型 0代表xpath、1代表正则 ，99代表手动指定
     */
    @Column(name = "selector")
    private Integer selector;

    /**
     * 选择器字段值
     */
    @Column(name = "selector_val")
    private String selectorVal;

    /**
     * 元素类型 0代表单元素 1代表多元素
     */
    @Column(name = "element_type")
    private Integer elementType;

    /**
     * 请求前缀
     */
    @Column(name = "request_before_prefix")
    private String  requestBeforePrefix;

    /**
     * 请求后缀
     */
    @Column(name = "request_after_prefix")
    private String requestAfterPrefix;

    /**
     * 来源前缀
     */
    @Column(name = "source_before_prefix")
    private String sourceBeforePrefix;

    /**
     * 来源后缀
     */
    @Column(name = "source_after_prefix")
    private String sourceAfterPrefix;

    /**
     * 前缀
     */
    @Column(name = "before_prefix")
    private String beforePrefix;

    /**
     * 后缀
     */
    @Column(name = "after_prefix")
    private String afterPrefix;

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
