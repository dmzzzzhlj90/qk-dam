package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplatePageDTO;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplateReleaseDTO;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVO;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVO;
import com.qk.dm.dataquality.vo.RuleTemplateConstantsVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/8 7:46 下午
 * @since 1.0.0
 */
public interface DqcRuleTemplateService {

  void insert(DqcRuleTemplateVO dqcRuleTemplateVo);

  void update(DqcRuleTemplateVO dqcRuleTemplateVo);

  void deleteOne(Long delId);

  void deleteBulk(String ids);

  DqcRuleTemplateInfoVO detail(Long id);

  void release(DqcRuleTemplateReleaseDTO dqcRuleTemplateReleaseDto);

  List<DqcRuleTemplateInfoVO> search(DqcRuleTemplatePageDTO dqcRuleTemplateVo);

  PageResultVO<DqcRuleTemplateInfoVO> searchPageList(DqcRuleTemplatePageDTO dqcRuleTemplatePageDto);

  RuleTemplateConstantsVO getRuLeTemplateConstants();
}
