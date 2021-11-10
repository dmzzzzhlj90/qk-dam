package com.qk.dm.dataquality.service;//package com.qk.dm.dataquality.service;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import com.qk.dm.dataquality.vo.DsdSchedulerAllParamsVO;
import org.springframework.stereotype.Service;

/**
 * 数据质量_规则调度_规则信息
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public interface DqcSchedulerRulesService {

    PageResultVO<DqcSchedulerRulesVO> searchPageList(DsdSchedulerAllParamsVO dsdSchedulerAllParamsVO);

    void insert(DqcSchedulerRulesVO dqcSchedulerRulesVO);

    void update(DqcSchedulerRulesVO dqcSchedulerRulesVO);

    void delete(Long valueOf);

    void deleteBulk(String ids);

}
