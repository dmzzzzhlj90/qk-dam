package com.qk.dam.rdbmsl2atlas.pojo.mysql;

public enum MysqlRelationshipTypeEnum {
  DB("db"),
  TABLE("table");

  MysqlRelationshipTypeEnum(String name) {
    this.name = name;
  }

  private final String name;

  public String getName() {
    return name;
  }
}
