package com.qk.dm.indicator.params.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdcAtomDTO implements Serializable {

  /** 原子指标名称 */
  private String atomIndicatorName;

  /** 原子指标编码 */
  private String atomIndicatorCode;

  /** 数据表 */
  private String dataSheet;

  /** 主题名称 */
  private String themeCode;

  /** 函数字段表达式 */
  private String expression;

  /** 指标状态 0草稿 1已上线 2已下线 */
  private Integer indicatorStatus;

  /** 描述信息 */
  private String describeInfo;

  /** sql语句 */
  private String sqlSentence;
}
