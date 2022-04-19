package com.qk.dm.datacollect.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metadata.catacollect.pojo.ConnectInfoVo;
import com.qk.dam.metadata.catacollect.service.MetadataApiService;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.datacollect.service.DctDataSourceService;
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
  private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;
  private final MetadataApiService metadataApiService;

  public DctDataSourceServiceImpl(DataBaseInfoDefaultApi dataBaseInfoDefaultApi,
      MetadataApiService metadataApiService) {
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
}