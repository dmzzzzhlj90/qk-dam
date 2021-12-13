package com.qk.dm.reptile.service;

import com.qk.dm.reptile.params.dto.RptDimensionInfoDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RptDimensionInfoService {
  List<RptDimensionInfoVO> qyeryRptDir();

  void addRptDir(RptDimensionInfoDTO rptDimensionInfoDTO);

  void deleteRptDir(Long id);

  void updateRptDir(RptDimensionInfoDTO rptDimensionInfoDTO);

  List<String> updateDirName();
}
