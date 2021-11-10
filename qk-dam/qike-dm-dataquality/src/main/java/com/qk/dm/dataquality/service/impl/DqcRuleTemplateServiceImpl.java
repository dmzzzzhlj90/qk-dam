package com.qk.dm.dataquality.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.QDqcRuleTemplate;
import com.qk.dm.dataquality.mapstruct.mapper.DqcRuleTemplateMapper;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateListVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author shenpj
 * @date 2021/11/8 7:46 下午
 * @since 1.0.0
 */
@Service
public class DqcRuleTemplateServiceImpl implements DqcRuleTemplateService {
  private static final Log LOG = LogFactory.get("规则模版操作");
  private final DqcRuleTemplateRepository dqcRuleTemplateRepository;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final QDqcRuleTemplate qDqcRuleTemplate = QDqcRuleTemplate.dqcRuleTemplate;
  public static final Integer del_state_up = 0;
  public static final Integer del_state_down = 1;

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
  public void insert(DqcRuleTemplateVo dqcRuleTemplateVo) {
    DqcRuleTemplate dqcRuleTemplate =
        DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplate(dqcRuleTemplateVo);
    // todo 添加创建人
    dqcRuleTemplate.setCreateUserid(1L);
    dqcRuleTemplateRepository.save(dqcRuleTemplate);
  }

  @Override
  public void update(Long id, DqcRuleTemplateVo dqcRuleTemplateVo) {
    DqcRuleTemplate dqcRuleTemplate = getOneByNotDel(id);
    if (dqcRuleTemplateVo.getPublishState() != null) {
      // todo 添加修改人
      dqcRuleTemplate.setUpdateUserid(1L);
      dqcRuleTemplate.setPublishState(dqcRuleTemplateVo.getPublishState());
      dqcRuleTemplateRepository.save(dqcRuleTemplate);
    }
  }

  @Override
  public void delete(Long id) {
    DqcRuleTemplate dqcRuleTemplate = getOneByNotDel(id);
    dqcRuleTemplate.setDelFlag(del_state_down);
    dqcRuleTemplateRepository.save(dqcRuleTemplate);
  }

  @Override
  public void deleteBulk(Long delId) {}

  @Override
  public PageResultVO<DqcRuleTemplateListVo> searchPageList(Pagination pagination) {
    Map<String, Object> map = queryParams(pagination);
    List<DqcRuleTemplate> list = (List<DqcRuleTemplate>) map.get("list");
    List<DqcRuleTemplateListVo> voList =
        DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplateListVo(list);
    return new PageResultVO<>(
        (long) map.get("total"), pagination.getPage(), pagination.getSize(), voList);
  }

  private Map<String, Object> queryParams(Pagination pagination) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder);
    Map<String, Object> result = new HashMap<>(2);
    result.put("list", getTemplateList(pagination, booleanBuilder));
    result.put("total", getCount(booleanBuilder));
    return result;
  }

  private long getCount(BooleanBuilder booleanBuilder) {
    try {
      return jpaQueryFactory
          .select(qDqcRuleTemplate.count())
          .from(qDqcRuleTemplate)
          .where(booleanBuilder)
          .fetchOne();
    } catch (Exception e) {
      LOG.debug("查询总数量出错{}", e.getMessage());
      throw new BizException("查询失败!!!");
    }
  }

  private List<DqcRuleTemplate> getTemplateList(
      Pagination pagination, BooleanBuilder booleanBuilder) {
    try {
      return jpaQueryFactory
          .select(qDqcRuleTemplate)
          .from(qDqcRuleTemplate)
          .where(booleanBuilder)
          .orderBy(qDqcRuleTemplate.id.asc())
          .offset((pagination.getPage() - 1) * pagination.getSize())
          .limit(pagination.getSize())
          .fetch();
    } catch (Exception e) {
      LOG.debug("查询列表出错{}", e.getMessage());
      throw new BizException("查询失败!!!");
    }
  }

  public void checkCondition(BooleanBuilder booleanBuilder) {
    booleanBuilder.and(qDqcRuleTemplate.delFlag.eq(del_state_up));
  }

  private DqcRuleTemplate getOneByNotDel(Long id) {
    Optional<DqcRuleTemplate> one = dqcRuleTemplateRepository.findById(id);
    if (one.isPresent()) {
      throw new BizException("id为：" + id + " 的模版不存在！！！");
    }
    return one.get();
  }
}
