package com.qk.dm.dataquality.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dam.sqlbuilder.sqlparser.SqlParserFactory;
import com.qk.dm.dataquality.constant.DataSourceEnum;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.TempTypeEnum;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.QDqcRuleTemplate;
import com.qk.dm.dataquality.mapstruct.mapper.DqcRuleTemplateMapper;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplateReleaseDto;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

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
    parseStatements(dqcRuleTemplateVo.getEngineType(), dqcRuleTemplateVo.getTempSql());
    DqcRuleTemplate dqcRuleTemplate =
        DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplate(dqcRuleTemplateVo);
    dqcRuleTemplate.setEngineType(DqcConstant.ENGINE_TYPE);
    dqcRuleTemplate.setTempType(TempTypeEnum.CUSTOMIZE.getCode());
    dqcRuleTemplate.setPublishState(DqcConstant.PUBLISH_STATE_DOWN);
    // todo 添加创建人
    dqcRuleTemplate.setCreateUserid(1L);
    dqcRuleTemplate.setDelFlag(0);
    dqcRuleTemplateRepository.save(dqcRuleTemplate);
  }

  @Override
  public void update(DqcRuleTemplateVo dqcRuleTemplateVo) {
    DqcRuleTemplate dqcRuleTemplate = getInfoById(dqcRuleTemplateVo.getId());
    checkPublishState(dqcRuleTemplate, "上线规则模版不支持修改！！！");
    DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplate(dqcRuleTemplateVo, dqcRuleTemplate);
    // todo 添加修改人
    dqcRuleTemplate.setUpdateUserid(1L);
    dqcRuleTemplateRepository.save(dqcRuleTemplate);
  }

  @Override
  public void release(DqcRuleTemplateReleaseDto dqcRuleTemplateReleaseDto) {
    DqcRuleTemplate dqcRuleTemplate = getInfoById(dqcRuleTemplateReleaseDto.getId());
    // todo 添加修改人
    dqcRuleTemplate.setUpdateUserid(1L);
    dqcRuleTemplate.setPublishState(dqcRuleTemplateReleaseDto.getPublishState());
    dqcRuleTemplateRepository.save(dqcRuleTemplate);
  }

  @Override
  public void deleteOne(Long id) {
    // todo 工作流下线
    DqcRuleTemplate dqcRuleTemplate = getInfoById(id);
    checkPublishState(dqcRuleTemplate, "上线规则模版不支持删除！！！");
    dqcRuleTemplate.setDelFlag(DqcConstant.DEL_FLAG_DEL);
    dqcRuleTemplateRepository.save(dqcRuleTemplate);
  }

  @Override
  public void deleteBulk(String ids) {
    // todo 工作流下线
    Iterable<Long> idList =
        Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<DqcRuleTemplate> ruleTemplates = dqcRuleTemplateRepository.findAllById(idList);
    checkInfo(ids, ruleTemplates);
    ruleTemplates.stream()
        .peek(
            ruleTemplate -> {
              checkPublishState(ruleTemplate, "上线规则模版不支持删除！！！");
              ruleTemplate.setDelFlag(DqcConstant.DEL_FLAG_DEL);
            })
        .collect(Collectors.toList());
    dqcRuleTemplateRepository.saveAll(ruleTemplates);
  }

  @Override
  public DqcRuleTemplateInfoVo detail(Long id) {
    return DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplateInfoVo(getInfoById(id));
  }

  @Override
  public List<DqcRuleTemplateInfoVo> search(DqcRuleTemplateVo dqcRuleTemplateVo) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(dqcRuleTemplateVo, booleanBuilder);
    List<DqcRuleTemplate> list =
        jpaQueryFactory
            .select(qDqcRuleTemplate)
            .from(qDqcRuleTemplate)
            .where(booleanBuilder)
            .orderBy(qDqcRuleTemplate.tempType.desc(), qDqcRuleTemplate.gmtModified.desc())
            .fetch();
    return DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplateInfoVo(list);
  }

  @Override
  public PageResultVO<DqcRuleTemplateInfoVo> searchPageList(
      DqcRuleTemplateVo dqcRuleTemplateVo, Pagination pagination) {
    Map<String, Object> map = queryParams(dqcRuleTemplateVo, pagination);
    List<DqcRuleTemplate> list = (List<DqcRuleTemplate>) map.get("list");
    List<DqcRuleTemplateInfoVo> voList =
        DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplateInfoVo(list);
    return new PageResultVO<>(
        (long) map.get("total"), pagination.getPage(), pagination.getSize(), voList);
  }

  private Map<String, Object> queryParams(
      DqcRuleTemplateVo dqcRuleTemplateVo, Pagination pagination) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(dqcRuleTemplateVo, booleanBuilder);
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
          .orderBy(qDqcRuleTemplate.tempType.desc(), qDqcRuleTemplate.gmtModified.desc())
          .offset((pagination.getPage() - 1) * pagination.getSize())
          .limit(pagination.getSize())
          .fetch();
    } catch (Exception e) {
      LOG.debug("查询列表出错{}", e.getMessage());
      throw new BizException("查询失败!!!");
    }
  }

  public void checkCondition(DqcRuleTemplateVo dqcRuleTemplateVo, BooleanBuilder booleanBuilder) {
    if (dqcRuleTemplateVo.getDirId() != null) {
      booleanBuilder.and(qDqcRuleTemplate.dirId.eq(dqcRuleTemplateVo.getDirId()));
    }
    if (dqcRuleTemplateVo.getTempName() != null) {
      booleanBuilder.and(qDqcRuleTemplate.tempName.contains(dqcRuleTemplateVo.getTempName()));
    }
    if (dqcRuleTemplateVo.getDimensionId() != null) {
      booleanBuilder.and(qDqcRuleTemplate.dimensionId.eq(dqcRuleTemplateVo.getDimensionId()));
    }
    if (dqcRuleTemplateVo.getEngineType() != null) {
      booleanBuilder.and(qDqcRuleTemplate.engineType.contains(dqcRuleTemplateVo.getEngineType()));
    }
  }

  private DqcRuleTemplate getInfoById(Long id) {
    Optional<DqcRuleTemplate> info = dqcRuleTemplateRepository.findById(id);
    checkInfo(id, info);
    return info.get();
  }

  private void checkInfo(Long id, Optional<DqcRuleTemplate> info) {
    if (info.isEmpty()) {
      throw new BizException("id为：" + id + " 的模版不存在！！！");
    }
  }

  private void checkInfo(String ids, List<DqcRuleTemplate> idcTimeLimitList) {
    if (CollectionUtils.isEmpty(idcTimeLimitList)) {
      throw new BizException("当前要删除的id为：" + ids + " 的数据，不存在！！！");
    }
  }

  private void checkPublishState(DqcRuleTemplate dqcRuleTemplate, String s) {
    if (Objects.equals(dqcRuleTemplate.getPublishState(), DqcConstant.PUBLISH_STATE_UP)) {
      throw new BizException(s);
    }
  }

  private void parseStatements(String engineType, String sql) {
    Arrays.stream(engineType.split(","))
        .peek(i -> checkSql(sql, DataSourceEnum.fromValue(Integer.parseInt(i))))
        .collect(Collectors.toList());
  }

  private void checkSql(String sql, DataSourceEnum dataSourceEnums) {
    if (!SqlParserFactory.parseStatements(sql, dataSourceEnums.getDbType())) {
      throw new BizException("本sql " + dataSourceEnums.getName() + " 不适用！！！");
    }
  }
}
