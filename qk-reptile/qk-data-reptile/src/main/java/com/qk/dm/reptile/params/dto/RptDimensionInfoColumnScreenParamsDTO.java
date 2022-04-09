package com.qk.dm.reptile.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zys
 * @date 2021/12/20 15:04
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptDimensionInfoColumnScreenParamsDTO {
  /**
   * 目录id
   */
  @NotNull(message = "目录id不能为空")
  private Long id;

  /**
   * 目录字段编码集合
   */
  @NotNull(message = "目录字段编码集合不能为空")
  private List <String> dimensionColumnCodeList;
  /**
   * 基础数据id
   */
  private Long baseInfoId;
}