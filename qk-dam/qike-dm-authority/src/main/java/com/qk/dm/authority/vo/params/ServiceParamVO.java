package com.qk.dm.authority.vo.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 权限管理-服务查询入参
 * @author zys
 * @date 2022/2/24 16:10
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceParamVO {
  /**
   * 服务名称
   */
  private String serviceName;

  /**
   * 区域id（项目id）
   */
  @NotBlank(message = "区域id不能为空")
  private String redionId;
}