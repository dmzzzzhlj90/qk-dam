package com.qk.dm.authority.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.vo.params.EmpowerParamVO;
import com.qk.dm.authority.vo.powervo.EmpowerVO;

/**
 * 授权
 */
public interface PowerEmpowerService {
  void addEmpower(EmpowerVO empowerVO);

  EmpowerVO EmpowerDetails(Long id);

  void updateEmpower(EmpowerVO empowerVO);

  void deleteEmpower(Long id);

  PageResultVO<EmpowerVO> queryEmpower(EmpowerParamVO empowerParamVO);
}
