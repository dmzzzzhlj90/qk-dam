package com.qk.dm.authority.vo.params;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 根据用户、角色、分组id分页查询授权信息入参
 * @author zys
 * @date 2022/3/29 11:12
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpowerQueryVO {
  /**
   * 用户、角色、分组的id
   */
  @NotBlank(message = "用户或角色或分组的id不能为空")
  private String id;

  /**
   * 分页信息
   */
  private Pagination pagination;
}