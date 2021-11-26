package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import com.qk.dm.dataquality.vo.SchedulerRuleConstantsVO;
import org.springframework.stereotype.Service;

/**
 * 数据质量_规则调度入口
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public interface DqcSchedulerInfoService {

    PageResultVO<DqcSchedulerBasicInfoVO> searchPageList(DqcSchedulerInfoParamsVO schedulerInfoParamsVO);

    void insert(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    void deleteOne(Long valueOf);

    void deleteBulk(String ids);

    SchedulerRuleConstantsVO getSchedulerRuLeConstants();

}
