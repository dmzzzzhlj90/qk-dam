package com.qk.dm.dataquality.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
     * 创建人
     */
    @Column(name = "create_userid", nullable = false)
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
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 执行参数
     */
    @Column(name = "rule_params")
    private String ruleParams;

    /**
     * 规则执行结果
     */
    @Column(name = "rule_result")
    private String ruleResult;

}