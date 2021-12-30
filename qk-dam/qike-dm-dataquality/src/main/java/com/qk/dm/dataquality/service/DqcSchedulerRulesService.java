package com.qk.dm.dataquality.service;//package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    List<DqcSchedulerRulesVO> insertBulk(List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList, String jobId);

    void update(DqcSchedulerRulesVO dqcSchedulerRulesVO);

    List<DqcSchedulerRulesVO> updateBulk(List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList,String jobId);

    void deleteOne(Long id);

    void deleteBulk(String ids);

    Boolean checkRuleTemp(Long id);

    void deleteByJobId(String jobId);

    void deleteBulkByJobIds(List<String> jobIds);

    Integer getTableSet(Set<Long> taskCodeSet);

    Integer getFieldSet(Set<Long> taskCodeSet);

    Integer getTableSet();

    Integer getFieldSet();

    List<DqcSchedulerRules> getSchedulerRulesListByTaskCode(Set<Long> taskCodeSet);
}
