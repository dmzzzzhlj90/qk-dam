package com.qk.dm.dataquality.biz;

import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
import com.qk.dm.dataquality.vo.statistics.JobInfoVO;
import org.springframework.stereotype.Component;

/**
 * @author shenpj
 * @date 2021/12/23 8:11 下午
 * @since 1.0.0
 */
@Component
public class JobInfoBiz {
    private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;
    private final DqcSchedulerRulesService dqcSchedulerRulesService;

    public JobInfoBiz(DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService, DqcSchedulerRulesService dqcSchedulerRulesService) {
        this.dqcSchedulerBasicInfoService = dqcSchedulerBasicInfoService;
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
    }

    public JobInfoVO basicInfoStatistics() {
        return JobInfoVO.builder()
                //作业数
                .count(dqcSchedulerBasicInfoService.getCount())
                .tableCount(dqcSchedulerRulesService.getTableSet())
                .fieldCount(dqcSchedulerRulesService.getFieldSet())
                .build();
    }
}
