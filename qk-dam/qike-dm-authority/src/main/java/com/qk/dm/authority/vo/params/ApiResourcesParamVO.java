package com.qk.dm.authority.vo.params;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 查询api数据信息
 * @author zys
 * @date 2022/3/3 10:35
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResourcesParamVO {
  /**
   * 服务id
   */
  @NotBlank(message = "服务id不能为空")
  private Long serviceId;
}