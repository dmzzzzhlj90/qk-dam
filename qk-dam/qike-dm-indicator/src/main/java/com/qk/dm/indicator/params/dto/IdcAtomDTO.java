package com.qk.dm.indicator.params.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class IdcAtomDTO implements Serializable {

  /** 原子指标名称 */
  @Pattern(
      regexp = "^[a-zA-Z\u4E00-\u9FA5][\u4E00-\u9FA5A-Za-z0-9_]+$",
      message = "只能包含中文、英文字母、数字和下划线,且以中文或英文字母开头")
  private String atomIndicatorName;

  /** 原子指标编码 */
  @Pattern(regexp = "^[a-zA-Z][A-Za-z0-9_]+$", message = "只能包含英文字母、数字和下划线，且以英文字母开头")
  private String atomIndicatorCode;

  /** 数据表 */
  @NotNull(message = "数据表不能为空！")
  private String dataSheet;

  /** 主题名称 */
  @NotNull(message = "主题不能为空！")
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
