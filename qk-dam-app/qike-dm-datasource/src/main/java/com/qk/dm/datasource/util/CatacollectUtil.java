package com.qk.dm.datasource.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Entity;
import com.qk.dam.catacollect.util.SourcesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/4/14 20:04
 * @since 1.0.0
 */
public class CatacollectUtil {

  public static List<String> getDbResult(List<Entity> list, String type) {
    List<String> dbList = new ArrayList<>();
    if (CollectionUtil.isNotEmpty(list)){
      switch (type){
        case SourcesUtil.MYSQL:
          dbList = list.stream().map(entity -> (String)entity.get(SourcesUtil.MYSQL_SCHEMA)).collect(
              Collectors.toList());
          break;
        case SourcesUtil.HIVE:
          dbList = list.stream().map(entity -> (String)entity.get(SourcesUtil.HIVE_SCHEMA)).collect(
              Collectors.toList());
          break;
        default:
          break;
      }
    }
    return dbList;
  }

  public static List<String> getTableResult(List<Entity> list, String type) {
    List<String> tableList = new ArrayList<>();
    if (CollectionUtil.isNotEmpty(list)) {
      switch (type) {
        case SourcesUtil.MYSQL:
          tableList = list.stream().map(entity -> (String) entity.get(SourcesUtil.MYSQL_NAME))
              .collect(Collectors.toList());
          break;
        case SourcesUtil.HIVE:
          tableList = list.stream().map(entity -> (String) entity.get(SourcesUtil.HIVE_NAME))
              .collect(Collectors.toList());
          break;
        default:
          break;
      }
    }
    return tableList;
  }
}