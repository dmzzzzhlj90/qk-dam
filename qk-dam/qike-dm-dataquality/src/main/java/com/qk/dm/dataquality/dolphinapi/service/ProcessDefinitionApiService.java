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

    int saveAndFlush(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    void save(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo);

    void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo);

    ProcessDefinitionDTO queryProcessDefinitionInfo(String projectName, String searchVal, String jobId);

    void delete(String projectName, Integer processDefinitionId);

    void deleteBulk(String projectName, List<Integer> processDefinitionIds);

    void release(Integer processDefinitionId, Integer releaseState);

    void deleteOne(Integer processDefinitionId);

    void verifyName(String name);

    void copy(Integer processDefinitionId);

    void startCheck(Integer processDefinitionId);

    void startInstance(Integer processDefinitionId);

}
