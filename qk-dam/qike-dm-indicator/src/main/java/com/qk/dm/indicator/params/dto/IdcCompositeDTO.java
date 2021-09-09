package com.qk.dm.indicator.params.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class IdcCompositeDTO implements Serializable {

  /** 复合指标名称 */
  @Pattern(
          regexp = "^[a-zA-Z\u4E00-\u9FA5][\u4E00-\u9FA5A-Za-z0-9_]+$",
          message = "只能包含中文、英文字母、数字和下划线,且以中文或英文字母开头")
  private String compositeIndicatorName;

  /** 复合指标编码 */
  private String compositeIndicatorCode;

  /** 统计维度 */
  private String dimStatistics;

  /** 所属主题 */
  private String themeCode;

  /** 数据类型 */
  private String dataType;

  /** 表达式 */
  private String expression;

  /** 指标状态 0草稿 1已上线 2已下线 */
  private Integer indicatorStatus;

  /** sql语句 */
  private String sqlSentence;
}
