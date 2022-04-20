package com.qk.dm.datacollect.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metadata.catacollect.pojo.ConnectInfoVo;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import com.qk.dam.metadata.catacollect.service.MetadataApiService;
import com.qk.dam.metedata.AtlasClient;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.datacollect.mapstruct.DctDataBaseMapper;
import com.qk.dm.datacollect.service.DctDataSourceService;
import com.qk.dm.datacollect.vo.DctBaseInfoVO;
import org.apache.atlas.AtlasClientV2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zys
 * @date 2022/4/18 16:06
 * @since 1.0.0
 */
@Service
public class DctDataSourceServiceImpl implements DctDataSourceService {
  private AtlasClientV2 atlasClientV2;
  private final AtlasClient atlasClient;
  private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;
  private final MetadataApiService metadataApiService;

  public DctDataSourceServiceImpl(DataBaseInfoDefaultApi dataBaseInfoDefaultApi,
      MetadataApiService metadataApiService, AtlasClient atlasClient) {
    this.atlasClient = atlasClient;
    this.atlasClientV2=atlasClient.instance();
    this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
    this.metadataApiService = metadataApiService;
  }

  @Override
  public List<String> getResultDb(String dataSourceName) {
    ResultDatasourceInfo dataSource = dataBaseInfoDefaultApi.getDataSource(dataSourceName);
    if (Objects.nonNull(dataSource)){
      ConnectInfoVo connectInfoVo = new ConnectInfoVo();
      connectInfoVo = GsonUtil.fromJsonString(dataSource.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {}.getType());
     return metadataApiService.queryDB(connectInfoVo);
    }else {
      throw  new BizException("根据连接名称获取连接信息失败");
    }
  }

  @Override
  public List<String> getResultTable(String dataSourceName, String db) {
    ResultDatasourceInfo dataSource = dataBaseInfoDefaultApi.getDataSource(dataSourceName);
    if (Objects.nonNull(dataSource)){
      ConnectInfoVo connectInfoVo = new ConnectInfoVo();
      connectInfoVo = GsonUtil.fromJsonString(dataSource.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {}.getType());
      connectInfoVo.setDb(db);
      return metadataApiService.queryTable(connectInfoVo);
    }else {
      throw  new BizException("根据连接名称获取连接信息失败");
    }
  }

  @Override
  public void dolphinCallback(DctBaseInfoVO dctBaseInfoVO) {
    ResultDatasourceInfo dataSource = dataBaseInfoDefaultApi.getDataSource(dctBaseInfoVO.getDataSourceName());
    if (Objects.nonNull(dataSource)){
      MetadataConnectInfoVo metadataConnectInfoVo =new MetadataConnectInfoVo();
      metadataConnectInfoVo = GsonUtil.fromJsonString(dataSource.getConnectBasicInfoJson(), new TypeToken<MetadataConnectInfoVo>() {}.getType());
      DctDataBaseMapper.INSTANCE.from(dctBaseInfoVO,metadataConnectInfoVo);
      metadataApiService.extractorAtlasEntitiesWith(metadataConnectInfoVo,atlasClientV2);
    }else {
      throw new BizException("根据连接名称获取连接信息失败");
    }
  }
}