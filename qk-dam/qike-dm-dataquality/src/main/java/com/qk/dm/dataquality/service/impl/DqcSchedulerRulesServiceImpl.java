package com.qk.dm.dataquality.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
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
public class DqcSchedulerRulesServiceImpl implements DqcSchedulerRulesService {

    @Override
    public PageResultVO<DqcSchedulerRulesVO> searchPageList(DsdSchedulerAllParamsVO dsdSchedulerAllParamsVO) {
        return null;
    }

    @Override
    public void insert(DqcSchedulerRulesVO dqcSchedulerRulesVO) {

    }

    @Override
    public void update(DqcSchedulerRulesVO dqcSchedulerRulesVO) {

    }

    @Override
    public void delete(Long valueOf) {

    }

    @Override
    public void deleteBulk(String ids) {

    }
}
