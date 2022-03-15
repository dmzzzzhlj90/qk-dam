package com.qk.dm.authority.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zys
 * @date 2022/3/15 15:10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserEmpPowerParamVO extends UserEmpParamVO {
  /**
   * 服务id
   */
  private String serviceId;
}