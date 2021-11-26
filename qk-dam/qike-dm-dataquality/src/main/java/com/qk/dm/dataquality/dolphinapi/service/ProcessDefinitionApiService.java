package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.dolphinapi.dto.ProcessDefinitionDTO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import org.springframework.stereotype.Service;

/**
 * 调度引擎Dolphin Scheduler 流程定义相关操作
 *
 * @author wjq
 * @date 2021/11/16
 * @since 1.0.0
 */
@Service
public interface ProcessDefinitionApiService {

    void save(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    ProcessDefinitionDTO queryProcessDefinitionInfo(String projectName, String searchVal, String jobId);

    void release(Integer processDefinitionId, Integer releaseState);

    void deleteOne(Integer processDefinitionId);

    void verifyName(String name);

    void copy(Integer processDefinitionId);

    void startCheck(Integer processDefinitionId);

    void startInstance(Integer processDefinitionId);

}
