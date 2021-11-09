package com.qk.dm.dataquality.service.impl;

import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.service.DqcDispatchRuleService;
import com.qk.dm.dataquality.vo.DqcDispatchRuleListVo;
import com.qk.dm.dataquality.vo.DqcDispatchRuleVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/9 10:53 上午
 * @since 1.0.0
 */
@Service
public class DqcDispatchRuleServiceImpl implements DqcDispatchRuleService {
    @Override
    public List<DqcDispatchRuleListVo> searchList() {
        return null;
    }

    @Override
    public PageResultVO<DqcDispatchRuleListVo> searchPageList(Pagination pagination) {
        return null;
    }

    @Override
    public void insert(DqcDispatchRuleVo dqcDispatchRuleVo) {

    }

    @Override
    public void update(DqcDispatchRuleVo dqcDispatchRuleVo) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void deleteBulk(Integer id) {

    }
}
