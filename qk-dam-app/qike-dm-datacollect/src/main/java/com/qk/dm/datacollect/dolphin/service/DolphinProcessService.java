package com.qk.dm.datacollect.dolphin.service;

import com.qk.datacenter.model.ProcessDefinition;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionResultDTO;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/4/21 15:46
 * @since 1.0.0
 */
public interface DolphinProcessService {
    ProcessDefinitionDTO createProcessDefinition(Long projectId, String name, String url, Object httpParams, String httpMethod, String description);

    void updateProcessDefinition(Long processDefinitionCode, Long projectId, long taskCode, String name, String url, Object httpParams, String httpMethod, String description);

    void release(Long processDefinitionCode, Long projectCode, ProcessDefinition.ReleaseStateEnum releaseState);

    void runing(Long processDefinitionCode, Long projectCode, Long environmentCode);

    ProcessDefinitionResultDTO pageList(Long projectCode, String searchVal, Integer pageNo, Integer pageSize);

    void delete(Long processDefinitionCode, Long projectCode);

    Object detail(Long processDefinitionCode, Long projectCode);

    ProcessDefinitionDTO detailToProcess(Long processDefinitionCode, Long projectCode);

    List<ProcessDefinitionDTO> list(Long projectCode);
}