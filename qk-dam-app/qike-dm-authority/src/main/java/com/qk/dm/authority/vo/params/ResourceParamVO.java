package com.qk.dm.authority.vo.params;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 根据服务id查询资源（API）
 * @author zys
 * @date 2022/2/24 18:17
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ResourceParamVO extends ApiResourcesParamVO{
  /**
   * 服务名称（查询资源为必填条件）
   */
  @NotBlank(message = "服务名称不能为空")
  private String name;
}