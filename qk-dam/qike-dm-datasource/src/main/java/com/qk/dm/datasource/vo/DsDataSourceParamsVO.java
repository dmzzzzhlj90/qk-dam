package com.qk.dm.datasource.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zys
 * @date 2021/8/2 11:29
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsDataSourceParamsVO {
  /** 分页信息 */
  private Pagination pagination;

  /** 数据连接名称 */
  private String dataSourceName;

  /** 数据源连接类型 */
  private String linkType;

  /** 数据源连接目录层级ID */
  private String dicId;

  /** 开始时间 */
  private String beginDay;

  /** 结束时间 */
  private String endDay;
}
