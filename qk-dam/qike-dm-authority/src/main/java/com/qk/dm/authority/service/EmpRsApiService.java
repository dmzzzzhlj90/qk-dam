package com.qk.dm.authority.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.params.ApiPageResourcesParamVO;
import com.qk.dm.authority.vo.params.ApiResourcesParamVO;
import com.qk.dm.authority.vo.powervo.ResourceApiVO;

import java.util.List;

public interface EmpRsApiService {
  void addApiResource(ResourceApiVO resourceApiVO);

  void updateApiResource(ResourceApiVO resourceApiVO);

  void deleteApiResource(String ids);

  List<ResourceApiVO> queryApiResource(ApiResourcesParamVO apiResourcesParamVO);

  PageResultVO<ResourceApiVO> queryApiPageEmpower(ApiPageResourcesParamVO apiPageResourcesParamVO);

  Boolean qeryApiRsEmp(String ids);
}
