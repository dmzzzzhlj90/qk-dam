package com.qk.dam.metadata.catacollect.service;

import com.qk.dam.metadata.catacollect.pojo.ConnectInfoVo;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import org.apache.atlas.model.instance.AtlasEntity;

import java.util.List;

public interface MetadataApiService {
  //获取库名称信息
  List<String> queryDB(ConnectInfoVo connectInfoVo);
  //获取表名称信息
  List<String> queryTable(ConnectInfoVo connectInfoVo);
  //获取元数据信息
  List<AtlasEntity.AtlasEntitiesWithExtInfo> extractorAtlasEntitiesWith(
      MetadataConnectInfoVo metadataConnectInfoVo);
}
