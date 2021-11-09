package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.vo.DqcDispatchRuleListVo;
import com.qk.dm.dataquality.vo.DqcDispatchRuleVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/9 10:52 上午
 * @since 1.0.0
 */
@Service
public interface DqcDispatchRuleService {
    List<DqcDispatchRuleListVo> searchList();

    PageResultVO<DqcDispatchRuleListVo> searchPageList(Pagination pagination);

    void insert(DqcDispatchRuleVo dqcDispatchRuleVo);

    void update(DqcDispatchRuleVo dqcDispatchRuleVo);

    void delete(Integer id);

    void deleteBulk(Integer id);
}
