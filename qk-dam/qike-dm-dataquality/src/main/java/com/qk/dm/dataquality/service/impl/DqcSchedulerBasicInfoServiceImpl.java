package com.qk.dm.dataquality.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DsdSchedulerAllParamsVO;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public class DqcSchedulerBasicInfoServiceImpl implements DqcSchedulerBasicInfoService {


    @Override
    public PageResultVO<DqcSchedulerBasicInfoVO> searchPageList(DsdSchedulerAllParamsVO dsdSchedulerAllParamsVO) {
        return null;
    }

    @Override
    public void insert(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {

    }

    @Override
    public void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteBulk(String ids) {

    }
}
