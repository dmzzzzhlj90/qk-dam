package com.qk.dm.datamodel.service.impl;

import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.utils.ConnectInfoConvertUtils;
import com.qk.dm.datamodel.feign.DataSourceFeign;
import com.qk.dm.datamodel.service.DatasourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zys
 * @date 2021/11/15 16:49
 * @since 1.0.0
 */
@Service
public class DatasourceServiceImpl implements DatasourceService {
  private final DataSourceFeign dataSourceFeign;

  public DatasourceServiceImpl(DataSourceFeign dataSourceFeign) {
    this.dataSourceFeign = dataSourceFeign;
  }

  @Override
  public List<String> getAllConnType() {
    return dataSourceFeign.getAllConnType().getData();
  }

  @Override
  public List<ResultDatasourceInfo> getResultDataSourceByType(String type) {
    return dataSourceFeign.getResultDataSourceByType(type).getData();
  }

  @Override
  public ResultDatasourceInfo getResultDataSourceByConnectName(
      String connectName) {
    ResultDatasourceInfo resultDatasourceInfo =
        dataSourceFeign.getResultDataSourceByConnectName(connectName).getData();
    ConnectBasicInfo connectInfo =
        ConnectInfoConvertUtils.getConnectInfo(
            resultDatasourceInfo.getDbType(), resultDatasourceInfo.getConnectBasicInfoJson());
    return resultDatasourceInfo;
  }
}