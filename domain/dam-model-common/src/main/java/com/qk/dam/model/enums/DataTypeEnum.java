package com.qk.dam.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 关系建模-数据格式枚举
 */
public enum DataTypeEnum {
  Parquet("Parquet"),
  JSON("JSON"),
  CSV("CSV"),
  AVRO("AVRO");

  /** 定义一个public修饰的实例变量 */
  private String name;

  DataTypeEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static DataTypeEnum locateEnum(String name) {

    for (DataTypeEnum connTypeEnum : values()) {
      if (connTypeEnum.getName().equalsIgnoreCase(name)) {
        return connTypeEnum;
      }
    }
    throw new IllegalArgumentException("未知的枚举类型：" + name);
  }

  public static List<String> getDataTypeName() {
    List<String> connTypeNameList = new ArrayList<>();

    for (DataTypeEnum connTypeEnum : values()) {
      connTypeNameList.add(connTypeEnum.getName());
    }
    return connTypeNameList;
  }
}
