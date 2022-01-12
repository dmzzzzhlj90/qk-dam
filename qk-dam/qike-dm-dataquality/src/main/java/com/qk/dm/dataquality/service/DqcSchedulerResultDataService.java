package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.entity.DqcSchedulerResult;
import com.qk.dm.dataquality.vo.DqcSchedulerResultPageVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

    List<DqcSchedulerResult> getSchedulerResultList(Set<String> jobIds);

    List<DqcSchedulerResult> getSchedulerResultListByWarn(String warnResult);

    List<DqcSchedulerResult> getSchedulerResultListByWarnTrend(String warnResult, Date startDate, Date endDate);

    PageResultVO<DqcSchedulerResultVO> searchResultPageList(DqcSchedulerResultPageVO schedulerResultDataParamsVO);
}
