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
@Table(name = "qk_model_physical_table")
@Where(clause = "del_flag = 0 ")
public class ModelPhysicalTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 表名称
     */
    @Column(name = "table_name", nullable = false)
    private String tableName;

    /**
     * 主题
     */
    @Column(name = "theme", nullable = false)
    private String theme;

    /**
     * 数据连接
     */
    @Column(name = "data_connection", nullable = false)
    private String dataConnection;

    /**
     * 数据源连接名称
     */
    @Column(name="data_source_name",nullable = false)
    private String dataSourceName;
    /**
     * 数据源连接id
     */
    @Column(name = "data_source_id", nullable = false)
    private Integer dataSourceId;

    /**
     * 数据库名称
     */
    @Column(name = "database_name", nullable = false)
    private String dataBaseName;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 0草稿 1已发布2 已下线
     */
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @UpdateTimestamp
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;
    /**
     * 创建人id
     */
    @Column(name = "create_userid", nullable = false)
    private Long createUserid;
    /**
     * 修改人id
     */
    @Column(name = "update_userid", nullable = false)
    private Long updateUserid;
    /**
     * HIVE类型表分为内部表和外部表(1表示内部，2表示外部)
     */
    @Column(name = "table_type", nullable = false)
    private String tableType;
    /**
     * 负责人姓名
     */
    @Column(name = "responsible_by", nullable = false)
    private String responsibleBy;
    /**
     * HIVE类型表需要选择数据格式
     */
    @Column(name = "data_format")
    private String dataFormat;
    /**
     * 所属主题id
     */
    @Column(name = "theme_id", nullable = false)
    private String themeId;
    /**
     * 所属层级id
     */
    @Column(name = "model_id", nullable = false)
    private Long modelId;
    /**
     * HIVE类型表需要给ftfs路径
     */
    @Column(name = "hdfs_route")
    private String hdfsRoute;
    /**
     * 数据库和系统定义的sql是否同步（0表示已经同步，1表示未同步）
     */
    @Column(name = "sync_status", nullable = false)
    private Integer syncStatus;

}
