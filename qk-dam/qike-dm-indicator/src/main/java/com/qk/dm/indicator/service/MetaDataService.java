package com.qk.dm.indicator.service;

import com.qk.dam.metedata.entity.MtdTableApiParams;
import com.qk.dam.metedata.entity.MtdTables;

import java.util.List;
import java.util.Map;

public interface MetaDataService {

    List<MtdTables> getTables(MtdTableApiParams mtdTableApiParams);

    List<Map<String, Object>> getColumns(String guid);
}
