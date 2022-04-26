package com.qk.dm.datacollect.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.vo.*;

/**
 * @author shenpj
 * @date 2022/4/25 11:31
 * @since 1.0.0
 */
public interface DctInstanceService {
    PageResultVO<DctProcessInstanceVO> search(DctSchedulerInstanceParamsDTO dctInstanceParamsVO);

    DctProcessInstanceVO detail(Integer instanceId);

    void execute(DctInstanceExecuteVO instanceExecute);

    PageResultVO<DctTaskInstanceVO> searchTask(DctTaskInstanceParamsVO dctTaskInstanceParamsVO);

    Object taskLogDownload(Integer taskInstanceId);
}
