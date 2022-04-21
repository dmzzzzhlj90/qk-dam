package com.qk.dam.metadata.catacollect.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Atals
 * @author zhuyunsan
 */
public enum AtalsEnum {
  /*查询Atlas数据库配置*/
  MYSQL("mysql","mysql_db"),
  HIVE("hive","hive_db"),
  ;

  private String type;
  private String value;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  AtalsEnum(String type, String value) {
    this.type = type;
    this.value = value;
  }


  public static AtalsEnum fromValue(String type) {
    for (AtalsEnum b : AtalsEnum.values()) {
      if (b.type.equals(type)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected type '" + type + "'");
  }

  public static Map<String, String> getAllValue() {
    Map<String, String> val = new HashMap<>();
    for (AtalsEnum enums : AtalsEnum.values()) {
      val.put(enums.type, enums.value);
    }
    return val;
  }
}
