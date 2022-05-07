package com.qk.dm.datasource.service;

import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dam.metedata.entity.MtdTables;

import java.util.List;
import java.util.Map;

public interface DsUnifiedApiService {
  List<String> getAllConnType();

  Map<String,String> getAllDataSourcesByType(String engineType);

  List<MtdApiDb> getAllDataBase(String engineType, String dataSourceConnId);

  List<MtdTables> getAllTable(String engineType, String dataSourceConnId, String dataBaseName);

  List<MtdAttributes> getAllColumn(String engineType, String dataSourceConnId, String dataBaseName, String tableName);

  List<String> getDctResultDb(String dataSourceConnId);

  List<String> getDctResultTable(String dataSourceConnId, String databaseName);
}
