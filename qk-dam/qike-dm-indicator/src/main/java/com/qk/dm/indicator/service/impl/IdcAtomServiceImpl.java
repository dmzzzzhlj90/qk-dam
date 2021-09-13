package com.qk.dm.indicator.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.indicator.common.property.IdcState;
import com.qk.dam.indicator.common.sqlbuilder.SqlBuilder;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.IdcAtom;
import com.qk.dm.indicator.entity.QIdcAtom;
import com.qk.dm.indicator.mapstruct.mapper.IdcAtomMapper;
import com.qk.dm.indicator.params.dto.IdcAtomDTO;
import com.qk.dm.indicator.params.dto.IdcAtomPageDTO;
import com.qk.dm.indicator.params.vo.IdcAtomPageVO;
import com.qk.dm.indicator.params.vo.IdcAtomVO;
import com.qk.dm.indicator.repositories.IdcAtomRepository;
import com.qk.dm.indicator.service.IdcAtomService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/9/2 8:40 下午
 * @since 1.0.0
 */
@Service
public class IdcAtomServiceImpl implements IdcAtomService {
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final QIdcAtom qIdcAtom = QIdcAtom.idcAtom;
  private final IdcAtomRepository idcAtomRepository;

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  public IdcAtomServiceImpl(EntityManager entityManager, IdcAtomRepository idcAtomRepository) {
    this.entityManager = entityManager;
    this.idcAtomRepository = idcAtomRepository;
  }

  @Override
  public void insert(IdcAtomDTO idcAtomDTO) {
    IdcAtom idcAtom = IdcAtomMapper.INSTANCE.useIdcAtom(idcAtomDTO);
    idcAtom.setSqlSentence(SqlBuilder.atomicSql(idcAtom.getExpression(),idcAtom.getSqlSentence()));
    idcAtomRepository.save(idcAtom);
  }

  @Override
  public void update(Long id, IdcAtomDTO idcAtomDTO) {
    IdcAtom idcAtom = idcAtomRepository.findById(id).orElse(null);
    if (idcAtom == null) {
      throw new BizException("当前要修改的原子指标id为：" + id + " 的数据不存在！！！");
    }
    IdcAtomMapper.INSTANCE.useIdcAtom(idcAtomDTO, idcAtom);
    idcAtom.setSqlSentence(SqlBuilder.atomicSql(idcAtom.getExpression(),idcAtom.getSqlSentence()));
    idcAtomRepository.saveAndFlush(idcAtom);
  }

  @Override
  public void publish(Long id) {
    IdcAtom idcAtom = idcAtomRepository.findById(id).orElse(null);
    if (idcAtom == null) {
      throw new BizException("当前要修改的原子指标id为：" + id + " 的数据不存在！！！");
    }
    idcAtom.setIndicatorStatus(IdcState.ONLINE);
    idcAtomRepository.save(idcAtom);
  }

  @Override
  public void offline(Long id) {
    IdcAtom idcAtom = idcAtomRepository.findById(id).orElse(null);
    if (idcAtom == null) {
      throw new BizException("当前要修改的原子指标id为：" + id + " 的数据不存在！！！");
    }
    idcAtom.setIndicatorStatus(IdcState.OFFLINE);
    idcAtomRepository.save(idcAtom);
  }

  @Override
  public void delete(String ids) {
    Iterable<Long> idSet =
        Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<IdcAtom> idcAtomList = idcAtomRepository.findAllById(idSet);
    if (idcAtomList.isEmpty()) {
      throw new BizException("当前要删除的原子指标id为：" + ids + " 的数据不存在！！！");
    }
    idcAtomRepository.deleteAll(idcAtomList);
  }

  @Override
  public IdcAtomVO detail(Long id) {
    Optional<IdcAtom> idcAtom = idcAtomRepository.findById(id);
    if (idcAtom.isEmpty()) {
      throw new BizException("当前要查询的原子指标id为：" + id + " 的数据不存在！！！");
    }
    return IdcAtomMapper.INSTANCE.useIdcAtomVO(idcAtom.get());
  }

  @Override
  public IdcAtomVO getDetailByCode(String code) {
    Predicate predicate = qIdcAtom.atomIndicatorCode.eq(code);
    Optional<IdcAtom> idcAtom = idcAtomRepository.findOne(predicate);
    if (idcAtom.isEmpty()) {
      throw new BizException("当前要查询的原子指标编码为：" + code + " 的数据不存在！！！");
    }
    return IdcAtomMapper.INSTANCE.useIdcAtomVO(idcAtom.get());
  }

  @Override
  public List<IdcAtomVO> getList() {
    List<IdcAtom> idcAtomList = idcAtomRepository.findAll();
    return IdcAtomMapper.INSTANCE.useIdcAtomVO(idcAtomList);
  }

  @Override
  public PageResultVO<IdcAtomPageVO> listByPage(IdcAtomPageDTO idcAtomPageDTO) {
    Map<String, Object> map;
    try {
      map = queryByParams(idcAtomPageDTO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<IdcAtom> list = (List<IdcAtom>) map.get("list");
    List<IdcAtomPageVO> voList = IdcAtomMapper.INSTANCE.useIdcAtomPageVO(list);
    return new PageResultVO<>(
        (long) map.get("total"),
        idcAtomPageDTO.getPagination().getPage(),
        idcAtomPageDTO.getPagination().getSize(),
        voList);
  }

  private Map<String, Object> queryByParams(IdcAtomPageDTO idcAtomPageDTO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, idcAtomPageDTO);
    Map<String, Object> result = new HashMap<>();
    long count =
        jpaQueryFactory.select(qIdcAtom.count()).from(qIdcAtom).where(booleanBuilder).fetchOne();
    List<IdcAtom> mtdLabelsList =
        jpaQueryFactory
            .select(qIdcAtom)
            .from(qIdcAtom)
            .where(booleanBuilder)
            .orderBy(qIdcAtom.id.asc())
            .offset(
                (long) (idcAtomPageDTO.getPagination().getPage() - 1)
                    * idcAtomPageDTO.getPagination().getSize())
            .limit(idcAtomPageDTO.getPagination().getSize())
            .fetch();
    result.put("list", mtdLabelsList);
    result.put("total", count);
    return result;
  }

  public void checkCondition(BooleanBuilder booleanBuilder, IdcAtomPageDTO idcAtomPageDTO) {
    if (!StringUtils.isEmpty(idcAtomPageDTO.getNameOrCode())) {
      booleanBuilder.and(
          qIdcAtom
              .atomIndicatorName
              .contains(idcAtomPageDTO.getNameOrCode())
              .or(qIdcAtom.atomIndicatorCode.contains(idcAtomPageDTO.getNameOrCode())));
    }
    if (!StringUtils.isEmpty(idcAtomPageDTO.getAtomIndicatorName())) {
      booleanBuilder.and(
          qIdcAtom.atomIndicatorName.contains(idcAtomPageDTO.getAtomIndicatorName()));
    }
    if (!StringUtils.isEmpty(idcAtomPageDTO.getAtomIndicatorCode())) {
      booleanBuilder.and(
          qIdcAtom.atomIndicatorCode.contains(idcAtomPageDTO.getAtomIndicatorCode()));
    }
    if (!StringUtils.isEmpty(idcAtomPageDTO.getBeginDay())
        && !StringUtils.isEmpty(idcAtomPageDTO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qIdcAtom.gmtCreate);
      booleanBuilder.and(
          dateExpr.between(idcAtomPageDTO.getBeginDay(), idcAtomPageDTO.getEndDay()));
    }
  }
}
