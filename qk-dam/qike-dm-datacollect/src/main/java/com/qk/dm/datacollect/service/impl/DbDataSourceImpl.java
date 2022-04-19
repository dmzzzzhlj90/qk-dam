package com.qk.dm.datacollect.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.datacollect.service.DbDataSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zys
 * @date 2022/4/18 16:06
 * @since 1.0.0
 */
@Service
public class DbDataSourceImpl implements DbDataSource {
  private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;

  public DbDataSourceImpl(DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
    this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
  }
  //private final MetadataApiService metadataApiService;



  @Override
  public List<String> getResultDb(String dataSourceName) {
    ResultDatasourceInfo dataSource = dataBaseInfoDefaultApi.getDataSource(dataSourceName);
    if (Objects.nonNull(dataSource)){
      //ConnectInfoVo connectInfoVo = new ConnectInfoVo();
      //connectInfoVo =GsonUtil.fromJsonString(dataSource.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {}.getType());
     //return metadataApiService.queryDB(connectInfoVo);
      return  null;
    }else {
      throw  new BizException("根据连接名称获取连接信息失败");
    }
  }
}