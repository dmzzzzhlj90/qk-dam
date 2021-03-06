package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.params.dto.DqcSchedulerDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerReleaseDTO;

/**
 * @author shenpj
 * @date 2021/12/1 5:28 下午
 * @since 1.0.0
 */
public interface DqcSchedulerExecutorsService {
    void release(DqcSchedulerReleaseDTO dqcSchedulerReleaseDto);

    void runing(Long id);

    void update(Integer scheduleId, DqcSchedulerDTO dqcSchedulerDTO);

    void online(Integer scheduleId);

    void offline(Integer scheduleId);

    void deleteOne(Integer scheduleId, DqcSchedulerDTO dqcSchedulerDTO);

    Object search(Long processDefinitionCode);
}
