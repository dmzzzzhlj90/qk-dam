package com.qk.dm.authority.vo.params;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 分页查询api资源
 * @author zys
 * @date 2022/3/16 17:56
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ApiPageResourcesParamVO extends ApiResourcesParamVO{
  /**
   * 分页信息
   */
  private Pagination pagination;

  /**
   * 资源（API）名称
   */
  private String name;
}