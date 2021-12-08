package com.qk.dm.reptile.service;

import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RptDimensionInfoColumnService {
  void addRptDimensionInfoColumn(RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO);

  void deleteRptDimensionInfoColumn(Integer id);

  void updateRptDimensionInfoColumn(RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO);

  List<RptDimensionInfoColumnVO> qyeryRptDimensionInfoColumn();
}
