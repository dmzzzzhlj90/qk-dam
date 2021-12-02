package com.qk.dm.dataquality.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataquality.service.DqcRuleDataBaseService;
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
public class DqcRuleDataBaseServiceImpl implements DqcRuleDataBaseService {

  private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;

  public DqcRuleDataBaseServiceImpl(DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
    this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
  }

  private static final String server = "server";

  @Override
  public List<String> getAllConnType() {
    return dataBaseInfoDefaultApi.getAllConnType();
  }

  @Override
  public List<String> getResultDataSourceByType(String type) {
    List<ResultDatasourceInfo> resultDataSourceByType = dataBaseInfoDefaultApi.getResultDataSourceByType(type);
    return resultDataSourceByType.stream().map(ResultDatasourceInfo::getDataSourceName).collect(Collectors.toList());
  }

  @Override
  public List<String> getAllDataBase(String dbType, String dataSourceName) {
    return dataBaseInfoDefaultApi.getAllDataBase(dbType,getServer(dataSourceName));
  }

  @Override
  public List<String> getAllTable(String dbType, String dataSourceName, String dbName) {
    return dataBaseInfoDefaultApi.getAllTable(dbType, getServer(dataSourceName), dbName);
  }

  @Override
  public List getAllColumn(String dbType, String dataSourceName, String dbName, String tableName) {
    return dataBaseInfoDefaultApi.getAllColumn(dbType, getServer(dataSourceName), dbName, tableName);
  }

  private String getServer(String dataSourceName) {
    ResultDatasourceInfo resultDataSourceByConnectName = dataBaseInfoDefaultApi.getResultDataSourceByConnectName(dataSourceName);
    Map<String, String> map = GsonUtil.fromJsonString(resultDataSourceByConnectName.getConnectBasicInfoJson(), new TypeToken<Map<String, String>>() {
    }.getType());
    return map.get(server);
  }
}
