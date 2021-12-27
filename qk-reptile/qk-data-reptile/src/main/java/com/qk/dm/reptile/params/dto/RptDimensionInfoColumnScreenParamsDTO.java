package com.qk.dm.reptile.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
  @NotBlank(message = "目录id不能为空")
  private Long id;

  /**
   * 目录字段编码集合
   */
  private List <String> dimensionColumnCodeList;
}