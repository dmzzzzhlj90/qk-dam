package com.qk.dm.dataservice.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicinfoVO;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public interface DataServiceApiBasicInfoService {
  PageResultVO<DasApiBasicinfoVO> getDasApiBasicInfo(
      DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO);

  void addDasApiBasicInfo(DasApiBasicinfoVO dasApiBasicInfoVO);

  void updateDasApiBasicInfo(DasApiBasicinfoVO dasApiBasicInfoVO);

  void deleteDasApiBasicInfo(Long delId);

  void bulkDeleteDasApiBasicInfo(String ids);
}
