package com.qk.dm.dataquality.service;//package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据质量_规则调度_规则信息
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public interface DqcSchedulerRulesService {

    PageResultVO<DqcSchedulerRulesVO> searchPageList(DqcSchedulerRulesParamsVO dqcSchedulerRulesParamsVO);

    void insert(DqcSchedulerRulesVO dqcSchedulerRulesVO);

    void insertBulk(List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList, String jobId);

    void update(DqcSchedulerRulesVO dqcSchedulerRulesVO);

    void updateBulk(List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList);

    void deleteOne(Long id);

    void deleteBulk(String ids);

    Boolean checkRuleTemp(Long id);

    void deleteByJobId(String jobId);

    void deleteBulkByJobIds(List<String> jobIds);

}
