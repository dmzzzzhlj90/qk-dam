package com.qk.dm.reptile.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据目录id和相关条件查询维度字段信息
 * @author zys
 * @date 2021/12/8 16:02
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptDimensionInfoColumnParamDTO {
  /**分页信息*/
  private Pagination pagination;
  /**
   * 主键id（维度目录id）
   */
  private Long id;

  /**
   * 创建人姓名
   */
  private String createUsername;

  /**
   * 维度字段编码
   */
  private String dimensionColumnCode;

  /**
   * 维度字段名称
   */
  private String dimensionColumnName;


}