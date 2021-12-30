package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/** @author shenpengjie */
@Data
public class DqcRuleTemplateInfoVO{

  private Integer id;

  /** 模板名称 */
  private String tempName;

  /** 模板类型 BUILT_IN_SYSTEM_系统内置 CUSTOM_自定义 */
  private String tempType;

  /** 分类目录 */
  private String dirId;

  /** 质量纬度id */
  private String dimensionType;

  /** 适用引擎 MYSQL,HIVE,ORACLE,适用多个以逗号分隔 */
  private List<String> engineTypeList;

  /** 描述 */
  private String description;

  /** 模板sql */
  private String tempSql;

  /** 结果定义 */
  private String tempResult;

  /** 规则类型 "RULE_TYPE_FIELD":"字段级别规则","RULE_TYPE_TABLE":"表级别规则","RULE_TYPE_DB":"库级别规则" */
  private String ruleType;

  /** 发布状态 "OFFLINE":"下线","OUTLINE":"草稿","RELEASE":"发布"; */
  private String publishState;

  /** 创建人 */
  private String createUserid;

  /** 修改时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;
}
