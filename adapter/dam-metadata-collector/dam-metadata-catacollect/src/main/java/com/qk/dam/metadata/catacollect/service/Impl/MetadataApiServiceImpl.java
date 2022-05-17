/*
package com.qk.dam.metadata.catacollect.service.Impl;

import com.qk.dam.catacollect.vo.MetadataConnectInfoVo;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.metadata.catacollect.enums.AtalsEnum;
import com.qk.dam.metadata.catacollect.repo.AtlasAgg;
import com.qk.dam.metadata.catacollect.repo.HiveAtlasEntity;
import com.qk.dam.metadata.catacollect.repo.MysqlAtlasEntity;
import com.qk.dam.metadata.catacollect.service.MetadataApiService;
import com.qk.dam.metadata.catacollect.util.SourcesUtil;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * @author zys
 * @date 2022/4/18 16:40
 * @since 1.0.0
 *//*

@Service
public class MetadataApiServiceImpl implements MetadataApiService {
  protected final AtlasAgg atlasAgg;

  public MetadataApiServiceImpl(AtlasAgg atlasAgg) {
    this.atlasAgg = atlasAgg;
  }
  */
/**
   * 根据数据连接信息获取元数据信息
   * @param metadataConnectInfoVo
   * @return
   *//*

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
}*/
