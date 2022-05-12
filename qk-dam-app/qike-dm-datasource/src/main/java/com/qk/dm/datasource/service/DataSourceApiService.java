package com.qk.dm.datasource.service;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import org.springframework.stereotype.Service;

import java.util.List;

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

    ResultDatasourceInfo getDataSource(String dataSourceName);

    List<ResultDatasourceInfo> getDataSourceList(List<String> dataSourceNames);

    ResultDatasourceInfo getDataSourceByCode(String dataSourceCode);

    List<ResultDatasourceInfo> getDataSourceListByCode(List<String> dataSourceCodes);
}
