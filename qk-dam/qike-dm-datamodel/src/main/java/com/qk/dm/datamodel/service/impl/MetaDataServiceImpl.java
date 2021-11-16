package com.qk.dm.datamodel.service.impl;

import com.qk.dam.metedata.entity.MtdTableApiParams;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.datamodel.feign.MetaDataFeign;
import com.qk.dm.datamodel.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 元数据调用
 * @author zys
 * @date 2021/11/16 17:20
 * @since 1.0.0
 */
@Service
public class MetaDataServiceImpl implements MetaDataService {
  @Autowired
  private MetaDataFeign metaDataFeign;
  @Override
  public List<MtdTables> getTables(MtdTableApiParams mtdTableApiParams) {
    return metaDataFeign.getTables(mtdTableApiParams).getData();
  }

  @Override
  public List<Map<String,Object>> getColumns(String guid) {
    return metaDataFeign.getColumns(guid).getData();
  }
}