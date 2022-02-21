package com.qk.dm.dataservice.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public interface DasApiBasicInfoService {
  PageResultVO<DasApiBasicInfoVO> searchList(DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO);

  void insert(DasApiBasicInfoVO dasApiBasicInfoVO);

  void update(DasApiBasicInfoVO dasApiBasicInfoVO);

  void delete(Long delId);

  void deleteBulk(String ids);

  Map<String, String> getApiType();

  Map<String, String> getDMSourceType();

  Map<String, String> getRequestParasHeaderInfos();

  Map<String, String> getRequestParamsPositions();

  Optional<DasApiBasicInfo> searchApiBasicInfoByDelParamIsEmpty(
      DasApiBasicInfoVO dasApiBasicInfoVO);

  Optional<DasApiBasicInfo> checkExistApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO);

  List<DasApiBasicInfoVO> findAllByApiType(String apiType);

  Map<String, String> getStatusInfo();

  Map<String, String> getDataType();

}
