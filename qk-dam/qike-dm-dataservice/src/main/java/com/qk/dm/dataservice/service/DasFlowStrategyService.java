package com.qk.dm.dataservice.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.vo.DasFlowStrategyParamsVO;
import com.qk.dm.dataservice.vo.DasFlowStrategyVO;
import org.springframework.stereotype.Service;

/**
 * @author zys
 * @date 2021/8/18
 * @since 1.0.0
 */
@Service
public interface DasFlowStrategyService {
  void addDasFlowStrategy(DasFlowStrategyVO dasFlowStrategyVO);

  void updateDasFlowStrategy(DasFlowStrategyVO dasFlowStrategyVO);

  void deleteDasFlowStrategy(Long valueOf);

  void bulkDeleteDasFlowStrategy(String ids);

  PageResultVO<DasFlowStrategyVO> getDasFlowStrategy(
      DasFlowStrategyParamsVO dasFlowStrategyParamsVO);
}
