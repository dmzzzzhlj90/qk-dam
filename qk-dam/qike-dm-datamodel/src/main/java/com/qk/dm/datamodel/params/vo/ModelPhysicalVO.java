package com.qk.dm.datamodel.params.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 建模查询vo
 * @author zys
 * @date 2021/11/11 16:18
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelPhysicalVO {
  /**
   * 基础信息
   */
  private ModelPhysicalTableVO modelPhysicalTableVO;
  /**
   * 字段信息
   */
  private List<ModelPhysicalColumnVO> modelPhysicalColumnVOList;
  /**
   * 关系信息
   */
  private List<ModelPhysicalRelationVO> modelPhysicalRelationVOList;
}