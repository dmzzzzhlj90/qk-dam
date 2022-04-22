package com.qk.dam.datasource.entity;

import lombok.Data;

/**
 * 数据源连接信息
 *
 * @author wjq
 */
@Data
public class ConnectBasicInfo {
  /** 连接驱动 */
  private String driverInfo;
  /** 数据源连接服务地址 */
  private String server;

  /** 数据源连接服务地址 */
  private String hiveServer2;

  /** 端口 */
  private String port;

  /** 用户名 */
  private String userName;

  /** 密码 */
  private String password;
}
