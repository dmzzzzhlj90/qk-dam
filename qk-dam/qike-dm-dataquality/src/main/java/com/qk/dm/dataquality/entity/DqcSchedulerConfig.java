package com.qk.dm.dataquality.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dqc_scheduler_config")
public class DqcSchedulerConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 作业id */
  @Column(name = "job_id", nullable = false)
  private String jobId;

  /** 调度执行方式 "SCHEDULER_TYPE_SINGLE":"单次调度" "SCHEDULER_TYPE_CYCLE":周期调度; */
  @Column(name = "scheduler_type", nullable = false)
  private String schedulerType;

  /** 调度周期 1-分钟 2-小时 3-天 4-周 */
  @Column(name = "scheduler_cycle")
  private String schedulerCycle;

  /** 间隔时间 调度周期为周 1-周一 2-周二 3-周三 4-周四 5-周五 6-周六 7-周日 */
  @Column(name = "scheduler_interval_time")
  private String schedulerIntervalTime;

  /** 有效日期开始 */
  @Column(name = "effective_time_start")
  private Date effectiveTimeStart;

  /** 有效日期结束 */
  @Column(name = "effective_time_ent")
  private Date effectiveTimeEnt;

  /** 周期时间 */
  @Column(name = "scheduler_time")
  private String schedulerTime;

  /** 调度执行cron表达式 */
  @Column(name = "cron")
  private String cron;

  /** 调度定时id */
  @Column(name = "scheduler_id")
  private Integer schedulerId;

  /** 创建人 */
  @Column(name = "create_userid", nullable = false)
  private String createUserid;

  /** 修改人 */
  @Column(name = "update_userid")
  private String updateUserid;

  /** 创建时间 */
  @Column(name = "gmt_create", nullable = false)
//  @CreationTimestamp
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
//  @UpdateTimestamp
  private Date gmtModified;

  /** 删除标识(0-保留 1-删除) */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag;
}
