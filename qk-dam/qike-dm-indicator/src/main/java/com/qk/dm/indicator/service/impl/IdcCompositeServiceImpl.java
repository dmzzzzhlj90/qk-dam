package com.qk.dm.indicator.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.indicator.common.property.IdcState;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.IdcComposite;
import com.qk.dm.indicator.entity.QIdcComposite;
import com.qk.dm.indicator.mapstruct.mapper.IdcCompositeMapper;
import com.qk.dm.indicator.params.dto.IdcCompositeDTO;
import com.qk.dm.indicator.params.dto.IdcCompositePageDTO;
import com.qk.dm.indicator.params.vo.IdcCompositePageVO;
import com.qk.dm.indicator.params.vo.IdcCompositeVO;
import com.qk.dm.indicator.repositories.IdcCompositeRepository;
import com.qk.dm.indicator.service.IdcCompositeService;
import com.querydsl.core.BooleanBuilder;
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
 * @date 2021/9/3 4:46 下午
 * @since 1.0.0
 */
@Service
public class IdcCompositeServiceImpl implements IdcCompositeService {

  private JPAQueryFactory jpaQueryFactory;
  private final QIdcComposite qIdcComposite = QIdcComposite.idcComposite;
  private final EntityManager entityManager;
  private final IdcCompositeRepository idcCompositeRepository;

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  public IdcCompositeServiceImpl(
      EntityManager entityManager, IdcCompositeRepository idcCompositeRepository) {
    this.entityManager = entityManager;
    this.idcCompositeRepository = idcCompositeRepository;
  }

  @Override
  public void insert(IdcCompositeDTO idcCompositeDTO) {
    IdcComposite idcComposite = IdcCompositeMapper.INSTANCE.useIdc(idcCompositeDTO);
    idcCompositeRepository.save(idcComposite);
  }

  @Override
  public void update(Long id, IdcCompositeDTO idcCompositeDTO) {
    IdcComposite idcComposite = idcCompositeRepository.findById(id).orElse(null);
    if (idcComposite == null) {
      throw new BizException("当前要修改的复合指标id为：" + id + " 的数据不存在！！！");
    }
    IdcCompositeMapper.INSTANCE.useIdc(idcCompositeDTO, idcComposite);
    idcCompositeRepository.save(idcComposite);
  }

  @Override
  public void publish(Long id) {
    IdcComposite idcComposite = idcCompositeRepository.findById(id).orElse(null);
    if (idcComposite == null) {
      throw new BizException("当前要修改的复合指标id为：" + id + " 的数据不存在！！！");
    }
    idcComposite.setIndicatorStatus(IdcState.Composite.ONLINE);
    idcCompositeRepository.save(idcComposite);
  }

  @Override
  public void offline(Long id) {
    IdcComposite idcComposite = idcCompositeRepository.findById(id).orElse(null);
    if (idcComposite == null) {
      throw new BizException("当前要修改的复合指标id为：" + id + " 的数据不存在！！！");
    }
    idcComposite.setIndicatorStatus(IdcState.Composite.OFFLINE);
    idcCompositeRepository.save(idcComposite);
  }

  @Override
  public void delete(String ids) {
    Iterable<Long> idSet =
        Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<IdcComposite> idcCompositeList = idcCompositeRepository.findAllById(idSet);
    if (idcCompositeList.isEmpty()) {
      throw new BizException("当前要删除的复合指标id为：" + ids + " 的数据不存在！！！");
    }
    idcCompositeRepository.deleteAll(idcCompositeList);
  }

  @Override
  public IdcCompositeVO detail(Long id) {
    Optional<IdcComposite> idcComposite = idcCompositeRepository.findById(id);
    if (idcComposite.isEmpty()) {
      throw new BizException("当前要查询的复合指标id为：" + id + " 的数据不存在！！！");
    }
    return IdcCompositeMapper.INSTANCE.useIdcVO(idcComposite.get());
  }

  @Override
  public List<IdcCompositeVO> getList() {
    List<IdcComposite> idcCompositeList = idcCompositeRepository.findAll();
    return IdcCompositeMapper.INSTANCE.useIdcVO(idcCompositeList);
  }

  @Override
  public PageResultVO<IdcCompositePageVO> listByPage(IdcCompositePageDTO idcPageDTO) {
    Map<String, Object> map;
    try {
      map = queryByParams(idcPageDTO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<IdcComposite> list = (List<IdcComposite>) map.get("list");
    List<IdcCompositePageVO> voList = IdcCompositeMapper.INSTANCE.useIdcPageVO(list);
    return new PageResultVO<>(
        (long) map.get("total"),
        idcPageDTO.getPagination().getPage(),
        idcPageDTO.getPagination().getSize(),
        voList);
  }

  private Map<String, Object> queryByParams(IdcCompositePageDTO idcPageDTO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, idcPageDTO);
    Map<String, Object> result = new HashMap<>();
    long count =
        jpaQueryFactory
            .select(qIdcComposite.count())
            .from(qIdcComposite)
            .where(booleanBuilder)
            .fetchOne();
    List<IdcComposite> mtdLabelsList =
        jpaQueryFactory
            .select(qIdcComposite)
            .from(qIdcComposite)
            .where(booleanBuilder)
            .orderBy(qIdcComposite.id.asc())
            .offset(
                (long) (idcPageDTO.getPagination().getPage() - 1)
                    * idcPageDTO.getPagination().getSize())
            .limit(idcPageDTO.getPagination().getSize())
            .fetch();
    result.put("list", mtdLabelsList);
    result.put("total", count);
    return result;
  }

  public void checkCondition(BooleanBuilder booleanBuilder, IdcCompositePageDTO idcPageDTO) {
    if (!StringUtils.isEmpty(idcPageDTO.getNameOrCode())) {
      booleanBuilder.and(
          qIdcComposite
              .compositeIndicatorName
              .contains(idcPageDTO.getNameOrCode())
              .or(qIdcComposite.compositeIndicatorCode.contains(idcPageDTO.getNameOrCode())));
    }
    if (!StringUtils.isEmpty(idcPageDTO.getCompositeIndicatorName())) {
      booleanBuilder.and(
          qIdcComposite.compositeIndicatorName.contains(idcPageDTO.getCompositeIndicatorName()));
    }
    if (!StringUtils.isEmpty(idcPageDTO.getCompositeIndicatorCode())) {
      booleanBuilder.and(
          qIdcComposite.compositeIndicatorCode.contains(idcPageDTO.getCompositeIndicatorCode()));
    }
    if (!StringUtils.isEmpty(idcPageDTO.getBeginDay())
        && !StringUtils.isEmpty(idcPageDTO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate(
              "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qIdcComposite.gmtCreate);
      booleanBuilder.and(dateExpr.between(idcPageDTO.getBeginDay(), idcPageDTO.getEndDay()));
    }
  }
}
