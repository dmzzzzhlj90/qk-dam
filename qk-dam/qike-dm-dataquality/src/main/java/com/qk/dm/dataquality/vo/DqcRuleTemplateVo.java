package com.qk.dm.dataquality.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DqcRuleTemplateVo {

  private Long id;

  /** 模板名称 */
  @NotBlank(message = "模板名称不能为空！")
  private String tempName;

  /** 模板类型1-系统内置 2-自定义 */
  private Integer tempType = 1;

  /** 分类目录 */
  @NotNull(message = "分类目录不能为空！")
  private Long dirId;

  /** 质量维度 */
  @NotNull(message = "质量维度不能为空！")
  private Integer dimensionId;

  /** 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔 */
  private String engineType = "1,2";

  /** 描述 */
  @NotBlank(message = "描述不能为空！")
  private String description;

  /** 模板sql */
  @NotBlank(message = "模板sql不能为空！")
  private String tempSql;

  /** 结果定义 */
  @NotBlank(message = "结果定义不能为空！")
  private String tempResult;

  /** 发布状态 -1-下线 0-草稿 1-发布 */
  private Integer publishState = 0;
}
