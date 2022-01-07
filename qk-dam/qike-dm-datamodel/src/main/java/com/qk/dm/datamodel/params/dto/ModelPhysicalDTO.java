package com.qk.dm.datamodel.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 关系建模入参DTO
 * @author zys
 * @date 2021/11/10 15:47
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelPhysicalDTO implements Serializable {
  /**基本信息*/
  private ModelPhysicalTableDTO modelPhysicalTableDTO;
  /**字段配置*/
  private List<ModelPhysicalColumnDTO> modelColumnDtoList;
  /**关系*/
  private List<ModelPhysicalRelationDTO> modelRelationDtoList;
}