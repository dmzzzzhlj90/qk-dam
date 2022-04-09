package com.qk.dm.authority.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 查询用户入参
 * @author zys
 * @date 2022/2/23 15:56
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserRoleParamVO {
  /**
   * 用户id
   */
  @NotBlank(message = "用户id必填！")
  String userId;
  /**
   * 域
   */
  @NotBlank(message = "域必填！")
  String realm;
  /**
   * 客户端-客户端的clientId
   */
  @NotBlank(message = "客户端的clientId必填！")
  String client_clientId;
}