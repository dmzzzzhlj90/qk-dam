package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModelSqlDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 对应的表id */
  private Long tableId;

  /** 1,逻辑表2物理表 3 维度表 4 汇总表 */
  private Integer type;

  /** sql语句 */
  private String sqlSentence;

}
