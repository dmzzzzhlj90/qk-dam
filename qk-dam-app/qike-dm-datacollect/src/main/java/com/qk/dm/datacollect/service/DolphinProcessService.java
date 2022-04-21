package com.qk.dm.datacollect.service;

import com.qk.datacenter.model.ProcessDefinition;
import com.qk.dm.datacollect.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.dto.ProcessDefinitionResultDTO;

/**
 * @author shenpj
 * @date 2022/4/21 15:46
 * @since 1.0.0
 */
public interface DolphinProcessService {
    ProcessDefinitionDTO createProcessDefinition(Long projectId, String name, String url, Object httpParams, String httpMethod, String description);

    void updateProcessDefinition(Long projectId, String name, long taskCode, String url, Object httpParams, String httpMethod, String description);

    void release(Long code, Long projectCode, ProcessDefinition.ReleaseStateEnum releaseState);

    void runing(Long projectCode, Long processDefinitionCode, Long environmentCode);

    ProcessDefinitionResultDTO list(Long projectCode, String searchVal, Integer pageNo, Integer pageSize);
}
