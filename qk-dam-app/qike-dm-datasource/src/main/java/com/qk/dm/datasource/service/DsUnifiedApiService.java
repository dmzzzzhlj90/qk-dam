package com.qk.dm.datasource.service;

import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dam.metedata.entity.MtdTables;

import java.util.List;
import java.util.Map;

public interface DsUnifiedApiService {
  List<String> getAllConnType();

  Map<String,String> getAllDataSourcesByType(String engineType);

  List<MtdApiDb> getAllDataBase(String engineType, String dataSourceCode);

  List<MtdTables> getAllTable(String engineType, String dataSourceCode, String dataBaseName);

  List<MtdAttributes> getAllColumn(String engineType, String dataSourceCode, String dataBaseName, String tableName);

  List<String> getDctResultDb(String dataSourceCode);

  List<String> getDctResultTable(String dataSourceCode, String databaseName);
}
