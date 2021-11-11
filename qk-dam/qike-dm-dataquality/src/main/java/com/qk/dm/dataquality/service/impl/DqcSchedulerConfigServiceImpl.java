package com.qk.dm.dataquality.service.impl;


import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public class DqcSchedulerConfigServiceImpl implements DqcSchedulerConfigService {

    @Override
    public PageResultVO<DqcSchedulerConfigVO> searchPageList(DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO) {
        return null;
    }

    @Override
    public void insert(DqcSchedulerRulesVO dqcSchedulerRulesVO) {

    }

    @Override
    public void update(DqcSchedulerRulesVO dqcSchedulerRulesVO) {

    }
    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteBulk(String ids) {

    }


}
