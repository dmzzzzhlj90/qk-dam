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

    void updateProcessDefinition(Long processDefinitionCode, Long projectId, String name, String url, Object httpParams, String httpMethod, String description);

    void release(Long processDefinitionCode, Long projectCode, ProcessDefinition.ReleaseStateEnum releaseState);

    void runing(Long processDefinitionCode, Long projectCode, Long environmentCode);

    ProcessDefinitionResultDTO list(Long projectCode, String searchVal, Integer pageNo, Integer pageSize);

    void delete(Long processDefinitionCode, Long projectCode);

    void detail(Long processDefinitionCode, Long projectCode);
}
