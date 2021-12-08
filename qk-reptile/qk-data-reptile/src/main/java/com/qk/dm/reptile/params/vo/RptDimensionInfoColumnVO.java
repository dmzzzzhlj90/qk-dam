package com.qk.dm.reptile.params.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 维度信息字段出参VO
 * @author zys
 * @date 2021/12/8 11:20
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptDimensionInfoColumnVO {

  /**
   * 主键id
   */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 维度目录id
   */
  @Column(name = "dimension_id", nullable = false)
  private Long dimensionId;

  /**
   * 描述
   */
  @Column(name = "description")
  private String description;

  /**
   * 维度字段名称
   */
  @Column(name = "dimension_column_name", nullable = false)
  private String dimensionColumnName;

  /**
   * 修改时间
   */
  @UpdateTimestamp
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /**
   * 创建时间
   */
  @CreationTimestamp
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /**
   * 创建人id
   */
  @Column(name = "create_userid")
  private Long createUserid;

  /**
   * 修改人id
   */
  @Column(name = "update_userid")
  private Long updateUserid;

  /**
   * 创建人姓名
   */
  @Column(name = "create_username")
  private String createUsername;

  /**
   * 修改人姓名
   */
  @Column(name = "update_username")
  private String updateUsername;

  /**
   * 维度字段编码
   */
  private String dimensionColumnCode;
}