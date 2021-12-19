package com.qk.dm.reptile.params.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提供配置页使用输出VO
 * @author zys
 * @date 2021/12/14 10:32
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptDimensionInfoParamsVO {
  /**
   * 主键id（修改接口为必填项）
   */
  private Long id;

  /**
   * 维度目录名称
   */
  private String dimensionName;
  /**
   * 目录编码
   */
  private String dimensionCode;
}