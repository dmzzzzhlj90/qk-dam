package com.qk.dm.dataquality.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dqc_scheduler_basic_info")
public class DqcSchedulerBasicInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 作业id */
  @Column(name = "job_id", nullable = false)
  private String jobId;

  /** 作业名称 */
  @Column(name = "job_name", nullable = false)
  private String jobName;

  /** 分类目录 */
  @Column(name = "dir_id", nullable = false)
  private Long dirId;

  /** 提示级别 1-严重 */
  @Column(name = "notify_level", nullable = false)
  private Integer notifyLevel;

  /** 通知状态 0-关 1-开 */
  @Column(name = "notify_state", nullable = false)
  private Integer notifyState;

  /** 通知类型 1-触发告警 2-运行成功 */
  @Column(name = "notify_type", nullable = false)
  private Integer notifyType;

  /** 主题，多个以逗号分隔 */
  @Column(name = "notify_theme_id", nullable = false)
  private String notifyThemeId;

  /** 调度开启状态 0-停止 1-启动 */
  @Column(name = "scheduler_open_state")
  private Integer schedulerOpenState;

  /** 调度状态 0-未启动 1-调度中 2-运行中 3-停止 4-运行失败 */
  @Column(name = "scheduler_state")
  private Integer schedulerState;

  /** 创建人 */
  @Column(name = "create_userid", nullable = false)
  private Long createUserid;

  /** 修改人 */
  @Column(name = "update_userid")
  private Long updateUserid;

  /** 删除标识(0-保留 1-删除) */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag;

  /** 创建时间 */
  @Column(name = "gmt_create", nullable = false)
  @CreationTimestamp
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  @UpdateTimestamp
  private Date gmtModified;
}
