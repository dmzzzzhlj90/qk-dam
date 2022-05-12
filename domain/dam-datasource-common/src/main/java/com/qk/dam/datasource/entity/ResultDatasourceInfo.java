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

  /**
   * 数据源标识id
   */
  private String dataSourceCode;

  /** 系统名称 */
  private String dataSourceName;

  /** 所属系统 */
  private String homeSystem;

  /** 数据源连接返回JSON信息 */
  private String connectBasicInfoJson;

  /** 连接方式 */
  private String dbType;

  /** 状态设置状态值 */
  private Integer status;
}
