package com.qk.dm.dataservice.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.vo.BulkDeleteParamVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public interface DasApiBasicInfoService {

    PageResultVO<DasApiBasicInfoVO> searchList(DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO);

    void insert(DasApiBasicInfoVO dasApiBasicInfoVO);

    DasApiBasicInfo buildBulkSaveApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO);

    void update(DasApiBasicInfoVO dasApiBasicInfoVO);

    DasApiBasicInfo buildUpdateApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO);

    void delete(Long delId);

    void deleteBulk(BulkDeleteParamVO bulkDeleteParamVO);

    Map<String, String> getApiType();

    Map<String, String> getDMSourceType();

    LinkedList<Map<String, Object>> getRequestParasHeaderInfos();

    Map<String, String> getRequestParamsPositions();

    Optional<DasApiBasicInfo> checkExistApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO);

    List<DasApiBasicInfoVO> findAllByApiType(String apiType);

    Map<String, String> getStatusInfo();

    Map<String, String> getDataType();

    Map<String, String> getSyncType();

    LinkedList<Map<String, Object>> getDebugParamHeaderInfo();

}
