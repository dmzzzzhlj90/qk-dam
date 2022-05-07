package com.qk.dam.datasource.utils;

/**
 * @author zys
 * @date 2021/12/15 11:43
 * @since 1.0.0
 */
public final class SourcesUtil {
  /**
   * 数据库类型
   */
  public static final String MYSQL="mysql";
  public static final String HIVE="hive";

  /**
   * 系统使用常量
   */
  public static final String TABLE_NUMS="all";
  public static final String TABLE_NAME_ALL="全部表名";

  /**
   * MYSQL数据库
   */
  public static final String MYSQL_SCHEMA = "table_schema";
  public static final String MYSQL_NAME = "table_name";
  /**
   * HIVE数据库
   */
  public static final String HIVE_SCHEMA = "database_name";
  public static final String HIVE_NAME = "tab_name";
}