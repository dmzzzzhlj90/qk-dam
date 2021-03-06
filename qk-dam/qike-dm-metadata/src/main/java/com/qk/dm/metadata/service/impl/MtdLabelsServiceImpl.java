package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.metedata.property.SynchStateProperty;
import com.qk.dm.metadata.entity.MtdLabels;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.entity.QMtdLabels;
import com.qk.dm.metadata.mapstruct.mapper.MtdLabelsMapper;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.repositories.MtdLabelsRepository;
import com.qk.dm.metadata.service.MtdLabelsService;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class MtdLabelsServiceImpl implements MtdLabelsService {

  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final QMtdLabels qMtdLabels = QMtdLabels.mtdLabels;
  private final MtdLabelsRepository mtdLabelsRepository;
  private final MtdLabelsAtlasRepository mtdLabelsAtlasRepository;

  public MtdLabelsServiceImpl(
      EntityManager entityManager,
      MtdLabelsRepository mtdLabelsRepository,
      MtdLabelsAtlasRepository mtdLabelsAtlasRepository) {
    this.entityManager = entityManager;
    this.mtdLabelsRepository = mtdLabelsRepository;
    this.mtdLabelsAtlasRepository = mtdLabelsAtlasRepository;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public void insert(MtdLabelsVO mtdLabelsVO) {
    Predicate predicate = qMtdLabels.name.eq(mtdLabelsVO.getName());
    if (mtdLabelsRepository.exists(predicate)) {
      throw new BizException("?????????????????????????????????" + mtdLabelsVO.getName() + " ???????????????????????????");
    }
    MtdLabels mtdLabels = MtdLabelsMapper.INSTANCE.useMtdLabels(mtdLabelsVO);
    mtdLabelsRepository.save(mtdLabels);
  }

  @Override
  public void update(Long id, MtdLabelsVO mtdLabelsVO) {
    Predicate predicate = qMtdLabels.name.eq(mtdLabelsVO.getName()).and(qMtdLabels.id.ne(id));
    if (mtdLabelsRepository.exists(predicate)) {
      throw new BizException("?????????????????????????????????" + mtdLabelsVO.getName() + " ???????????????????????????");
    }
    MtdLabels mtdLabels = mtdLabelsRepository.findById(id).orElse(null);
    if (mtdLabels == null) {
      throw new BizException("????????????????????????id??????" + id + " ???????????????????????????");
    }
    MtdLabelsMapper.INSTANCE.updateMtdLabelsVO(mtdLabelsVO, mtdLabels);
    // todo ????????????????????????????????????atlas???????????????
    mtdLabelsRepository.saveAndFlush(mtdLabels);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(String ids) {
    Iterable<Long> idSet =
        Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<MtdLabels> labelAllList = mtdLabelsRepository.findAllById(idSet);
    List<MtdLabelsAtlas> labelsAtlases = synchLabels(labelAllList);
    if (!labelsAtlases.isEmpty()) {
      mtdLabelsAtlasRepository.saveAll(labelsAtlases);
    }
    mtdLabelsRepository.deleteAll(labelAllList);
  }

  public List<MtdLabelsAtlas> synchLabels(List<MtdLabels> labelAllList) {
    // todo ??????????????? ????????????????????????????????????
    List<MtdLabelsAtlas> labelsAtlases =
        mtdLabelsAtlasRepository.findAllBySynchStatusIsNot(SynchStateProperty.LabelsAtlas.DELETE);
    List<String> labelsNameList =
        labelAllList.stream().map(MtdLabels::getName).collect(Collectors.toList());
    return labelsAtlases.stream()
        .filter(
            i ->
                CollectionUtils.containsAny(
                    labelsNameList, Arrays.asList(i.getLabels().split(","))))
        .peek(
            mla -> {
              String labels =
                  Stream.of(mla.getLabels().split(","))
                      .filter(y -> !labelsNameList.contains(y))
                      .collect(Collectors.joining(","));
              mla.setLabels(labels.isEmpty() ? mla.getLabels() : labels);
              mla.setSynchStatus(
                  labels.isEmpty()
                      ? SynchStateProperty.LabelsAtlas.DELETE
                      : SynchStateProperty.LabelsAtlas.NOT_SYNCH);
            })
        .collect(Collectors.toList());
  }

  @Override
  public PageResultVO<MtdLabelsInfoVO> listByPage(MtdLabelsListVO mtdLabelsListVO) {
    Map<String, Object> map;
    try {
      map = queryMtdLabelsByParams(mtdLabelsListVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("????????????!!!");
    }
    List<MtdLabels> list = (List<MtdLabels>) map.get("list");
    List<MtdLabelsInfoVO> mtdLabelsVOList =
        list.stream()
            .map(MtdLabelsMapper.INSTANCE::useMtdLabelsInfoVO)
            .collect(Collectors.toList());
    return new PageResultVO<>(
        (long) map.get("total"),
        mtdLabelsListVO.getPagination().getPage(),
        mtdLabelsListVO.getPagination().getSize(),
        mtdLabelsVOList);
  }

  @Override
  public List<MtdLabelsInfoVO> listByAll(MtdLabelsVO mtdLabelsVO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, MtdLabelsMapper.INSTANCE.useMtdLabelsListVO(mtdLabelsVO));
    List<MtdLabels> mtdLabelsList =
        jpaQueryFactory
            .select(qMtdLabels)
            .from(qMtdLabels)
            .where(booleanBuilder)
            .orderBy(qMtdLabels.id.asc())
            .fetch();
    return mtdLabelsList.stream()
        .map(MtdLabelsMapper.INSTANCE::useMtdLabelsInfoVO)
        .collect(Collectors.toList());
  }

  private Map<String, Object> queryMtdLabelsByParams(MtdLabelsListVO mtdLabelsListVO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, mtdLabelsListVO);
    Map<String, Object> result = new HashMap<>();
    long count =
        jpaQueryFactory
            .select(qMtdLabels.count())
            .from(qMtdLabels)
            .where(booleanBuilder)
            .fetchOne();
    List<MtdLabels> mtdLabelsList =
        jpaQueryFactory
            .select(qMtdLabels)
            .from(qMtdLabels)
            .where(booleanBuilder)
            .orderBy(qMtdLabels.id.asc())
            .offset(
                (long) (mtdLabelsListVO.getPagination().getPage() - 1)
                    * mtdLabelsListVO.getPagination().getSize())
            .limit(mtdLabelsListVO.getPagination().getSize())
            .fetch();
    result.put("list", mtdLabelsList);
    result.put("total", count);
    return result;
  }

  public void checkCondition(BooleanBuilder booleanBuilder, MtdLabelsListVO mtdLabelsListVO) {
    if (!StringUtils.isEmpty(mtdLabelsListVO.getName())) {
      booleanBuilder.and(qMtdLabels.name.contains(mtdLabelsListVO.getName()));
    }
    if (!StringUtils.isEmpty(mtdLabelsListVO.getBeginDay())
        && !StringUtils.isEmpty(mtdLabelsListVO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qMtdLabels.gmtCreate);
      booleanBuilder.and(
          dateExpr.between(mtdLabelsListVO.getBeginDay(), mtdLabelsListVO.getEndDay()));
    }
  }
}
