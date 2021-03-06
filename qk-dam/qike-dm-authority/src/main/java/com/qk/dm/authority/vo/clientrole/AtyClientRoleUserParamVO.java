package com.qk.dm.authority.vo.clientrole;

import com.qk.dam.jpa.pojo.Pagination;
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
public class AtyClientRoleUserParamVO {
  /**
   * 域
   */
  @NotBlank(message = "域必填!")
  String realm;
  /**
   * 角色名称
   */
  @NotBlank(message = "角色名称必填!")
  String name;
  /**
   * 客户端表id
   */
  @NotBlank(message = "客户端id必填!")
  String client_id;
  /**
   * 分页信息
   */
  private Pagination pagination;
}