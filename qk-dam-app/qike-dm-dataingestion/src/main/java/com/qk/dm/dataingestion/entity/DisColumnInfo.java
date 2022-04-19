package com.qk.dm.dataingestion.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据迁移字段信息
 */
@Data
@Entity
@Table(name = "qk_dis_column_info")
public class DisColumnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 迁移基础表id
     */
    @Column(name = "base_info_id", nullable = false)
    private Long baseInfoId;

    /**
     * 字段源名称
     */
    @Column(name = "source_name", nullable = false)
    private String sourceName;

    /**
     * 字段源类型
     */
    @Column(name = "source_type", nullable = false)
    private String sourceType;

    /**
     * 字段目标名称
     */
    @Column(name = "target_name")
    private String targetName;

    /**
     * 字段目标类型
     */
    @Column(name = "target_type")
    private String targetType;

    /**
     * 创建人
     */
    @Column(name = "create_userid")
    private String createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private String updateUserid;

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
     * 删除字段 0未删除 1 已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

}
