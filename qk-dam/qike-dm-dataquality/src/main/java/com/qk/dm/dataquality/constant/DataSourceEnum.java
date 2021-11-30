package com.qk.dm.dataquality.constant;

import com.alibaba.druid.DbType;

/** @author shenpengjie */
public enum DataSourceEnum {
  // 数据源
  CALCULATE_ENGINE_HIVE(1, "HIVE", DbType.hive),
  CALCULATE_ENGINE_MYSQL(2, "MYSQL", DbType.mysql);

  private Integer code;
  private String name;
  private DbType dbType;

  DataSourceEnum(Integer code, String name, DbType dbType) {
    this.code = code;
    this.name = name;
    this.dbType = dbType;
  }

  public static DataSourceEnum fromValue(Integer code) {
    for (DataSourceEnum b : DataSourceEnum.values()) {
      if (b.code.equals(code)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected id '" + code + "'");
  }

  public Integer getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public DbType getDbType() {
    return dbType;
  }
}
