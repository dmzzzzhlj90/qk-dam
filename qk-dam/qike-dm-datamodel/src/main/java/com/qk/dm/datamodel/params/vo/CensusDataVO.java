package com.qk.dm.datamodel.params.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关系建模数据统计
 * @author zys
 * @date 2021/11/12 10:40
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CensusDataVO {
  //标准覆盖率
  private String coverage="0%";
  //已经发布表数量
  private int pushnums;
  //表总数
  private int totlenums;
  //发布字段数量
  private int fieldnums;
}