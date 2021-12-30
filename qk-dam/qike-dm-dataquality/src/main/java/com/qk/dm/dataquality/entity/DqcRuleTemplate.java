package com.qk.dm.dataquality.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/** @author shenpengjie */
@Data
@Entity
@Table(name = "qk_dqc_rule_template")
@Where(clause = "del_flag = 0 ")
public class DqcRuleTemplate implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 模板名称 */
  @Column(name = "temp_name", nullable = false)
  private String tempName;

  /** 模板类型 "BUILT_IN_SYSTEM":"系统内置","CUSTOM":"自定义" */
  @Column(name = "temp_type", nullable = false)
  private String tempType;

  /** 分类目录 */
  @Column(name = "dir_id", nullable = false)
  private String dirId;

  /** 质量维度 */
  @Column(name = "dimension_type", nullable = false)
  private String dimensionType;

  /** 适用引擎 MYSQL,HIVE,ORACLE,适用多个以逗号分隔 */
  @Column(name = "engine_type", nullable = false)
  private String engineType;

  /** 描述 */
  @Column(name = "description", nullable = false)
  private String description;

  /** 模板sql */
  @Column(name = "temp_sql", nullable = false)
  private String tempSql;

  /** 结果定义 */
  @Column(name = "temp_result", nullable = false)
  private String tempResult;

  /** 发布状态 "OFFLINE":"下线","OUTLINE":"草稿","RELEASE":"发布"; */
  @Column(name = "publish_state", nullable = false)
  private String publishState;

  /** 规则类型 "RULE_TYPE_FIELD":"字段级别规则","RULE_TYPE_TABLE":"表级别规则","RULE_TYPE_DB":"库级别规则" */
  @Column(name = "rule_type", nullable = false)
  private String ruleType;

  /** 创建人 */
  @Column(name = "create_userid", nullable = false)
  private String createUserid;

  /** 修改人 */
  @Column(name = "update_userid")
  private String updateUserid;

  /** 删除标识(0-保留 1-删除) */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag;

  /** 创建时间 */
  @Column(name = "gmt_create", nullable = false)
  @CreationTimestamp
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  @UpdateTimestamp
  private Date gmtModified;
}
