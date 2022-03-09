package com.qk.dm.authority.vo.params;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 *授权信息查询入参
 * @author zys
 * @date 2022/2/25 17:40
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpowerParamVO {
  /**
   * 分页信息
   */
  private Pagination pagination;

  /**
   * 被授权主体名称
   */
  private String empoerName;

  /**
   * 服务uuid
   */
  @NotBlank(message = "服务uuid不能为空")
  private String serviceId;
}