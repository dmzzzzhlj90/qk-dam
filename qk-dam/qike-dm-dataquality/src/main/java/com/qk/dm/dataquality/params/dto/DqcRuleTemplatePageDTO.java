package com.qk.dm.dataquality.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

/**
 * @author shenpj
 * @date 2021/11/25 3:24 下午
 * @since 1.0.0
 */
@Data
public class DqcRuleTemplatePageDTO {

  Pagination pagination;

  /** 模板名称 */
  private String tempName;

  /** 分类目录 */
  private String dirId;

  /** 质量维度 */
  private String dimensionType;

  /** 适用引擎 MYSQL,HIVE,ORACLE */
  private String engineType;

  /** 规则类型 "RULE_TYPE_FIELD", "字段级别规则","RULE_TYPE_TABLE", "表级别规则","RULE_TYPE_DB", "库级别规则"; */
  private String ruleType;
}
