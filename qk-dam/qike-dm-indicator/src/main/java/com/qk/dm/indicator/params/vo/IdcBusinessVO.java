package com.qk.dm.indicator.params.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdcBusinessVO {
  /** 主键id */
  private Integer id;

  /** 业务指标名称 */
  private String bIndicatorName;

  /** 指标编码 */
  private String bIndicatorCode;

  /** 指标别名 */
  private String bIndicatorAlias;

  /** 设置目的 */
  private String setPurpose;

  /** 指标定义 */
  private String indicatorDefinition;

  /** 备注 */
  private String remarks;

  /** 计算公式 */
  private String calculationFormula;

  /** 统计周期 */
  private String statisticalCycle;

  /** 统计维度 */
  private String dimStatistical;

  /** 指标状态 0草稿 1已上线 2已下线 */
  private Integer indicatorStatus;

  /** 口径或修饰词 */
  private String caliberModifier;

  /** 应用场景 */
  private String applicationScenario;

  /** 技术指标 */
  private String techIndicator;

  /** 度量对象 */
  private String measureObject;

  /** 度量单位 */
  private String measureUnit;

  /** 指标管理部门 */
  private String indicatorDepart;

  /** 指标负责人 */
  private String indicatorPersonLiable;

  /** 创建时间 */
  private Date gmtCreate;

  /** 修改时间 */
  private Date gmtModified;
}
