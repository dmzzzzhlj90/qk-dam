package com.qk.dam.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据源连接信息
 *
 * @author wjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectBasicInfo {

  /** 数据源连接服务地址 */
  private String server;

  /** 端口 */
  private String port;

  /** 用户名 */
  private String userName;

  /** 密码 */
  private String password;
}
