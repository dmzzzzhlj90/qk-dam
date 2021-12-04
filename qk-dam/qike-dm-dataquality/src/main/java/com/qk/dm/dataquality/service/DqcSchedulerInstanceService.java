package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;

/**
 * @author shenpj
 * @date 2021/12/4 4:32 下午
 * @since 1.0.0
 */
public interface DqcSchedulerInstanceService {
    Object search(DqcSchedulerInstanceParamsDTO dqcSchedulerInstanceParamsDTO);
}
