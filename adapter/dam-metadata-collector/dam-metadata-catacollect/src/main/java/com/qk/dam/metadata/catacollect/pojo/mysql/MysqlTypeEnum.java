package com.qk.dam.metadata.catacollect.pojo.mysql;

import org.apache.atlas.model.instance.AtlasEntity;

public enum MysqlTypeEnum {
  /** DB TYPE */
  MYSQL_DB("mysql_db"),
  /** MYSQL_TABLE TYPE */
  MYSQL_TABLE("mysql_table"),
  /** MYSQL_COLUMN TYPE */
  MYSQL_COLUMN("mysql_column"),
  MYSQL_INDEX("mysql_index"),
  MYSQL_FOREIGN_KEY("mysql_foreign_key"),
  MYSQL_PROCESS("mysql_process"),
  MYSQL_COLUMN_LINEAGE("mysql_column_lineage"),
  ;

  MysqlTypeEnum(String name) {
    this.name = name;
  }

  private final String name;

  public AtlasEntity getAtlasEntity() {
    return new AtlasEntity(name);
  }

  public String getName() {
    return name;
  }
}
