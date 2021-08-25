package com.qk.dm.datasource.connect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据源主类连接信息
 *
 * @author zys
 * @date 2021/8/23 16:54
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DSConnectBasicInfo {

  /** 数据源连接服务地址 */
  private String server;

  /** 端口 */
  private String port;

  /** 用户名 */
  private String userName;

  /** 密码 */
  private String password;
}
