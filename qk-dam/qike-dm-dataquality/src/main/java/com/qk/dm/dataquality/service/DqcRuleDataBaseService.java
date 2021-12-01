package com.qk.dm.dataquality.service;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/29 12:08 下午
 * @since 1.0.0
 */
public interface DqcRuleDataBaseService {

    List<String> getAllConnType();

    List<ResultDatasourceInfo> getResultDataSourceByType(String type);

    List<String> getAllDataBase(String dbType, String server);

    List<String> getAllTable(String dbType, String server, String dbName);

    List getAllColumn(String dbType, String server, String dbName, String tableName);
}
