package com.qk.dm.datamodel.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 查询建模信息入参DTO
 * @author zys
 * @date 2021/11/11 16:57
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryModelPhysicalDTO implements Serializable {
  /**分页信息*/
  private Pagination pagination;
  /**所属主题id*/
  private Long themeId;
  /**所属层级id*/
  private Long modelId;

}