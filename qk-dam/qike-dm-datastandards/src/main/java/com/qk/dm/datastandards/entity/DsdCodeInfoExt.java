package com.qk.dm.datastandards.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "qk_dsd_code_info_ext")
public class DsdCodeInfoExt implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 关键码表信息表 */
  @Column(name = "dsd_code_info_id", nullable = false)
  private Long dsdCodeInfoId;

  /** 建表配置编码 */
  @Column(name = "table_conf_code")
  private String tableConfCode;

  /** 建表配置值 */
  @Column(name = "table_conf_value")
  private String tableConfValue;

  /** 创建时间 */
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /** 是否删除；0逻辑删除，1物理删除； */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag = 0;

  /** 建表配合扩展字段数值 */
  @Column(name = "table_conf_ext_values")
  private String tableConfExtValues;
}
