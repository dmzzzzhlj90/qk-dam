package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.params.UserEmpParamVO;
import com.qk.dm.authority.vo.params.UserEmpPowerParamVO;
import com.qk.dm.authority.vo.powervo.EmpResourceUrlVO;
import com.qk.dm.authority.vo.powervo.ServiceQueryVO;

import java.util.List;

/**
 * 用户授权信息
 */

public interface EmpUserPowerService {
  List<ServiceQueryVO> queryServicesByUserId(UserEmpParamVO userEmpParamVO);

  List<String> queryEmpower(UserEmpPowerParamVO userEmpPowerParamVO);

  List<EmpResourceUrlVO> queryEmpowerResource(UserEmpPowerParamVO userEmpPowerParamVO);
}
