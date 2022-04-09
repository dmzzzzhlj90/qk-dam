package com.qk.dm.indicator.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.IdcFunction;
import com.qk.dm.indicator.entity.QIdcFunction;
import com.qk.dm.indicator.mapstruct.mapper.IdcFunctionMapper;
import com.qk.dm.indicator.params.dto.IdcFunctionDTO;
import com.qk.dm.indicator.params.dto.IdcFunctionPageDTO;
import com.qk.dm.indicator.params.vo.IdcFunctionVO;
import com.qk.dm.indicator.repositories.IdcFunctionRepository;
import com.qk.dm.indicator.service.IdcFunctionService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/9/1 2:48 下午
 * @since 1.0.0
 */
@Service
public class IdcFunctionServiceImpl implements IdcFunctionService {
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final QIdcFunction qIdcFunction = QIdcFunction.idcFunction;
  private final IdcFunctionRepository idcFunctionRepository;

  public IdcFunctionServiceImpl(
      EntityManager entityManager, IdcFunctionRepository idcFunctionRepository) {
    this.entityManager = entityManager;
    this.idcFunctionRepository = idcFunctionRepository;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public void insert(IdcFunctionDTO idcFunctionDTO) {
    IdcFunction idcFunction = IdcFunctionMapper.INSTANCE.useIdcFunction(idcFunctionDTO);
    idcFunctionRepository.save(idcFunction);
  }

  @Override
  public void update(Long id, IdcFunctionDTO idcFunctionDTO) {
    IdcFunction idcFunction = idcFunctionRepository.findById(id).orElse(null);
    if (idcFunction == null) {
      throw new BizException("当前要修改的函数id为：" + id + " 的数据不存在！！！");
    }
    IdcFunctionMapper.INSTANCE.useIdcFunction(idcFunctionDTO, idcFunction);
    idcFunctionRepository.save(idcFunction);
  }

  @Override
  public void delete(String ids) {
    Iterable<Long> idSet =
        Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<IdcFunction> idcFunctionList = idcFunctionRepository.findAllById(idSet);
    if (idcFunctionList.isEmpty()) {
      throw new BizException("当前要删除的函数id为：" + ids + " 的数据不存在！！！");
    }
    idcFunctionRepository.deleteAll(idcFunctionList);
  }

  @Override
  public IdcFunctionVO detail(Long id) {
    Optional<IdcFunction> idcFunction = idcFunctionRepository.findById(id);
    if (idcFunction.isEmpty()) {
      throw new BizException("当前要查询的函数id为：" + id + " 的数据不存在！！！");
    }
    return IdcFunctionMapper.INSTANCE.useIdcFunctionVO(idcFunction.get());
  }

  @Override
  public Map<String, List<IdcFunctionVO>> getList(String engine) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (!StringUtils.isEmpty(engine)) {
      booleanBuilder.and(qIdcFunction.engine.eq(engine));
    }
    List<IdcFunction> functionList =
        jpaQueryFactory.select(qIdcFunction).from(qIdcFunction).where(booleanBuilder).fetch();
    return functionList.stream()
        .map(IdcFunctionMapper.INSTANCE::useIdcFunctionVO)
        .collect(Collectors.groupingBy(IdcFunctionVO::getTypeName));
  }

  @Override
  public PageResultVO<IdcFunctionVO> listByPage(IdcFunctionPageDTO idcFunctionPageDTO) {
    Map<String, Object> map;
    try {
      map = queryByParams(idcFunctionPageDTO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<IdcFunction> list = (List<IdcFunction>) map.get("list");
    List<IdcFunctionVO> voList = IdcFunctionMapper.INSTANCE.useIdcFunctionVO(list);
    return new PageResultVO<>(
        (long) map.get("total"),
        idcFunctionPageDTO.getPagination().getPage(),
        idcFunctionPageDTO.getPagination().getSize(),
        voList);
  }

  private Map<String, Object> queryByParams(IdcFunctionPageDTO idcFunctionPageDTO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, idcFunctionPageDTO);
    Map<String, Object> result = new HashMap<>();
    long count =
        jpaQueryFactory
            .select(qIdcFunction.count())
            .from(qIdcFunction)
            .where(booleanBuilder)
            .fetchOne();
    List<IdcFunction> mtdLabelsList =
        jpaQueryFactory
            .select(qIdcFunction)
            .from(qIdcFunction)
            .where(booleanBuilder)
            .orderBy(qIdcFunction.id.asc())
            .offset(
                (long) (idcFunctionPageDTO.getPagination().getPage() - 1)
                    * idcFunctionPageDTO.getPagination().getSize())
            .limit(idcFunctionPageDTO.getPagination().getSize())
            .fetch();
    result.put("list", mtdLabelsList);
    result.put("total", count);
    return result;
  }

  public void checkCondition(BooleanBuilder booleanBuilder, IdcFunctionPageDTO idcFunctionPageDTO) {
    if (!StringUtils.isEmpty(idcFunctionPageDTO.getName())) {
      booleanBuilder.and(qIdcFunction.name.contains(idcFunctionPageDTO.getName()));
    }
    if (!StringUtils.isEmpty(idcFunctionPageDTO.getFunction())) {
      booleanBuilder.and(qIdcFunction.function.contains(idcFunctionPageDTO.getFunction()));
    }
    if (!StringUtils.isEmpty(idcFunctionPageDTO.getEngine())) {
      booleanBuilder.and(qIdcFunction.engine.eq(idcFunctionPageDTO.getEngine()));
    }
    if (!StringUtils.isEmpty(idcFunctionPageDTO.getTypeName())) {
      booleanBuilder.and(qIdcFunction.typeName.contains(idcFunctionPageDTO.getTypeName()));
    }
    if (!StringUtils.isEmpty(idcFunctionPageDTO.getBeginDay())
        && !StringUtils.isEmpty(idcFunctionPageDTO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate(
              "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qIdcFunction.gmtCreate);
      booleanBuilder.and(
          dateExpr.between(idcFunctionPageDTO.getBeginDay(), idcFunctionPageDTO.getEndDay()));
    }
  }
}
