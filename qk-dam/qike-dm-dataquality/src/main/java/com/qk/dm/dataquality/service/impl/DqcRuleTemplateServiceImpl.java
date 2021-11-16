package com.qk.dm.dataquality.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.druid.DbType;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dam.sqlbuilder.sqlparser.SqlParserFactory;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.QDqcRuleTemplate;
import com.qk.dm.dataquality.constant.DataSourceEnum;
import com.qk.dm.dataquality.mapstruct.mapper.DqcRuleTemplateMapper;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVo;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

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
  public static final Integer del_state_down = 1;
  public static final Integer publish_state_up = 1;

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
    // todo 添加创建人
    dqcRuleTemplate.setCreateUserid(1L);
    dqcRuleTemplateRepository.save(dqcRuleTemplate);
  }

  private void parseStatements(String engineType, String sql) {
    Arrays.asList(engineType.split(","))
        .forEach(
            i -> {
              if (Integer.parseInt(i) == DataSourceEnum.CALCULATE_ENGINE_HIVE.getCode()
                  && !SqlParserFactory.parseStatements(sql, DbType.hive)) {
                throw new BizException("本sql hive不适用！！！");
              }
              if (Integer.parseInt(i) == DataSourceEnum.CALCULATE_ENGINE_MYSQL.getCode()
                  && !SqlParserFactory.parseStatements(sql, DbType.mysql)) {
                throw new BizException("本sql mysql不适用！！！");
              }
            });
  }

  @Override
  public void update(DqcRuleTemplateVo dqcRuleTemplateVo) {
    if (dqcRuleTemplateVo.getId() != null && dqcRuleTemplateVo.getPublishState() != null) {
      DqcRuleTemplate dqcRuleTemplate = getInfoById(dqcRuleTemplateVo.getId());
      if (Objects.equals(dqcRuleTemplate.getPublishState(), publish_state_up)) {
        throw new BizException("上线规则模版不支持修改！！！");
      }
      // todo 添加修改人
      dqcRuleTemplate.setUpdateUserid(1L);
      dqcRuleTemplate.setPublishState(dqcRuleTemplateVo.getPublishState());
      dqcRuleTemplateRepository.save(dqcRuleTemplate);
    }
  }

  @Override
  public void delete(Long id) {
    // todo 工作流下线
    DqcRuleTemplate dqcRuleTemplate = getInfoById(id);
    if (Objects.equals(dqcRuleTemplate.getPublishState(), publish_state_up)) {
      throw new BizException("上线规则模版不支持删除！！！");
    }
    dqcRuleTemplate.setDelFlag(del_state_down);
    dqcRuleTemplateRepository.save(dqcRuleTemplate);
  }

  @Override
  public void deleteBulk(String ids) {
    // todo 工作流下线
    Iterable<Long> idList =
        Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<DqcRuleTemplate> idcTimeLimitList = dqcRuleTemplateRepository.findAllById(idList);
    if (idcTimeLimitList.isEmpty()) {
      throw new BizException("当前要删除的id为：" + ids + " 的数据，不存在！！！");
    }
    idcTimeLimitList.stream()
        .peek(
            i -> {
              if (Objects.equals(i.getPublishState(), publish_state_up)) {
                throw new BizException("上线规则模版不支持删除！！！");
              }
              i.setDelFlag(del_state_down);
            })
        .collect(Collectors.toList());
    dqcRuleTemplateRepository.saveAll(idcTimeLimitList);
  }

  @Override
  public DqcRuleTemplateInfoVo search(Long id) {
    return DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplateInfoVo(getInfoById(id));
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
          .orderBy(qDqcRuleTemplate.id.asc())
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
  }

  private DqcRuleTemplate getInfoById(Long id) {
    Optional<DqcRuleTemplate> info = dqcRuleTemplateRepository.findById(id);
    if (info.isEmpty()) {
      throw new BizException("id为：" + id + " 的模版不存在！！！");
    }
    return info.get();
  }
}
