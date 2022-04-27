package com.qk.dm.datacollect.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DctDataSourceService {
  List<String> getResultDb(String dataSourceId);

  List<String> getResultTable(String dataSourceId, String db);

  void dolphinCallback(String dctBaseInfoVO) throws Exception;
}
