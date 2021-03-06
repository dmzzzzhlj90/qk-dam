package com.qk.dm.datastandards.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "qk_dsd_basicinfo")
public class DsdBasicinfo implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 标准名称 */
  @Column(name = "dsd_name", nullable = false)
  private String dsdName;

  /** 标准编码 */
  @Column(name = "dsd_code", nullable = false)
  private String dsdCode;

  /** 字段名称 */
  @Column(name = "col_name", nullable = false)
  private String colName;

  /** 数据类型 */
  @Column(name = "data_type", nullable = false)
  private String dataType;

  /** 数据容量 */
  @Column(name = "data_capacity")
  private String dataCapacity;

  /** 码表标准分类ID */
  @Column(name = "code_dir_id")
  private String codeDirId;

  /** 引用码表层级 */
  @Column(name = "use_code_level")
  private String useCodeLevel;

  /** 码表字段 */
  @Column(name = "code_col")
  private String codeCol;

  /** 标准层级 */
  @Column(name = "dsd_level", nullable = false)
  private String dsdLevel;

  /** 标准层级ID */
  @Column(name = "dsd_level_id", nullable = false)
  private String dsdLevelId;

  /** 排序字段(不设置根据标准名称进行排序) */
  @Column(name = "sort_field")
  private Integer sortField;

  /** 描述 */
  @Column(name = "description")
  private String description;

  /** 创建时间 */
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /** 是否删除；0逻辑删除，1物理删除； */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag;
}
