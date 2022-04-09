package com.qk.dm.indicator.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "qk_idc_time_limit")
public class IdcTimeLimit implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 限定名称 */
  @Column(name = "limit_name", nullable = false)
  private String limitName;

  /** 限定类型 1按分钟 2按小时 3按月 4按年 */
  @Column(name = "limit_type", nullable = false)
  private Integer limitType;

  /** 基准时间 如limit_type按月表示每月多少日，如limit_type按日表示每日多少时，如limit_type按小时表示每时的多少分 */
  @Column(name = "base_time")
  private String baseTime;

  /** 快速选择 */
  @Column(name = "quick_start")
  private String quickStart;

  /** 开始位置 */
  @Column(name = "start")
  private String start;

  /** 结束位置 */
  @Column(name = "end")
  private String end;

  /** 描述信息 */
  @Column(name = "describe")
  private String describe;

  /** 创建时间 */
  @Column(name = "gmt_create")
  @CreationTimestamp
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  @UpdateTimestamp
  private Date gmtModified;

  /** 0未删除 1已删除 */
  @Column(name = "del_flag")
  private Integer delFlag = 0;
}
