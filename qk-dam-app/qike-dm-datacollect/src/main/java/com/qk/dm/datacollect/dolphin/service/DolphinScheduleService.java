package com.qk.dm.datacollect.dolphin.service;

import com.qk.datacenter.model.ProcessDefinition;
import com.qk.dm.datacollect.dolphin.dto.ScheduleDTO;

/**
 * @author shenpj
 * @date 2022/4/21 15:05
 * @since 1.0.0
 */
public interface DolphinScheduleService {
    void insert(Long processDefinitionCode, String effectiveTimeStart, String ffectiveTimeEnt, String cron);

    void update(Integer scheduleId, String effectiveTimeStart, String ffectiveTimeEnt, String cron);

    void execute(Integer scheduleId,  ProcessDefinition.ReleaseStateEnum state);

    void delete(Integer scheduleId);

    ScheduleDTO detail(Long processDefinitionCode);
}
