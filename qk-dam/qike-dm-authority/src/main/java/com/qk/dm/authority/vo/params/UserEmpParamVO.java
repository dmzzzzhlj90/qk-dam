package com.qk.dm.authority.vo.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author zys
 * @date 2022/3/15 15:07
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEmpParamVO {
  /**
   * 域名称
   */
  @NotBlank(message = "域名称不能为空")
  private String realm;
  /**
   * 用户id
   */
  @NotBlank(message = "用户id不能为空")
  private String userId;

  /**
   * 客户端id(客户端编号)
   */
  @NotBlank(message = "客户端id(客户端编号)不能为空")
  private String clientId;
}