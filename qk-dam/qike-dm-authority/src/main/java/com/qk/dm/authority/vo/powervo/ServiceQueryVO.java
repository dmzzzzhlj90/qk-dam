package com.qk.dm.authority.vo.powervo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**根据用户id查询授权服务信息
 * @author zys
 * @date 2022/4/1 15:20
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceQueryVO {
  /**
   * id(服务的uuid)
   */
  private String id;

  /**
   * 服务名称
   */
  private String name;
  /**
   * 微服务线上地址
   */
  private String entry;

  /**
   * 微服务项目名
   */
  private String path;

  /**
   * 微应用
   */
  private String microApp;
}