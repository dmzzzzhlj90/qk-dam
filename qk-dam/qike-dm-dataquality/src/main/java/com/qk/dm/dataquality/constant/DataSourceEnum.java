package com.qk.dm.dataquality.constant;

/** @author shenpengjie */
public enum DataSourceEnum {
  // 数据源
  CALCULATE_ENGINE_HIVE(1, "HIVE"),
  CALCULATE_ENGINE_MYSQL(2, "MYSQL");

  private Integer code;
  private String name;

  DataSourceEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static String fromValue(Integer code) {
    for (DataSourceEnum b : DataSourceEnum.values()) {
      if (b.code.equals(code)) {
        return b.getName();
      }
    }
    throw new IllegalArgumentException("Unexpected id '" + code + "'");
  }

  public static Integer fromValue(String name) {
    for (DataSourceEnum b : DataSourceEnum.values()) {
      if (b.name.equals(name)) {
        return b.getCode();
      }
    }
    return null;
  }

  public Integer getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
