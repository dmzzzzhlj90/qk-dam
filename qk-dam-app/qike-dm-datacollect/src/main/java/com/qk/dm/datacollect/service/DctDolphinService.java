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
    void delete(Long processDefinitionCode);

    void release(DctSchedulerReleaseVO dctSchedulerReleaseVO);

    void runing(Long processDefinitionCode);

    PageResultVO<DctSchedulerInfoVO> searchPageList(DctSchedulerInfoParamsVO schedulerInfoParamsVO);

    DctSchedulerBasicInfoVO detail(Long code);
}
