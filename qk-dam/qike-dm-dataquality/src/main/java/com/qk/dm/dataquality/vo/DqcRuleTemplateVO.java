package com.qk.dm.dataquality.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author shenpengjie
 */
@Data
public class DqcRuleTemplateVO {

  private Long id;

  /** 模板名称 */
  @NotBlank(message = "模板名称不能为空！")
  private String tempName;

  /** 模板类型 BUILT_IN_SYSTEM_系统内置 CUSTOM_自定义 */
  @NotBlank(message = "模板类型不能为空！")
  private String tempType;

  /** 分类目录 */
  @NotNull(message = "分类目录不能为空！")
  private String dirId;

  /** 质量维度 1-完整性 2-唯一性 3-及时性 4-有效性 5-准确性 6-一致性*/
  @NotNull(message = "质量维度不能为空！")
  private String dimensionType;

  /** 适用引擎 MYSQL,HIVE,ORACLE,适用多个以逗号分隔 */
  @NotNull(message = "适用引擎不能为空！")
  private List<String> engineType;

  /** 描述 */
  @NotBlank(message = "描述不能为空！")
  private String description;

  /** 模板sql */
  @NotBlank(message = "模板sql不能为空！")
  private String tempSql;

  /** 结果定义 */
  @NotBlank(message = "结果定义不能为空！")
  private String tempResult;

  /** 规则类型 "RULE_TYPE_FIELD":"字段级别规则","RULE_TYPE_TABLE":"表级别规则","RULE_TYPE_DB":"库级别规则" */
  private String ruleType;
}
