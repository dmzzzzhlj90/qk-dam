package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.vo.DqcRuleTemplateListVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/8 7:46 下午
 * @since 1.0.0
 */
public interface DqcRuleTemplateService {

    List<DqcRuleTemplateListVo> searchList();

    PageResultVO<DqcRuleTemplateListVo> searchPageList(Pagination pagination);

    void insert(DqcRuleTemplateVo dqcRuleTemplateVo);

    void update(DqcRuleTemplateVo dqcRuleTemplateVo);

    void delete(Integer delId);

    void deleteBulk(Integer delId);
}
