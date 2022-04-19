package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasApiRegisterDefinitionVO;
import com.qk.dm.dataservice.vo.DasApiRegisterVO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    LinkedList<Map<String, Object>> getRegisterBackendParaHeaderInfo();

    LinkedList<Map<String, Object>> getRegisterConstantParaHeaderInfo();

    void bulkAddDasApiRegister(List<DasApiRegisterVO> dasApiRegisterVOList);

    List<DasApiRegisterVO> findAll();

    List<DasApiRegisterDefinitionVO> searchRegisterByApiId(List<String> apiIds);

}
