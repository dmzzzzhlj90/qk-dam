package com.qk.dm.datacollect.service;

import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;

/**
 * @author shenpj
 * @date 2022/4/28 15:48
 * @since 1.0.0
 */
public interface DolphinProcessDefinitionService {
    /**
     * 创建流程定义
     * @param dctSchedulerBasicInfoVO
     */
    void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO);

    /**
     * 修改流程定义
     * @param dctSchedulerBasicInfoVO
     */
    void update(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO);
}
