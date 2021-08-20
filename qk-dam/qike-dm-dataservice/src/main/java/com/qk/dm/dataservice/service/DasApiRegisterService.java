package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasApiRegisterVO;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210817
 * @since 1.0.0
 */
@Service
public interface DasApiRegisterService {

  DasApiRegisterVO getDasApiRegisterInfoByApiId(String apiId);

  void addDasApiRegister(DasApiRegisterVO dasApiRegisterVO);

  void updateDasApiRegister(DasApiRegisterVO dasApiRegisterVO);

  Map<String, String> getBackendParaHeaderInfo();

  Map<String, String> getConstantParaHeaderInfo();
}
