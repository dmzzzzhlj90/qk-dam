package com.qk.dm.reptile.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnParamDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RptDimensionInfoColumnService {
  void addRptDimensionInfoColumn(RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO);

  void deleteRptDimensionInfoColumn(List<Long> id);

  void updateRptDimensionInfoColumn(RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO);

  PageResultVO<RptDimensionInfoColumnVO> qyeryRptDimensionInfoColumn(
      RptDimensionInfoColumnParamDTO rptDimensionInfoColumnParamDTO);
}
