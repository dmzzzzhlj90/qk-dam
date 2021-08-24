package com.qk.dm.datasource.datasourinfo;

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
public class BaseDataSourceTypeInfo {
  /** 数据库连接server */
  private String service;
}
