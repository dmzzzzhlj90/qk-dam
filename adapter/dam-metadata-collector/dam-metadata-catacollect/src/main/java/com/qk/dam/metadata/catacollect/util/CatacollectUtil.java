package com.qk.dam.metadata.catacollect.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/4/14 20:04
 * @since 1.0.0
 */
public class CatacollectUtil {

  public static String convertListToString(List<String> lists) {
    StringBuffer sb = new StringBuffer();
    if(lists !=null){
      for (int i=0;i<lists.size();i++) {
        if(i==0){
          sb.append("'").append(lists.get(i)).append("'");
        }else{
          sb.append(",").append("'").append(lists.get(i)).append("'");
        }
      }
    }
    return sb.toString();
  }

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