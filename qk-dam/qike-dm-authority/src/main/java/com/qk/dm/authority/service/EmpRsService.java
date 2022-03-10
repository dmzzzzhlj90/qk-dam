package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.params.ApiResourcesParamVO;
import com.qk.dm.authority.vo.params.PowerResourcesParamVO;
import com.qk.dm.authority.vo.params.ResourceParamVO;
import com.qk.dm.authority.vo.powervo.ResourceOutVO;
import com.qk.dm.authority.vo.powervo.ResourceVO;

import java.util.List;

/**
 * 资源和API
 */
public interface EmpRsService {
  void addResource(ResourceVO resourceVO);

  void updateResource(ResourceVO resourceVO);

  void deleteResource(Long id);

  List<ResourceOutVO> queryResource(ResourceParamVO resourceParamVO);

  List<ResourceVO> queryResourceApi(ApiResourcesParamVO apiResourcesParamVO);

  List<ResourceVO> queryauthorized(PowerResourcesParamVO powerResourcesParamVO);
}
