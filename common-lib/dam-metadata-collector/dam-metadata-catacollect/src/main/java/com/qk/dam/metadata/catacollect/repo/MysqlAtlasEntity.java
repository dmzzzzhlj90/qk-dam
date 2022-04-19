package com.qk.dam.metadata.catacollect.repo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.google.common.collect.Lists;
import com.qk.dam.metadata.catacollect.adapter.MysqlMetadata;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import com.qk.dam.metadata.catacollect.pojo.mysql.MysqlColumnType;
import com.qk.dam.metadata.catacollect.pojo.mysql.MysqlDbType;
import com.qk.dam.metadata.catacollect.pojo.mysql.MysqlTableType;
import com.qk.dam.metadata.catacollect.util.SourcesUtil;
import com.qk.dam.metadata.catacollect.util.Util;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取mysql元数据信息
 * @author zys
 * @date 2022/4/15 10:25
 * @since 1.0.0
 */
public class MysqlAtlasEntity {
  public static final String TABLE_COMMENT = "table_comment";
  private static final String TABLE_NAME = "table_name";
  private final Db use;
  private final String db;
  private final String host;
  private final String allNums;
  private final List<String> tableList;
  private final String applicationName;
  private final String description;
  private final String displayName;
  private final String owner;
  public MysqlAtlasEntity(MetadataConnectInfoVo metadataConnectInfoVo) {
    this.use =
        Db.use(
            new SimpleDataSource(
                "jdbc:mysql://"
                    +metadataConnectInfoVo.getServer()
                    + ":"
                    +metadataConnectInfoVo.getPort()
                    + "/information_schema",
                metadataConnectInfoVo.getUserName(),
                metadataConnectInfoVo.getPassword()));
    db = metadataConnectInfoVo.getDb();
    allNums= metadataConnectInfoVo.getAllNums();
    tableList=metadataConnectInfoVo.getTableList();
    host=metadataConnectInfoVo.getServer();
    applicationName=metadataConnectInfoVo.getApplicationName();
    description=metadataConnectInfoVo.getDescription();
    displayName=metadataConnectInfoVo.getDisplayName();
    owner=metadataConnectInfoVo.getOwner();
  }

  public List<AtlasEntity.AtlasEntitiesWithExtInfo> searchMysqlAtals(
      List<AtlasEntity.AtlasEntitiesWithExtInfo> list) throws SQLException {
    List<Entity> tableList = getTableEntity();
    if (CollectionUtil.isNotEmpty(tableList)){
      List<List<Entity>> entityList = Lists.partition(tableList, 2);
      list=getAtlasMessage(entityList);
    }

    return list;
  }

  //根据输入条件获取表信息
  private List<Entity> getTableEntity() throws SQLException {
    List <Entity> list = new ArrayList<>();
    if (StringUtils.isNotEmpty(allNums) && allNums.equals(SourcesUtil.TABLE_NUMS)){
        list = use.query("select * from TABLES im where im.table_schema=?", db);
    }else {
      if (CollectionUtil.isNotEmpty(tableList)){
        String s = Util.convertListToString(tableList);
        list = use.query("select * from TABLES im where im.TABLE_NAME in"+"("+s+")");
      }
    }
    return list;
  }
  //获取元数据信息
  private List<AtlasEntity.AtlasEntitiesWithExtInfo> getAtlasMessage(
      List<List<Entity>> entityList) {
    List<AtlasEntity.AtlasEntitiesWithExtInfo> list = new ArrayList<>();
      entityList.forEach(entities ->{
            AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo = extractorAtlasEntitiesWith(entities);
            list.add(atlasEntitiesWithExtInfo);
      });
      return list;
  }
  //获取元数据抽取类->获取元数据实体
  private AtlasEntity.AtlasEntitiesWithExtInfo extractorAtlasEntitiesWith(
      List<Entity> entityList) {
    MysqlMetadata mysqlMetadata = new MysqlMetadata();
    return mysqlMetadata.syncMysqlMetadata(List.of(mysqlDbType(entityList)));
  }

  private MysqlDbType mysqlDbType(List<Entity> entityList) {
    // TODO 数据库信息从数据应用系统名录获得

    MysqlDbType mysqlDbType =
        MysqlDbType.builder()
            .applicationName(applicationName)
            .serverInfo(host)
            .name(db)
            .owner(owner)
            .description(description)
            .displayName(displayName)
            .qualifiedName(db + "@" + host)
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
                          .owner(owner)
                          .description(entity.getStr(TABLE_COMMENT))
                          .displayName(entity.getStr(TABLE_COMMENT))
                          .qualifiedName(db + "." + entity.getStr(TABLE_NAME) + "@" + host)
                          .build();
                  try {
                    List<Entity> column =
                        use.query(
                            "select * from COLUMNS where table_schema=?  and table_name=?",
                            db,
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
                                        .owner(owner)
                                        .qualifiedName(
                                            db
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