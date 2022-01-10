package com.qk.dam.datasource.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源连接驱动枚举
 * @author zhuyunsan
 */
public enum DataSourceEnum {
  /*数据连接枚举信息*/
  MYSQL("mysql","com.mysql.cj.jdbc.Driver","jdbc:mysql://"),
  ORACLE("oracle","oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:"),
  HIVE("hive","org.apache.hive.jdbc.HiveDriver","jdbc:hive2://"),
  POSTGRESQL("postgresql","org.postgresql.Driver","jdbc:postgresql://"),
  ELASTICSEARCH("elasticsearch","org.elasticsearch.xpack.sql.jdbc.EsDriver","jdbc:es://");

  private String name;
  private String driver;
  private String cold;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCold() {
    return cold;
  }

  public void setCold(String cold) {
    this.cold = cold;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  DataSourceEnum(String name, String driver, String cold) {
    this.name = name;
    this.driver = driver;
    this.cold = cold;
  }

  public static DataSourceEnum fromValue(String name) {
    for (DataSourceEnum b : DataSourceEnum.values()) {
      if (b.name.equals(name)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected type '" + name + "'");
  }

  public static Map<String, String> getAllValue() {
    Map<String, String> val = new HashMap<>();
    for (DataSourceEnum enums : DataSourceEnum.values()) {
      val.put(enums.name, enums.cold);
    }
    return val;
  }
}
