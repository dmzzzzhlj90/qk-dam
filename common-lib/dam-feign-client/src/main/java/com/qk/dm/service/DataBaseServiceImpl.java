package com.qk.dm.service;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/11/29 12:08 下午
 * @since 1.0.0
 */
@Service
public class DataBaseServiceImpl implements DataBaseService {

  private static final String server = "server";

  private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;

  public DataBaseServiceImpl(DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
    this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
  }

  @Override
  public List<String> getAllConnectType() {
    return dataBaseInfoDefaultApi.getAllConnType();
  }

  @Override
  public List<String> getAllDataSource(String connectType) {
    List<ResultDatasourceInfo> resultDataSourceByType = dataBaseInfoDefaultApi.getResultDataSourceByType(connectType);
    return resultDataSourceByType.stream().map(ResultDatasourceInfo::getDataSourceName).collect(Collectors.toList());
  }

  @Override
  public List<String> getAllDataBase(String connectType, String dataSourceName) {
    return dataBaseInfoDefaultApi.getAllDataBase(connectType,getServer(dataSourceName));
  }

  @Override
  public List<String> getAllTable(String connectType, String dataSourceName, String dataBaseName) {
    return dataBaseInfoDefaultApi.getAllTable(connectType, getServer(dataSourceName), dataBaseName);
  }

  @Override
  public List getAllColumn(String connectType, String dataSourceName, String dataBaseName, String tableName) {
    return dataBaseInfoDefaultApi.getAllColumn(connectType, getServer(dataSourceName), dataBaseName, tableName);
  }

  @Override
  public Boolean getExistData(String connectType, String dataSourceName, String dataBaseName, String tableName) {
    return dataBaseInfoDefaultApi.getExistData(connectType, getServer(dataSourceName), dataBaseName, tableName);
  }

  private String getServer(String dataSourceName) {
    ResultDatasourceInfo resultDataSourceByConnectName = dataBaseInfoDefaultApi.getResultDataSourceByConnectName(dataSourceName);
    Map<String, String> map = GsonUtil.fromJsonString(resultDataSourceByConnectName.getConnectBasicInfoJson(), new TypeToken<Map<String, String>>() {
    }.getType());
    return map.get(server);
  }
}
