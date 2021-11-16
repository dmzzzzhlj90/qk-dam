package com.qk.dm.datamodel.service;

import com.qk.dam.metedata.entity.MtdTableApiParams;
import com.qk.dam.metedata.entity.MtdTables;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MetaDataService {
  List<MtdTables> getTables(MtdTableApiParams mtdTableApiParams);

  List<Map<String, Object>>  getColumns(String guid);
}
