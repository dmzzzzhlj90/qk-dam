package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.powervo.ServiceVO;

import java.util.List;

/**
 * 用户授权信息
 */

public interface UserEmpowerService {
  List<ServiceVO> queryServicesByUserId(String realm, String userId);

  List<String> queryEmpower(String realm, String serviceId, String userId);
}
