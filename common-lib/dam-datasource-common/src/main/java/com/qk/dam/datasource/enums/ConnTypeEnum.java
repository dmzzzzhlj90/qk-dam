package com.qk.dam.datasource.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源连接类型
 *
 * @author weijunqi
 */
public enum ConnTypeEnum {

  /*数据连接枚举信息*/
  MYSQL("mysql"),
  ORACLE("oracle"),
  DB2("db2"),
  SQLSERVER("sqlserver"),
  HIVE("hive"),
  POSTGRESQL("postgresql"),
  ELASTICSEARCH("elasticsearch"),
  REDIS("redis"),
  EXCEL("excel"),
  CSV("csv"),
  REST("rest");

  /** 定义一个public修饰的实例变量 */
  private String name;

  ConnTypeEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static ConnTypeEnum locateEnum(String name) {

    for (ConnTypeEnum connTypeEnum : values()) {
      if (connTypeEnum.getName().equalsIgnoreCase(name)) {
        return connTypeEnum;
      }
    }
    throw new IllegalArgumentException("未知的枚举类型：" + name);
  }

  public static List<String> getConnTypeName() {
    List<String> connTypeNameList = new ArrayList<>();

    for (ConnTypeEnum connTypeEnum : values()) {
      connTypeNameList.add(connTypeEnum.getName());
    }
    return connTypeNameList;
  }
}
