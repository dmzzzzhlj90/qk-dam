package com.qk.dm.dataquality.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dqc_scheduler_basic_info")
public class DqcSchedulerBasicInfo implements Serializable {

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
     * 分类目录
     */
    @Column(name = "dir_id", nullable = false)
    private String dirId;

    /**
     * 调度流程实例ID
     */
    @Column(name = "process_definition_Id", nullable = false)
    private Integer processDefinitionId;

    /**
     * 提示级别 1-严重
     */
    @Column(name = "notify_level", nullable = false)
    private Integer notifyLevel;

    /**
     * 通知状态 0-关 1-开
     */
    @Column(name = "notify_state", nullable = false)
    private Integer notifyState;

    /**
     * 通知类型 1-触发告警 2-运行成功
     */
    @Column(name = "notify_type", nullable = false)
    private Integer notifyType;

    /**
     * 主题，多个以逗号分隔
     */
    @Column(name = "notify_theme_id", nullable = false)
    private String notifyThemeId;

    /**
     * 调度状态 0-未启动 1-调度中
     */
    @Column(name = "scheduler_state")
    private Integer schedulerState;

    /**
     * 运行实例状态 0-初始状态 1-运行中 2-停止 3-成功 4-失败
     */
    @Column(name = "run_instance_state")
    private Integer runInstanceState;

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

}