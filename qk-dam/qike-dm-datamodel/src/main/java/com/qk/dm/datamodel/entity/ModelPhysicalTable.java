package com.qk.dm.datamodel.entity;

import lombok.Data;
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
     * 数据库名称
     */
    @Column(name = "database_name", nullable = false)
    private String databaseName;

    /**
     * 描述
     */
    @Column(name = "desc")
    private String desc;

    /**
     * 0草稿 1已发布2 已下线
     */
    @Column(name = "status", nullable = false)
    private Integer status;

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
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

}
