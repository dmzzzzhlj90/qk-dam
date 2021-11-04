package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasApiRegisterVO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210817
 * @since 1.0.0
 */
@Service
public interface DasApiRegisterService {

  DasApiRegisterVO detail(String apiId);

  void insert(DasApiRegisterVO dasApiRegisterVO);

  void update(DasApiRegisterVO dasApiRegisterVO);

  Map<String, String> getRegisterBackendParaHeaderInfo();

  Map<String, String> getRegisterConstantParaHeaderInfo();

  void bulkAddDasApiRegister(List<DasApiRegisterVO> dasApiRegisterVOList);

  List<DasApiRegisterVO> findAll();
}
