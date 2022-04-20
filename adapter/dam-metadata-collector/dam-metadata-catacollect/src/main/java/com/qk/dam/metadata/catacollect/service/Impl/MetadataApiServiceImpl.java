package com.qk.dam.metadata.catacollect.service.Impl;

import cn.hutool.db.Entity;
import com.qk.dam.metadata.catacollect.pojo.ConnectInfoVo;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import com.qk.dam.metadata.catacollect.repo.HiveAtlasEntity;
import com.qk.dam.metadata.catacollect.repo.HiveDbToTableAgg;
import com.qk.dam.metadata.catacollect.repo.MysqlAtlasEntity;
import com.qk.dam.metadata.catacollect.repo.MysqlDbToTableAgg;
import com.qk.dam.metadata.catacollect.service.MetadataApiService;
import com.qk.dam.metadata.catacollect.util.CatacollectUtil;
import com.qk.dam.metadata.catacollect.util.SourcesUtil;
import org.apache.atlas.model.instance.AtlasEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zys
 * @date 2022/4/18 16:40
 * @since 1.0.0
 */
@Service
public class MetadataApiServiceImpl implements MetadataApiService {
  /*private final BaseClientConf baseClientConf;

  public MetadataApiServiceImpl(BaseClientConf baseClientConf) {
    this.baseClientConf = baseClientConf;
  }*/

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
  public  List<String> queryTable(ConnectInfoVo connectInfoVo){
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

  /**
   * 根据数据连接信息获取元数据信息
   * @param metadataConnectInfoVo
   * @return
   */
  public  List<AtlasEntity.AtlasEntitiesWithExtInfo> extractorAtlasEntitiesWith(
      MetadataConnectInfoVo metadataConnectInfoVo){
    List<AtlasEntity.AtlasEntitiesWithExtInfo> list = new ArrayList<>();
    if (metadataConnectInfoVo.getType() !=null){
      try {
      switch (metadataConnectInfoVo.getType()){
        case SourcesUtil.MYSQL:
            list =new MysqlAtlasEntity(metadataConnectInfoVo).searchMysqlAtals(list);
          break;
        case SourcesUtil.HIVE:
          list = new HiveAtlasEntity(metadataConnectInfoVo).searchHiveAtals(list);
          break;
        default:
          break;
      }
      } catch (SQLException sqlException) {
        sqlException.printStackTrace();
      }
    }
   /* list.forEach(e->{
      try {
        baseClientConf.atlasClientV2.createEntities(e);
      } catch (AtlasServiceException atlasServiceException) {
        atlasServiceException.printStackTrace();
      }
    });*/
    return list;
  }

}