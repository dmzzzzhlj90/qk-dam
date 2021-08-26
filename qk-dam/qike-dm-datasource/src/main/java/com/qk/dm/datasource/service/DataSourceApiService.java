package com.qk.dm.datasource.service;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 数据服务对外提供API接口
 *
 * @author wjq
 * @date 20210826
 * @since 1.0.0
 */
@Service
public interface DataSourceApiService {

  // ========================API调用=============================
  List<String> getAllConnType();

  List<ResultDatasourceInfo> getResultDataSourceByType(String type);

  ResultDatasourceInfo getResultDataSourceByConnectName(String connectName);
}
