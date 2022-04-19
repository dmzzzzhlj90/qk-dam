package com.qk.dam.indicator.common.sqlbuilder.sqlparser;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.dialect.blink.parser.BlinkStatementParser;
import com.alibaba.druid.sql.dialect.clickhouse.parser.ClickhouseStatementParser;
import com.alibaba.druid.sql.dialect.db2.parser.DB2StatementParser;
import com.alibaba.druid.sql.dialect.h2.parser.H2StatementParser;
import com.alibaba.druid.sql.dialect.hive.parser.HiveStatementParser;
import com.alibaba.druid.sql.parser.SQLParserFeature;
import com.alibaba.druid.sql.parser.SQLStatementParser;

/**
 * SQL校验工厂
 *
 * @author wangzp
 * @date 2021/9/15 14:38
 * @since 1.0.0
 */
public class SqlParserFactory {

  public static boolean parseStatements(String sql, DbType dbType) {
    try {
      return SQLUtils.parseStatements(sql, dbType).size() > 0;
    } catch (Exception e) {
      // SQL格式错误
      return false;
    }
  }

  public static SQLStatementParser createSQLStatementParser(
      String sql, DbType dbType, SQLParserFeature... features) {
    if (dbType == null) {
      dbType = DbType.other;
    }

    switch (dbType) {
      case mysql:
      case hive:
        return new HiveStatementParser(sql, features);
      case clickhouse:
        return new ClickhouseStatementParser(sql);
      case blink:
        return new BlinkStatementParser(sql, features);
      case db2:
        return new DB2StatementParser(sql, features);
      case h2:
        return new H2StatementParser(sql, features);
      default:
        return new SQLStatementParser(sql, dbType);
    }
  }

  public static SQLStatementParser createSQLStatementParser(String sql, DbType dbType) {
    SQLParserFeature[] features;
    if (DbType.odps == dbType || DbType.mysql == dbType) {
      features = new SQLParserFeature[] {SQLParserFeature.KeepComments};
    } else {
      features = new SQLParserFeature[] {};
    }
    return createSQLStatementParser(sql, dbType, features);
  }

  public static SQLStatementParser createSQLStatementParser(
      String sql, DbType dbType, boolean keepComments) {
    SQLParserFeature[] features;
    if (keepComments) {
      features = new SQLParserFeature[] {SQLParserFeature.KeepComments};
    } else {
      features = new SQLParserFeature[] {};
    }

    return createSQLStatementParser(sql, dbType, features);
  }

  public static SQLStatementParser createSQLStatementParser(
      String sql, String dbType, SQLParserFeature... features) {
    return createSQLStatementParser(sql, dbType == null ? null : DbType.valueOf(dbType), features);
  }

  public static void main(String[] args) {
    System.out.println(parseStatements("SELECT sum() FROM gvcup4 WHERE ()name234", DbType.hive));
  }
}
