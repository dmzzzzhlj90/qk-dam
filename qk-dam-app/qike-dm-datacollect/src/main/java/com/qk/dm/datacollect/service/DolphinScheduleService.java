package com.qk.dm.datacollect.service;

import com.qk.datacenter.model.ProcessDefinition;
import com.qk.dm.datacollect.dto.ScheduleDTO;
import com.qk.dm.datacollect.vo.DctSchedulerConfigVO;

/**
 * @author shenpj
 * @date 2022/4/21 15:05
 * @since 1.0.0
 */
public interface DolphinScheduleService {
    void insert(Long projectCode,Long processDefinitionCode,DctSchedulerConfigVO dqcSchedulerConfigVO);

    void update(Long projectCode,Integer scheduleId, DctSchedulerConfigVO dqcDctSchedulerConfigVO);

    void execute(Integer scheduleId, Long projectCode, ProcessDefinition.ReleaseStateEnum state);

    void delete(Integer scheduleId, Long projectCode);

    ScheduleDTO detail(Long processDefinitionCode, Long projectCode);
}
