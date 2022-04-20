package com.qk.dm.datacollect.service;

import com.qk.dm.datacollect.vo.DctBaseInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DctDataSourceService {
  List<String> getResultDb(String dataSourceName);

  List<String> getResultTable(String dataSourceName, String db);

  void dolphinCallback(DctBaseInfoVO dctBaseInfoVO);
}
