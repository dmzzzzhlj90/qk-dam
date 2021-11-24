package com.qk.dm.dataquality.dolphinapi.builder;

import lombok.Data;

@Data
public class InstanceData {
  Integer id;
  /** 流程id */
  Integer processDefinitionId;
  /** 状态 */
  String state;
  /** 运行时间 */
  String duration;
}
