package com.qk.dam.rdbmsl2atlas.repo;

import cn.hutool.core.lang.Assert;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.qk.dam.rdbmsl2atlas.pojo.MysqlDataConnectYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.ServerinfoYamlVO;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlColumnType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlTableType;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 元数据管理-MySQL类型数据聚合
 *
 * @author daomingzhu
 */
public class MysqlTypeAgg {
  public static final String TABLE_COMMENT = "table_comment";
  private static final String TABLE_NAME = "table_name";
  private final Db use;
  private final String host;
  private final String db;
  private final String table;

  public MysqlTypeAgg(MysqlDataConnectYamlVO mysqlDataConnectYamlVO) {
    this.use =
        Db.use(
            new SimpleDataSource(
                "jdbc:mysql://"
                    + mysqlDataConnectYamlVO.getHost()
                    + ":"
                    + mysqlDataConnectYamlVO.getPort()
                    + "/information_schema",
                mysqlDataConnectYamlVO.getUsername(),
                mysqlDataConnectYamlVO.getPassword()));
    host = mysqlDataConnectYamlVO.getHost();
    db = mysqlDataConnectYamlVO.getDb();
    table = mysqlDataConnectYamlVO.getTable();
  }

  public List<Entity> searchMedataByDb() {
    List<Entity> entityList = null;
    String inTbs =
        Stream.of(table.split(",")).map(t -> "'" + t + "'").collect(Collectors.joining(","));

    try {
      Entity tables =
          Entity.create("TABLES").set("table_schema", db).set(TABLE_NAME, "in" + "(" + inTbs + ")");

      entityList = use.find(tables);
      Assert.notEmpty(
          entityList, "未查询到符合条件的db实体，请检查yml配置，错误信息db【{}】table【{}】serverinfo【{}】", db, table);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return entityList;
  }

  public List<Entity> getEntityList() {
    List<Entity> entityList = null;
    if (table.equals("all")) {
      try {
        entityList = use.query("select * from TABLES im where im.table_schema=?", db);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    if (table.startsWith("%") || table.endsWith("%")) {
      try {
        Entity tables =
            Entity.create("TABLES").set("table_schema", db).set(TABLE_NAME, "like " + table);

        entityList = use.find(tables);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    Assert.notEmpty(
        entityList, "未查询到符合条件的db实体，请检查yml配置，错误信息db【{}】table【{}】serverinfo【{}】", db, table);
    return entityList;
  }

  public MysqlDbType searchPatternTMedataByDb(
      List<Entity> entityList, ServerinfoYamlVO serverinfoYamlVO) {
    return getMysqlDbType(db, serverinfoYamlVO, entityList);
  }

  private MysqlDbType getMysqlDbType(
      String database, ServerinfoYamlVO serverinfoYamlVO, List<Entity> entityList) {
    // TODO 数据库信息从数据应用系统名录获得

    MysqlDbType mysqlDbType =
        MysqlDbType.builder()
            .applicationName(serverinfoYamlVO.getApplicationName())
            .serverInfo(host)
            .name(database)
            .owner(serverinfoYamlVO.getOwner())
            .description(serverinfoYamlVO.getDescription())
            .displayName(serverinfoYamlVO.getDisplayName())
            .qualifiedName(database + "@" + host)
            .build();

    List<MysqlTableType> mysqlTableTypes =
        entityList.stream()
            .map(
                entity -> {
                  MysqlTableType mysqlTableType =
                      MysqlTableType.builder()
                          .name(entity.getStr(TABLE_NAME))
                          .type(entity.getStr("table_type"))
                          .dataLength(entity.getLong("data_length"))
                          .indexLength(entity.getLong("index_length"))
                          .createTime(entity.getLong("create_time"))
                          .updateTime(entity.getLong("update_time"))
                          .tableCollation(entity.getStr("table_collation"))
                          .tableRows(entity.getStr("table_rows"))
                          .comment(entity.getStr(TABLE_COMMENT))
                          .owner(serverinfoYamlVO.getOwner())
                          .description(entity.getStr(TABLE_COMMENT))
                          .displayName(entity.getStr(TABLE_COMMENT))
                          .qualifiedName(database + "." + entity.getStr(TABLE_NAME) + "@" + host)
                          .build();
                  try {
                    List<Entity> column =
                        use.query(
                            "select * from COLUMNS where table_schema=?  and table_name=?",
                            database,
                            mysqlTableType.getName());

                    List<MysqlColumnType> columnTypeList =
                        column.stream()
                            .map(
                                colEntity ->
                                    MysqlColumnType.builder()
                                        .name(colEntity.getStr("column_name"))
                                        .position(colEntity.getInt("ordinal_position"))
                                        .isNullable(colEntity.getStr("is_nullable").equals("YES"))
                                        .isPrimaryKey(
                                            colEntity.getStr("column_key") != null
                                                && colEntity.getStr("column_key").equals("PRI"))
                                        .data_type(colEntity.getStr("column_type"))
                                        .extra(colEntity.getStr("extra"))
                                        .default_value(colEntity.getStr("column_default"))
                                        .displayName(colEntity.getStr("column_comment"))
                                        .description(colEntity.getStr("column_comment"))
                                        .owner(serverinfoYamlVO.getOwner())
                                        .qualifiedName(
                                            database
                                                + "."
                                                + mysqlTableType.getName()
                                                + "."
                                                + colEntity.getStr("column_name")
                                                + "@"
                                                + host)
                                        .build())
                            .collect(Collectors.toList());
                    mysqlTableType.setMysqlColumnTypes(columnTypeList);
                  } catch (SQLException throwables) {
                    throwables.printStackTrace();
                  }

                  return mysqlTableType;
                })
            .collect(Collectors.toList());

    mysqlDbType.setMysqlTableTypes(mysqlTableTypes);
    return mysqlDbType;
  }
}
