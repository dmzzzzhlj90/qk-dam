package com.qk.dam.metadata.catacollect.pojo.mysql;

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
