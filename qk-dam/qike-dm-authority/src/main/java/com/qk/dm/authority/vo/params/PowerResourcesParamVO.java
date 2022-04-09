package com.qk.dm.authority.vo.params;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 权限
 * @author zys
 * @date 2022/3/3 10:38
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PowerResourcesParamVO extends ApiResourcesParamVO{
  /**
   * 资源（API）标识编码-(当授权页面使用不能为空)
   */
  @NotBlank(message = "资源（API）标识编码不能为空")
  private String resourceSign;

  /**
   * 0查询API,1查询资源(当授权页面使用不能为空)
   */
  @NotNull(message = "0查询API,1查询资源不能为空")
  private Integer type;
}