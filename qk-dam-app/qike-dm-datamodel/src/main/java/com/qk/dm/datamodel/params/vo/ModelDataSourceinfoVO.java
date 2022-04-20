package com.qk.dm.datamodel.params.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据连接类型、数据源名称
 * @author zys
 * @date 2022/1/20 14:55
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDataSourceinfoVO{
  /**
   * 数据连接
   */
  private String dataConnection;
  /**
   *数据源连接名称
   */
  private String dataSourceName;
}