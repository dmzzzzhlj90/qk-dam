package com.qk.dm.datacollect.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.vo.*;

/**
 * @author shenpj
 * @date 2022/4/25 11:31
 * @since 1.0.0
 */
public interface DctInstanceService {
    /**
     * 流程实例列表
     * @param dctInstanceParamsVO
     * @return
     */
    PageResultVO<DctProcessInstanceVO> search(DctInstanceParamsVO dctInstanceParamsVO);

    /**
     * 流程实例详情
     * @param instanceId
     * @return
     */
    DctProcessInstanceVO detail(Integer instanceId);

    /**
     * 流程定义操作
     * @param instanceExecute
     */
    void execute(DctInstanceExecuteVO instanceExecute);

    /**
     * 任务实例列表
     * @param dctTaskInstanceParamsVO
     * @return
     */
    PageResultVO<DctTaskInstanceVO> searchTask(DctTaskInstanceParamsVO dctTaskInstanceParamsVO);

    /**
     * 任务实例日志
     * @param taskInstanceId
     * @return
     */
    Object taskLogDownload(Integer taskInstanceId);
}
