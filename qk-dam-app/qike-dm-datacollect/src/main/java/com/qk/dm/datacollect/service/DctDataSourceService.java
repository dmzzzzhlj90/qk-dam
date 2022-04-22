package com.qk.dm.datacollect.service;

import com.qk.dm.datacollect.vo.DctBaseInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DctDataSourceService {
  List<String> getResultDb(String dataSourceId);

  List<String> getResultTable(String dataSourceId, String db);

  void dolphinCallback(DctBaseInfoVO dctBaseInfoVO) throws Exception;
}
