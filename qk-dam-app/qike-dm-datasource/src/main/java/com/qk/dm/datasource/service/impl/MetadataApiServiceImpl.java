package com.qk.dm.datasource.service.impl;

import cn.hutool.db.Entity;
import com.qk.dam.datasource.entity.ConnectInfoVo;
import com.qk.dam.datasource.utils.SourcesUtil;
import com.qk.dm.datasource.repo.HiveDbToTableAgg;
import com.qk.dm.datasource.repo.MysqlDbToTableAgg;
import com.qk.dm.datasource.service.MetadataApiService;
import com.qk.dm.datasource.util.CatacollectUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zys
 * @date 2022/4/18 16:40
 * @since 1.0.0
 */
@Service
public class MetadataApiServiceImpl implements MetadataApiService {
  /**
   * 根据数据源连接获取库信息
   * @param connectInfoVo
   * @return
   */
  public  List<String> queryDB(ConnectInfoVo connectInfoVo){
    List<Entity> list = new ArrayList<>();
    List<String> dbList = new ArrayList<>();
    if (connectInfoVo.getType()!=null){
      switch (connectInfoVo.getType()){
        case SourcesUtil.MYSQL:
          list =new MysqlDbToTableAgg(connectInfoVo).searchMysqlDB(list);
          dbList = CatacollectUtil.getDbResult(list,SourcesUtil.MYSQL);
          break;
        case SourcesUtil.HIVE:
          list = new HiveDbToTableAgg(connectInfoVo).searchHiveDB(list);
          dbList = CatacollectUtil.getDbResult(list,SourcesUtil.HIVE);
          break;
        default:
          break;
      }
    }
    return dbList;
  }

  /**
   * 根据库和连接信息获取表信息
   * @param connectInfoVo
   * @return
   */
  public List<String> queryTable(ConnectInfoVo connectInfoVo){
    List<Entity> list = new ArrayList<>();
    List<String> dbList = new ArrayList<>();
    if (connectInfoVo.getType()!=null){
      switch (connectInfoVo.getType()){
        case SourcesUtil.MYSQL:
          list =new MysqlDbToTableAgg(connectInfoVo).searchMysqlTable(list);
          dbList = CatacollectUtil.getTableResult(list,SourcesUtil.MYSQL);
          break;
        case SourcesUtil.HIVE:
          list = new HiveDbToTableAgg(connectInfoVo).searchHiveTable(list);
          dbList = CatacollectUtil.getTableResult(list,SourcesUtil.HIVE);
          break;
        default:
          break;
      }
    }
    return dbList;
  }
}