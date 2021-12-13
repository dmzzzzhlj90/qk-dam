package com.qk.dm.indicator.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "qk_idc_atom")
@Where(clause = "del_flag = 0 ")
public class IdcAtom implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键id */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 原子指标名称 */
  @Column(name = "atom_indicator_name", nullable = false)
  private String atomIndicatorName;

  /** 原子指标编码 */
  @Column(name = "atom_indicator_code", nullable = false)
  private String atomIndicatorCode;

  /** 数据表 */
  @Column(name = "data_sheet")
  private String dataSheet;

  /** 主题名称 */
  @Column(name = "theme_code")
  private String themeCode;

  /** 函数字段表达式 */
  @Column(name = "expression")
  private String expression;

  /** 指标状态 0草稿 1已上线 2已下线 */
  @Column(name = "indicator_status")
  private Integer indicatorStatus;

  /** 描述信息 */
  @Column(name = "describe_info")
  private String describeInfo;

  /** 创建时间 */
  @Column(name = "gmt_create")
  @CreationTimestamp
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  @UpdateTimestamp
  private Date gmtModified;

  /** 删除标识 0未删除 1已删除 */
  @Column(name = "del_flag")
  private Integer delFlag = 0;

  /** sql语句 */
  @Column(name = "sql_sentence")
  private String sqlSentence;
}
