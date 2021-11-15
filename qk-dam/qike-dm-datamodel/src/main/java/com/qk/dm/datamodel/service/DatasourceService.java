package com.qk.dm.datamodel.service;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取数据源连接
 */
@Service
public interface DatasourceService {
  List<String> getAllConnType();
  List<ResultDatasourceInfo> getResultDataSourceByType(String type);

  ResultDatasourceInfo getResultDataSourceByConnectName(String connectName);
}
