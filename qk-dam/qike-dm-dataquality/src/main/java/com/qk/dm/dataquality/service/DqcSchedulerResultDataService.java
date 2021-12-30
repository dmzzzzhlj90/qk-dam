package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultVO;
import org.springframework.stereotype.Service;

/**
 * 调度结果集
 *
 * @author wjq
 * @date 2021/12/10
 * @since 1.0.0
 */
@Service
public interface DqcSchedulerResultDataService {

    PageResultVO<DqcSchedulerResultVO> getResultDataList(DqcSchedulerResultParamsVO schedulerResultDataParamsVO);

    Object getWarnResultInfo(String ruleId);
}
