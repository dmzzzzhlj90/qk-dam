package com.qk.dm.indicator.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_idc_composite")
@Where(clause = "del_flag = 0 ")
public class IdcComposite implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 复合指标名称 */
  @Column(name = "composite_indicator_name", nullable = false)
  private String compositeIndicatorName;

  /** 复合指标编码 */
  @Column(name = "composite_indicator_code", nullable = false)
  private String compositeIndicatorCode;

  /** 统计维度 */
  @Column(name = "dim_statistics")
  private String dimStatistics;

  /** 所属主题 */
  @Column(name = "theme_code", nullable = false)
  private String themeCode;

  /** 数据类型 */
  @Column(name = "data_type")
  private String dataType;

  /** 表达式 */
  @Column(name = "expression")
  private String expression;

  /** 指标状态 0草稿 1已上线 2已下线 */
  @Column(name = "indicator_status")
  private Integer indicatorStatus;

  /** 删除标识 0未删除 1已删除 */
  @Column(name = "del_flag")
  private Integer delFlag = 0;

  /** 创建时间 */
  @Column(name = "gmt_create")
  @CreationTimestamp
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  @UpdateTimestamp
  private Date gmtModified;

  /** sql语句 */
  @Column(name = "sql_sentence")
  private String sqlSentence;
}
