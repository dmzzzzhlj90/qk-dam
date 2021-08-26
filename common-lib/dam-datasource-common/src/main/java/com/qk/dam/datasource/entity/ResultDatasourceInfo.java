package com.qk.dam.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 数据源返回结果集 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDatasourceInfo {
  /** 数据源主键id */
  private Integer id;

  /** 系统名称 */
  private String dataSourceName;

  /** 所属系统 */
  private String homeSystem;

  /** 数据源连接返回信息 */
  private ConnectBasicInfo connectBasicInfo;

  /** 连接方式 */
  private String dbType;

  /** 状态设置状态值 */
  private Integer status;
}
