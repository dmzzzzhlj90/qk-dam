package com.qk.dm.dataquality.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dqc_rule_template")
@Where(clause = "del_flag = 0 ")
public class DqcRuleTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 模板名称
     */
    @Column(name = "temp_name", nullable = false)
    private String tempName;

    /**
     * 模板类型1-系统内置 2-自定义
     */
    @Column(name = "temp_type", nullable = false)
    private Integer tempType;

    /**
     * 分类目录
     */
    @Column(name = "dir_id", nullable = false)
    private Long dirId;

    /**
     * 质量维度
     */
    @Column(name = "dimension_id", nullable = false)
    private Integer dimensionId;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    @Column(name = "engine_type", nullable = false)
    private String engineType;

    /**
     * 描述
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * 模板sql
     */
    @Column(name = "temp_sql", nullable = false)
    private String tempSql;

    /**
     * 结果定义
     */
    @Column(name = "temp_result", nullable = false)
    private String tempResult;

    /**
     * 发布状态 -1-下线 0-草稿 1-发布
     */
    @Column(name = "publish_state", nullable = false)
    private Integer publishState;

    /**
     * 创建人
     */
    @Column(name = "create_userid", nullable = false)
    private Long createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private Long updateUserid;

    /**
     * 删除标识(0-保留 1-删除)
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create", nullable = false)
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

}
