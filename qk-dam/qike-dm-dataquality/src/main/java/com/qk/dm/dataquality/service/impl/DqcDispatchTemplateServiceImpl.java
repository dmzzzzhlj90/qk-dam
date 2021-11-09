package com.qk.dm.dataquality.service.impl;

import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.service.DqcDispatchTemplateService;
import com.qk.dm.dataquality.vo.DqcDispatchTemplateListVo;
import com.qk.dm.dataquality.vo.DqcDispatchTemplateVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/9 10:32 上午
 * @since 1.0.0
 */
@Service
public class DqcDispatchTemplateServiceImpl implements DqcDispatchTemplateService {
    @Override
    public List<DqcDispatchTemplateListVo> searchList() {
        return null;
    }

    @Override
    public PageResultVO<DqcDispatchTemplateListVo> searchPageList(Pagination pagination) {
        return null;
    }

    @Override
    public void insert(DqcDispatchTemplateVo dqcDispatchTemplateVo) {

    }

    @Override
    public void update(DqcDispatchTemplateVo dqcDispatchTemplateVo) {

    }

    @Override
    public void delete(Integer delId) {

    }

    @Override
    public void deleteBulk(Integer delId) {

    }
}
