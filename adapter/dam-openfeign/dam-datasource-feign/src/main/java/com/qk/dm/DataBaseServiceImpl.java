package com.qk.dm;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.DsDatasourceVO;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  public List<ResultDatasourceInfo> getResultDataSourceByType(String type) {
    return dataBaseInfoDefaultApi.getResultDataSourceByType(type);
  }

  @Override
  public List<String> getAllConnType() {
    return dataBaseInfoDefaultApi.getAllConnType();
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
  public List<MtdApiDb> getAllDataBase(String connectType, String dataSourceName) {
    return dataBaseInfoDefaultApi.getAllDataBase(connectType,getServer(dataSourceName));
  }

  @Override
  public List<MtdTables> getAllTable(String connectType, String dataSourceName, String dataBaseName) {
    return dataBaseInfoDefaultApi.getAllTable(connectType, getServer(dataSourceName), dataBaseName);
  }

  @Override
  public List getAllColumn(String connectType, String dataSourceName, String dataBaseName, String tableName) {
    return dataBaseInfoDefaultApi.getAllColumn(connectType, getServer(dataSourceName), dataBaseName, tableName);
  }

  @Override
  public List getColumnListByTableGuid(String tableGuid) {
    return dataBaseInfoDefaultApi.getColumnListByTableGuid(tableGuid);
  }

  @Override
  public List<MtdApiDb> getAllDataBase(String connectType, String dataSourceName, Integer limit, Integer offset) {
    return dataBaseInfoDefaultApi.getAllDataBase(connectType,getServer(dataSourceName),limit,offset);
  }

  @Override
  public List<MtdTables> getAllTable(String connectType, String dataSourceName, String dataBaseName, Integer limit, Integer offset) {
    return dataBaseInfoDefaultApi.getAllTable(connectType, getServer(dataSourceName), dataBaseName,limit,offset);
  }

  @Override
  public List getAllColumn(String connectType, String dataSourceName, String dataBaseName, String tableName, Integer limit, Integer offset) {
    return dataBaseInfoDefaultApi.getAllColumn(connectType, getServer(dataSourceName), dataBaseName, tableName,limit,offset);
  }

  @Override
  public Integer getExistData(String connectType, String dataSourceName, String dataBaseName, String tableName) {
    return dataBaseInfoDefaultApi.getExistData(connectType, getServer(dataSourceName), dataBaseName, tableName);
  }

  @Override
  public Map<String,String> getAllDataSources(String connectType) {
    List<ResultDatasourceInfo> resultDataSourceByType = dataBaseInfoDefaultApi.getResultDataSourceByType(connectType);
    return resultDataSourceByType.stream().collect(Collectors.toMap(ResultDatasourceInfo::getId,ResultDatasourceInfo::getDataSourceName));
  }

  @Override
  public String getResultDataSourceByid(int id) {
    List<DsDatasourceVO> resultDataSourceById = dataBaseInfoDefaultApi.getResultDataSourceById(id);
    return resultDataSourceById.stream().filter(Objects::nonNull).findFirst().map(DsDatasourceVO::getConnectBasicInfo).orElse(null).toString();
  }
  private String getServer(String dataSourceName) {
    ResultDatasourceInfo resultDataSourceByConnectName = dataBaseInfoDefaultApi.getDataSource(dataSourceName);
    Map<String, String> map = GsonUtil.fromJsonString(resultDataSourceByConnectName.getConnectBasicInfoJson(), new TypeToken<Map<String, String>>() {
    }.getType());
    return map.get(server);
  }
}