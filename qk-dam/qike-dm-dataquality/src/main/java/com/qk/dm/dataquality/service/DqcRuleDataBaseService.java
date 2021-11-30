package com.qk.dm.dataquality.service;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/29 12:08 下午
 * @since 1.0.0
 */
public interface DqcRuleDataBaseService {

    List<String> getAllConnType();

    List<ResultDatasourceInfo> getResultDataSourceByType(String type);

    ResultDatasourceInfo getResultDataSourceByConnectName(String connectName);

    List<MtdAtlasEntityType> getAllEntityType();

    MtdApi mtdDetail(MtdApiParams mtdApiParams);

    List<String> getAllDataBase(String dbType);

    List<String> getAllTable(String dbType, String server, String dbName);

    List getAllColumn(String dbType, String server, String dbName, String tableName);
}
