package com.qk.dm.datamodel.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_model_summary")
@Where(clause = "del_flag = 0 ")
public class ModelSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 主题id
     */
    @Column(name = "theme_id", nullable = false)
    private Long themeId;

    /**
     * 主题名称
     */
    @Column(name = "theme_name", nullable = false)
    private String themeName;

    /**
     * 表名称
     */
    @Column(name = "table_name", nullable = false)
    private String tableName;

    /**
     * 维度id
     */
    @Column(name = "dim_id", nullable = false)
    private Long dimId;

    /**
     * 维度名称
     */
    @Column(name = "dim_name", nullable = false)
    private String dimName;

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
