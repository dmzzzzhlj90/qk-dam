package com.qk.dm.datacollect.service;

import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;

/**
 * @author shenpj
 * @date 2022/4/28 15:48
 * @since 1.0.0
 */
public interface DolphinHttpService {
    void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO);

    void update(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO);
}
