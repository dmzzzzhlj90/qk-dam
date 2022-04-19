package com.qk.dam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdBasicInfoVO {

  /** ID编号 */
  private Long id;

  /** 标准名称 */
  private String dsdName;

  /** 标准代码 */
  private String dsdCode;

  /** 字段名称 */
  private String colName;
  /** 数据类型 */
  private String dataType;

  /** 数据容量 */

  private String dataCapacity;

  /** 码表标准分类ID */
  private String codeDirId;

  /** 引用码表 */
  private String useCodeLevel;

  /** 码表字段 */
  private String codeCol;

  /** 标准层级 */
  private String dsdLevel;

  /** 标准层级ID */
  private String dsdLevelId;

  /** 标准层级目录名称 */
  private String dsdLevelName;

  /** 排序字段(不设置根据标准名称进行排序) */
  private Integer sortField;

  /** 描述 */
  private String description;

  /** 修改时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;
}
