package com.qk.dam.rdbmsl2atlas.adapter;

import static org.apache.atlas.type.AtlasTypeUtil.toAtlasRelatedObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Lists;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.commons.collections.CollectionUtils;

public class MysqlMetadata {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final TypeFactory TYPE_FACTORY = OBJECT_MAPPER.getTypeFactory();

  public AtlasEntity.AtlasEntitiesWithExtInfo syncMysqlMetadata(List<MysqlDbType> mysqlDbTypes) {
    var entitiesWithExtInfo = new AtlasEntity.AtlasEntitiesWithExtInfo();
    mysqlDbTypes.stream()
        .map(this::convertAtlasEntity)
        .flatMap(Collection::stream)
        .forEach(entitiesWithExtInfo::addEntity);
    return entitiesWithExtInfo;
  }

  /**
   * 获取元数据实体
   *
   * @param mysqlDbType db
   * @return List<AtlasEntity>
   */
  private List<AtlasEntity> convertAtlasEntity(MysqlDbType mysqlDbType) {
    // 创建DB 实体
    Map<String, Object> dbAttr =
        OBJECT_MAPPER.convertValue(
            mysqlDbType, TYPE_FACTORY.constructMapType(Map.class, String.class, Object.class));
    final var db = MysqlTypeEnum.MYSQL_DB.getAtlasEntity();
    db.setAttributes(dbAttr);
    List<AtlasEntity> tmplst = Lists.newArrayList(db);

    mysqlDbType.getMysqlTableTypes().stream()
        .map(this::convertAtlasEntity)
        .forEach(
            mysqlTableType -> {
              AtlasEntity tableEntity = mysqlTableType.getTableEntity();
              // 配置表与DB的映射关系
              tableEntity.setRelationshipAttribute(
                  MysqlRelationshipTypeEnum.DB.getName(), toAtlasRelatedObjectId(db));
              List<AtlasEntity> columnEntity = mysqlTableType.getColumnEntity();
              tmplst.add(tableEntity);
              if (CollectionUtils.isNotEmpty(columnEntity)) {
                tmplst.addAll(columnEntity);
              }
            });

    return tmplst;
  }

  private MysqlTableType convertAtlasEntity(MysqlTableType mysqlTableType) {
    // 创建TABLE 实体
    Map<String, Object> tbAttr =
        OBJECT_MAPPER.convertValue(
            mysqlTableType, TYPE_FACTORY.constructMapType(Map.class, String.class, Object.class));
    final var table = MysqlTypeEnum.MYSQL_TABLE.getAtlasEntity();
    table.setAttributes(tbAttr);

    if (CollectionUtils.isNotEmpty(mysqlTableType.getMysqlColumnTypes())) {
      List<AtlasEntity> entities =
          mysqlTableType.getMysqlColumnTypes().stream()
              .map(this::convertAtlasEntity)
              .map(
                  mysqlColumnType -> {
                    // 配置列与表的关系
                    AtlasEntity columnEntity = mysqlColumnType.getColumnEntity();
                    columnEntity.setRelationshipAttribute(
                        MysqlRelationshipTypeEnum.TABLE.getName(), toAtlasRelatedObjectId(table));
                    // 返回实体
                    return columnEntity;
                  })
              .collect(Collectors.toList());
      mysqlTableType.setColumnEntity(entities);
    }

    mysqlTableType.setTableEntity(table);

    return mysqlTableType;
  }

  private MysqlColumnType convertAtlasEntity(MysqlColumnType mysqlColumnType) {
    // 创建COLUMN 实体
    Map<String, Object> colAttr =
        OBJECT_MAPPER.convertValue(
            mysqlColumnType, TYPE_FACTORY.constructMapType(Map.class, String.class, Object.class));

    var column = MysqlTypeEnum.MYSQL_COLUMN.getAtlasEntity();
    column.setAttributes(colAttr);
    mysqlColumnType.setColumnEntity(column);
    return mysqlColumnType;
  }
}
