package com.qk.dam.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zys
 * @date 2022/4/14 16:19
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectInfoVo {
  /**应用名称*/
  private String applicationName;
  /**描述*/
  private String description;
  /**显示名称*/
  private String displayName;
  /**创建人名称*/
  private String owner;

  /** 数据源类型 */
  private  String type;

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

  /**
   * 数据库名称
   * @return
   */
  private String databaseName;
}