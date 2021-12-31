package com.qk.dm.dataquality.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据质量_规则调度_结果表
 */
@Data
@Entity
@Table(name = "qk_dqc_scheduler_result")
public class DqcSchedulerResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 作业id
     */
    @Column(name = "job_id", nullable = false)
    private String jobId;

    /**
     * 作业名称
     */
    @Column(name = "job_name", nullable = false)
    private String jobName;

    /**
     * 规则类型
     */
    @Column(name = "rule_id", nullable = false)
    private String ruleId;

    /**
     * 规则类型
     */
    @Column(name = "rule_name", nullable = false)
    private String ruleName;

    /**
     * 规则模板id
     */
    @Column(name = "rule_temp_id", nullable = false)
    private String ruleTempId;

    /**
     * 工作流实例task_code绑定规则
     */
    @Column(name = "task_code", nullable = false)
    private Long taskCode;

    /**
     * 执行规则元数据信息
     */
    @Column(name = "rule_meta_data", nullable = false)
    private String ruleMetaData;

    /**
     * 告警表达式结果
     */
    @Column(name = "warn_result")
    private String warnResult;

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
     * 删除标识(0-保留 1-删除)
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

    /**
     * 执行参数
     */
    @Column(name = "rule_params", nullable = false)
    private String ruleParams;

    /**
     * 规则执行结果
     */
    @Column(name = "rule_result")
    private String ruleResult;

}