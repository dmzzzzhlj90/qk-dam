package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.params.ServiceParamVO;
import com.qk.dm.authority.vo.powervo.ServiceVO;

import java.util.List;

/**
 * 权限服务
 */
public interface PowerService {
  void addService(ServiceVO serviceVO);

  ServiceVO ServiceDetails(Long id);

  void updateService(ServiceVO serviceVO);

  void deleteService(Long id);

  List<ServiceVO> queryServices(ServiceParamVO serviceParamVO);
}
