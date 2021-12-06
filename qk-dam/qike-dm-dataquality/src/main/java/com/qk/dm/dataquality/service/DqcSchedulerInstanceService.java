package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceExecuteDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.SchedulerInstanceConstantsVO;

/**
 * @author shenpj
 * @date 2021/12/4 4:32 下午
 * @since 1.0.0
 */
public interface DqcSchedulerInstanceService {
    DqcProcessInstanceVO search(DqcSchedulerInstanceParamsDTO dqcSchedulerInstanceParamsDTO);

    void execute(DqcSchedulerInstanceExecuteDTO instanceExecute);

    SchedulerInstanceConstantsVO getInstanceConstants();
}
