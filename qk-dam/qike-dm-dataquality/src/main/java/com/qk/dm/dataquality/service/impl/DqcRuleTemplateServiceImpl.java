package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateListVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * @author shenpj
 * @date 2021/11/8 7:46 下午
 * @since 1.0.0
 */
@Service
public class DqcRuleTemplateServiceImpl implements DqcRuleTemplateService {

  private final DqcRuleTemplateRepository dqcRuleTemplateRepository;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;

  public DqcRuleTemplateServiceImpl(
      DqcRuleTemplateRepository dqcRuleTemplateRepository, EntityManager entityManager) {
    this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public List<DqcRuleTemplateListVo> searchList() {
    return null;
  }

  @Override
  public PageResultVO<DqcRuleTemplateListVo> searchPageList(Pagination pagination) {
    Map<String, Object> map = null;
    try {
      //            map = queryParams(pagination);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DqcRuleTemplate> list = (List<DqcRuleTemplate>) map.get("list");
    List<DqcRuleTemplateListVo> voList = null;
    return new PageResultVO<>(
        (long) map.get("total"), pagination.getPage(), pagination.getSize(), voList);
  }

  @Override
  public void insert(DqcRuleTemplateVo dqcRuleTemplateVo) {}

  @Override
  public void update(DqcRuleTemplateVo dqcRuleTemplateVo) {}

  @Override
  public void delete(Integer delId) {}

  @Override
  public void deleteBulk(Integer delId) {}
}
