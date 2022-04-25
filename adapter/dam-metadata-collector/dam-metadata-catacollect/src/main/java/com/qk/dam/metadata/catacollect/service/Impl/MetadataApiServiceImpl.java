package com.qk.dam.metadata.catacollect.service.Impl;

import cn.hutool.db.Entity;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.metadata.catacollect.enums.AtalsEnum;
import com.qk.dam.metadata.catacollect.pojo.ConnectInfoVo;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import com.qk.dam.metadata.catacollect.repo.*;
import com.qk.dam.metadata.catacollect.service.MetadataApiService;
import com.qk.dam.metadata.catacollect.util.CatacollectUtil;
import com.qk.dam.metadata.catacollect.util.SourcesUtil;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntity;
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
  protected final AtlasAgg atlasAgg;

  public MetadataApiServiceImpl(AtlasAgg atlasAgg) {
    this.atlasAgg = atlasAgg;
  }

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
  public void extractorAtlasEntitiesWith(
      MetadataConnectInfoVo metadataConnectInfoVo,
      AtlasClientV2 atlasClientV2) throws Exception {
    List<AtlasEntity.AtlasEntitiesWithExtInfo> list = new ArrayList<>();
    if (metadataConnectInfoVo.getType() !=null){
        switch (metadataConnectInfoVo.getType()){
          case SourcesUtil.MYSQL:
            list =new MysqlAtlasEntity(metadataConnectInfoVo,atlasAgg).searchMysqlAtals(list,atlasClientV2,
                AtalsEnum.fromValue(metadataConnectInfoVo.getType()).getValue(),SourcesUtil.MYSQL_NAME);
            break;
          case SourcesUtil.HIVE:
            list = new HiveAtlasEntity(metadataConnectInfoVo).searchHiveAtals(list,atlasClientV2,
                AtalsEnum.fromValue(metadataConnectInfoVo.getType()).getValue(),SourcesUtil.HIVE_NAME);
            break;
          default:
            break;
        }
    }
    list.forEach(e->{
      try {
        atlasClientV2.createEntities(e);
      } catch (AtlasServiceException atlasServiceException) {
        atlasServiceException.printStackTrace();
        throw  new BizException("atlasClientV2更新添加操作失败");
      } finally {
          atlasClientV2.close();
      }
    });
  }
}