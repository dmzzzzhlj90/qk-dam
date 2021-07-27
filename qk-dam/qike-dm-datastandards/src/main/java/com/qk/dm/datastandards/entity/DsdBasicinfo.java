package com.qk.dm.datastandards.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "qk_dsd_basicinfo")
public class DsdBasicinfo implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

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
  @Column(name = "data_capacity", nullable = false)
  private String dataCapacity;

  /** 引用码表 */
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

  /** 描述 */
  @Column(name = "description")
  private String description;

  /** 创建时间 */
  @Column(name = "gmt_create")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /** 是否删除；0逻辑删除，1物理删除； */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag = 0;
}
