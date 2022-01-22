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
@Table(name = "qk_model_dim")
@Where(clause = "del_flag = 0 ")
public class ModelDim implements Serializable {

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
     * 维度名称
     */
    @Column(name = "dim_name", nullable = false)
    private String dimName;

    /**
     * 维度编码
     */
    @Column(name = "dim_code")
    private String dimCode;

    /**
     * 1 普通维度 2 码表维度 3 层级维度
     */
    @Column(name = "dim_type", nullable = false)
    private String dimType;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 0待审核 1已发布2 已下线
     */
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    /**
     * 连接类型
     */
    @Column(name = "connection_type", nullable = false)
    private String connectionType;

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
     * 责任人
     */
    @Column(name = "responsibility_person", nullable = false)
    private String responsibilityPerson;

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
     * 所属层级id
     */
    @Column(name = "model_id", nullable = false)
    private Long modelId;
    /**
     * 责任人姓名
     */
    @Column(name = "responsible_by", nullable = false)
    private String responsibleBy;

}
