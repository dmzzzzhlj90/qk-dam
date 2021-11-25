package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplatePageDto;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplateReleaseDto;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/8 7:46 下午
 * @since 1.0.0
 */
public interface DqcRuleTemplateService {

  void insert(DqcRuleTemplateVo dqcRuleTemplateVo);

  void update(DqcRuleTemplateVo dqcRuleTemplateVo);

  void deleteOne(Long delId);

  void deleteBulk(String ids);

  DqcRuleTemplateInfoVo detail(Long id);

  void release(DqcRuleTemplateReleaseDto dqcRuleTemplateReleaseDto);

  PageResultVO<DqcRuleTemplateInfoVo> searchPageList(
          DqcRuleTemplatePageDto dqcRuleTemplatePageDto, Pagination pagination);

  List<DqcRuleTemplateInfoVo> search(DqcRuleTemplatePageDto dqcRuleTemplateVo);
}
