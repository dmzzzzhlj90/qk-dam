package com.qk.dm.datacollect.service;

import com.qk.dm.datacollect.vo.DctTableDataVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DctDataSourceService {
  List<String> getResultDb(String dataSourceId);

  List<DctTableDataVO> getResultTable(String dataSourceId, String db);
}