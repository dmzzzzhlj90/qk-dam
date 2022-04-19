package com.qk.dm.authority.vo.powervo;

import lombok.*;

/**
 * 用户、用户组、角色授权详情
 * @author zys
 * @date 2022/3/10 10:34
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EmpowerAllVO extends EmpowerVO {
  /**
   * 服务名称
   */
  private String serviceName;
}