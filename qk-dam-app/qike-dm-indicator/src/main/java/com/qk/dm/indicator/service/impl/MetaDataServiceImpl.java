package com.qk.dm.indicator.service.impl;

import com.qk.dam.metedata.entity.MtdTableApiParams;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.indicator.feign.MtdDataFeign;
import com.qk.dm.indicator.service.MetaDataService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangzp
 * @date 2021/9/8 16:41
 * @since 1.0.0
 */
@Service
public class MetaDataServiceImpl implements MetaDataService {

  private final MtdDataFeign mtdDataFeign;

  @Autowired
  public MetaDataServiceImpl(MtdDataFeign mtdDataFeign) {
    this.mtdDataFeign = mtdDataFeign;
  }

  @Override
  public List<MtdTables> getTables(MtdTableApiParams mtdTableApiParams) {
    return mtdDataFeign.getTables(mtdTableApiParams).getData();
  }

  @Override
  public List<Map<String, Object>> getColumns(String guid) {
    return mtdDataFeign.getColumns(guid).getData();
  }
}
