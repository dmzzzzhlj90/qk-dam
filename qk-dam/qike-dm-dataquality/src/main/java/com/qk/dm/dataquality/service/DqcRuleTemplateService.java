package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.vo.DqcRuleTemplateListVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;

/**
 * @author shenpj
 * @date 2021/11/8 7:46 下午
 * @since 1.0.0
 */
public interface DqcRuleTemplateService {

  void insert(DqcRuleTemplateVo dqcRuleTemplateVo);

  void update(DqcRuleTemplateVo dqcRuleTemplateVo);

  void delete(Long delId);

  void deleteBulk(String ids);

  PageResultVO<DqcRuleTemplateListVo> searchPageList(DqcRuleTemplateVo dqcRuleTemplateVo,Pagination pagination);
}
