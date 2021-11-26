package com.qk.dm.dataquality.vo;

import lombok.Data;

/** @author shenpengjie */
@Data
public class DqcProcessInstanceVO {
  Integer id;
  /** 工作流名称 */
  String name;
  /** 流程id */
  Integer processDefinitionId;
  /** 状态 */
  String state;
  /** 运行时间 */
  String duration;
  /** 状态名称 */
  String stateName;
}
