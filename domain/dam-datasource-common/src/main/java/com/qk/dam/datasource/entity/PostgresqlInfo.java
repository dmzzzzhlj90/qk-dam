package com.qk.dam.datasource.entity;

import lombok.*;

/**
 * 数据源连接信息_POSTGRESQL
 *
 * @author wjq
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PostgresqlInfo extends ConnectBasicInfo {
  /** 数据源类型 */
//  private final String type = ConnTypeEnum.POSTGRESQL.getName();
}
