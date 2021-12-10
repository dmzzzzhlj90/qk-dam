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
     * 手动执行
     */
    @Column(name = "manual_execution")
    private String manualExecution;

    /**
     * 正则
     */
    @Column(name = "regular")
    private Long regular;

    /**
     * 选择器类型
     */
    @Column(name = "selector")
    private Long selector;

    /**
     * 维度字段名称
     */
    @Column(name = "column_name", nullable = false)
    private String columnName;

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
