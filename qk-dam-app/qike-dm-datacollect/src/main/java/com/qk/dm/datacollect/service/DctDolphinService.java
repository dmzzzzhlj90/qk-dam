package com.qk.dm.datacollect.service;

import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;

/**
 * @author shenpj
 * @date 2022/4/21 16:57
 * @since 1.0.0
 */
public interface DctDolphinService {
    void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO);

    void delete();
}
