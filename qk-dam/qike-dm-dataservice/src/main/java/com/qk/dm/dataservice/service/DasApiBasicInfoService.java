package com.qk.dm.dataservice.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public interface DasApiBasicInfoService {
  PageResultVO<DasApiBasicInfoVO> getDasApiBasicInfo(
      DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO);

  void addDasApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO);

  void updateDasApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO);

  void deleteDasApiBasicInfo(Long delId);

  void bulkDeleteDasApiBasicInfo(String ids);

  List<Map<String, String>> getApiType();

  List<Map<String, String>> getDMSourceType();

  Map<String, String> getRequestParasHeaderInfos();

  Map<String, String> getRequestParamsPositions();
}
