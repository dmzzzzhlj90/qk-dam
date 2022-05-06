package com.qk.dm.dolphin.common.service;

import com.qk.dm.dolphin.common.dto.ScheduleDTO;

/**
 * @author shenpj
 * @date 2022/4/21 15:05
 * @since 1.0.0
 */
public interface DolphinScheduleService {
    void insert(Long processDefinitionCode, String effectiveTimeStart, String ffectiveTimeEnt, String cron);

    void update(Integer scheduleId, String effectiveTimeStart, String ffectiveTimeEnt, String cron);

    void execute(Integer scheduleId,  String state);

    void delete(Integer scheduleId);

    ScheduleDTO detail(Long processDefinitionCode);
}
