package com.qk.dam.catacollect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zys
 * @date 2022/4/14 20:26
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataConnectInfoVo {
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
  /**
   * 表名称
   */
  private List<String> tableList;

  /**
   * 全选字段
   */
  private String allNums;

  /**采集元数据策略（1：仅更新、2：仅添加、3：既更新又添加、4：忽略更新添加）*/
  private String strategy;

  /**
   * atals用户名、密码（使用都好分隔）
   */
  private String auth;

  /**
   * atals操作ip
   */
  private String atalsServer;

}