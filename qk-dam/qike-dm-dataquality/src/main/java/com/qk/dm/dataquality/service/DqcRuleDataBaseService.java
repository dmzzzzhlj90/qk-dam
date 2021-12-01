package com.qk.dm.dataquality.service;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/29 12:08 下午
 * @since 1.0.0
 */
public interface DqcRuleDataBaseService {

    List<String> getAllConnType();

    List<String> getResultDataSourceByType(String type);

    List<String> getAllDataBase(String dbType, String dataSourceName);

    List<String> getAllTable(String dbType, String dataSourceName, String dbName);

    List getAllColumn(String dbType, String dataSourceName, String dbName, String tableName);
}
