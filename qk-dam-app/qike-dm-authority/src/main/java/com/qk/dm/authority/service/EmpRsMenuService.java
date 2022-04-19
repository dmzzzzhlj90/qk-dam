package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.params.ResourceParamVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuQueryVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuVO;

import java.util.List;

public interface EmpRsMenuService {
  void addResourceMenu(ResourceMenuVO resourceMenuVO);

  void updateResourceMenu(ResourceMenuVO resourceMenuVO);

  void deleteResourceMenu(Long ids);

  List<ResourceMenuQueryVO> queryResourceMenu(ResourceParamVO resourceParamVO);

  Boolean qeryRsMenuEmp(Long id);
}
