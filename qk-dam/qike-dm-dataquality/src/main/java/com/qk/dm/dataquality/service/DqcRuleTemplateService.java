package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.vo.DqcRuleTemplateListVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;
import com.qk.dm.dataquality.vo.PageResultVO;

/**
 * @author shenpj
 * @date 2021/11/8 7:46 下午
 * @since 1.0.0
 */
public interface DqcRuleTemplateService {

  void insert(DqcRuleTemplateVo dqcRuleTemplateVo);

  void update(Long id, DqcRuleTemplateVo dqcRuleTemplateVo);

  void delete(Long delId);

  void deleteBulk(Long delId);

  PageResultVO<DqcRuleTemplateListVo> searchPageList(Pagination pagination);
}
