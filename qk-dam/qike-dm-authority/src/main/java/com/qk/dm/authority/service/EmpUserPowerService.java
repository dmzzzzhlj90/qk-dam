package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.powervo.ServiceVO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 用户授权信息
 */

public interface EmpUserPowerService {
  List<ServiceVO> queryServicesByUserId(@Valid @NotBlank String s, String realm, String userId);

  List<String> queryEmpower(@Valid @NotBlank String s, String realm, String serviceId, String userId);
}
