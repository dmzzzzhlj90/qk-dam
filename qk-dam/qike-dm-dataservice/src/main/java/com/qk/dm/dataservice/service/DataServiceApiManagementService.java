package com.qk.dm.dataservice.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.vo.DasApplicationManagementParamsVO;
import com.qk.dm.dataservice.vo.DasApplicationManagementVO;
import org.springframework.stereotype.Service;

/**
 * @author zys
 * @date 2021/8/17
 * @since 1.0.0
 */
@Service
public interface DataServiceApiManagementService {
  void addDasManagement(DasApplicationManagementVO dasApplicationManagementVO);

  void updateDasManagement(DasApplicationManagementVO dasApplicationManagementVO);

  void deleteDasApiManagement(Long valueOf);

  void bulkDeleteDasApiManagement(String ids);

  PageResultVO<DasApplicationManagementVO> getDasApiDasAiManagement(DasApplicationManagementParamsVO dasApplicationManagementParamsVO);

  void manageMentAuthorization(DasApplicationManagementParamsVO dasApplicationManagementParamsVO);
}
