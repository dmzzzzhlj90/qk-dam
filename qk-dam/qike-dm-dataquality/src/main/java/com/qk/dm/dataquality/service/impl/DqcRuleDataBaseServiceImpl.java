package com.qk.dm.dataquality.service.impl;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataquality.service.DqcRuleDataBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

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

  @Override
  public List<String> getAllConnType() {
    return dataBaseInfoDefaultApi.getAllConnType();
  }

  @Override
  public List<ResultDatasourceInfo> getResultDataSourceByType(String type) {
    return dataBaseInfoDefaultApi.getResultDataSourceByType(type);
  }

  @Override
  public ResultDatasourceInfo getResultDataSourceByConnectName(String connectName) {
    return dataBaseInfoDefaultApi.getResultDataSourceByConnectName(connectName);
  }

  @Override
  public List<MtdAtlasEntityType> getAllEntityType() {
    return dataBaseInfoDefaultApi.getAllEntityType();
  }

  @Override
  public MtdApi mtdDetail(MtdApiParams mtdApiParams) {
    return null;
//    return dataBaseInfoDefaultApi.mtdDetail(mtdApiParams);
  }

  @Override
  public List<String> getAllDataBase(String dbType) {
    return dataBaseInfoDefaultApi.getAllDataBase(dbType);
  }

  @Override
  public List<String> getAllTable(String dbType, String server, String dbName) {
    return dataBaseInfoDefaultApi.getAllTable(dbType, server, dbName);
  }

  @Override
  public List getAllColumn(String dbType, String server, String dbName, String tableName) {
    return dataBaseInfoDefaultApi.getAllColumn(dbType, server, dbName, tableName);
  }
}
