package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceExecuteDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerTaskInstanceLogDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerTaskInstanceParamsDTO;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.DqcProcessTaskInstanceVO;
import com.qk.dm.dataquality.vo.SchedulerInstanceConstantsVO;

/**
 * @author shenpj
 * @date 2021/12/4 4:32 下午
 * @since 1.0.0
 */
public interface DqcSchedulerInstanceService {
    PageResultVO<DqcProcessInstanceVO> search(DqcSchedulerInstanceParamsDTO dqcSchedulerInstanceParamsDTO);

    void execute(DqcSchedulerInstanceExecuteDTO instanceExecute);

    SchedulerInstanceConstantsVO getInstanceConstants();

    PageResultVO<DqcProcessTaskInstanceVO> searchTask(DqcSchedulerTaskInstanceParamsDTO taskInstanceParamsDTO);

    Object searchTaskLog(DqcSchedulerTaskInstanceLogDTO taskInstanceLogDTO);

    Object taskLogDownload(Integer taskInstanceId);
}
