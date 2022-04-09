package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import org.springframework.stereotype.Service;

/**
 * 生成执行sql
 *
 * @author wjq
 * @date 2021/12/04
 * @since 1.0.0
 */
@Service
public interface DqcRuleSqlBuilderService {

    Object getExecuteSql(DqcSchedulerRulesVO dqcSchedulerRulesVO);

    Object getRealTimeSql(String ruleId);


}
