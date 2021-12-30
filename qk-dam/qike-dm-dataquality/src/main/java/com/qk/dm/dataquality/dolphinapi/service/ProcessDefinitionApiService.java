package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessDefinitionDTO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 调度引擎Dolphin Scheduler 流程定义相关操作
 *
 * @author wjq
 * @date 2021/11/16
 * @since 1.0.0
 */
@Service
public interface ProcessDefinitionApiService {

    Long saveAndFlush(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    void save(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo, Integer version);

    void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo, ProcessDefinitionDTO processDefinitionCode);

    ProcessDefinitionDTO queryProcessDefinitionInfo(Long projectCode, String searchVal, String jobId);

    void delete(Long projectCode, Long processDefinitionCode);

    void deleteBulk(List<Long> processDefinitionIdList,Long projectCode);

    void release(Long processDefinitionCode, String releaseState);

    void startCheck(Long processDefinitionCode);

    void startInstance(Long processDefinitionCode);

}
