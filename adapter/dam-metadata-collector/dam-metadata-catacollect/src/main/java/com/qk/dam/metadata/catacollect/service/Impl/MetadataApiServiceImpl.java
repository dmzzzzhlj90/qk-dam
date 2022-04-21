package com.qk.dam.metadata.catacollect.service.Impl;

import cn.hutool.db.Entity;
import com.qk.dam.metadata.catacollect.enums.AtalsEnum;
import com.qk.dam.metadata.catacollect.pojo.ConnectInfoVo;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import com.qk.dam.metadata.catacollect.repo.HiveAtlasEntity;
import com.qk.dam.metadata.catacollect.repo.HiveDbToTableAgg;
import com.qk.dam.metadata.catacollect.repo.MysqlAtlasEntity;
import com.qk.dam.metadata.catacollect.repo.MysqlDbToTableAgg;
import com.qk.dam.metadata.catacollect.service.MetadataApiService;
import com.qk.dam.metadata.catacollect.util.CatacollectUtil;
import com.qk.dam.metadata.catacollect.util.SourcesUtil;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
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
  @Override
  public List<AtlasEntity.AtlasEntitiesWithExtInfo> extractorAtlasEntitiesWith(
      MetadataConnectInfoVo metadataConnectInfoVo,
      AtlasClientV2 atlasClientV2) {
    List<AtlasEntity.AtlasEntitiesWithExtInfo> list = new ArrayList<>();
    if (metadataConnectInfoVo.getType() !=null){
      try {
        switch (metadataConnectInfoVo.getType()){
          case SourcesUtil.MYSQL:
            list =new MysqlAtlasEntity(metadataConnectInfoVo).searchMysqlAtals(list,atlasClientV2,
                AtalsEnum.fromValue(metadataConnectInfoVo.getType()).getValue());
            break;
          case SourcesUtil.HIVE:
            list = new HiveAtlasEntity(metadataConnectInfoVo).searchHiveAtals(list,atlasClientV2,
                AtalsEnum.fromValue(metadataConnectInfoVo.getType()).getValue());
            break;
          default:
            break;
        }
      } catch (SQLException sqlException) {
        sqlException.printStackTrace();
      }
    }
    list.forEach(e->{
      try {
        atlasClientV2.createEntities(e);
        //Set<String> set = new HashSet<>();
        //set.add("1e5bf085-52aa-410a-947e-2b3463f0005c");
        //set.add("c82984b2-8acd-488e-9e99-3564a9c04103");
        //物理删除
        //atlasClientV2.purgeEntitiesByGuids(set);
        //atlasClientV2.updateEntities(e);
      } catch (AtlasServiceException atlasServiceException) {
        atlasServiceException.printStackTrace();
      }
    });
    return list;
  }
}