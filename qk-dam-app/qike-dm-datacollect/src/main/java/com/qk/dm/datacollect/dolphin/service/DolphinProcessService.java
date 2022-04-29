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
    void release(Long processDefinitionCode, ProcessDefinition.ReleaseStateEnum releaseState);

    void runing(Long processDefinitionCode, Long environmentCode);

    ProcessDefinitionResultDTO pageList(String searchVal, Integer pageNo, Integer pageSize);

    void delete(Long processDefinitionCode);

    Object detail(Long processDefinitionCode);

    ProcessDefinitionDTO detailToProcess(Long processDefinitionCode);

    List<ProcessDefinitionDTO> list();
}
