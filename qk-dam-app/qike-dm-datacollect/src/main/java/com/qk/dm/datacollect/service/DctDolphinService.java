package com.qk.dm.datacollect.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import com.qk.dm.datacollect.vo.DctSchedulerInfoVO;
import com.qk.dm.datacollect.vo.DctSchedulerReleaseVO;
import com.qk.dm.datacollect.vo.DctSchedulerInfoParamsVO;

/**
 * @author shenpj
 * @date 2022/4/21 16:57
 * @since 1.0.0
 */
public interface DctDolphinService {
    /**
     * 流程定义删除
     * @param processDefinitionCode
     */
    void delete(Long processDefinitionCode);

    /**
     * 流程定义上下线
     * @param dctSchedulerReleaseVO
     */
    void release(DctSchedulerReleaseVO dctSchedulerReleaseVO);

    /**
     * 运行流程定义
     * @param processDefinitionCode
     */
    void runing(Long processDefinitionCode);

    /**
     * 流程定义列表
     * @param schedulerInfoParamsVO
     * @return
     */
    PageResultVO<DctSchedulerInfoVO> searchPageList(DctSchedulerInfoParamsVO schedulerInfoParamsVO);

    /**
     * 流程定义详情
     * @param code
     * @return
     */
    DctSchedulerBasicInfoVO detail(Long code);
}
