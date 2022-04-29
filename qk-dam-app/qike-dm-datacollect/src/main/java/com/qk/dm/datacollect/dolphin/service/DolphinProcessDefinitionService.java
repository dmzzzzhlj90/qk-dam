package com.qk.dm.datacollect.dolphin.service;

import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionDTO;

/**
 * @author shenpj
 * @date 2022/4/28 15:48
 * @since 1.0.0
 */
public interface DolphinProcessDefinitionService {
    ProcessDefinitionDTO createProcessDefinition(String name, Object httpParams, String description);

    void updateProcessDefinition(Long processDefinitionCode, long taskCode, String name, Object httpParams, String description);
}
