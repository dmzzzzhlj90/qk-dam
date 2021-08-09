package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.entity.MtdClassify;
import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.entity.QMtdClassify;
import com.qk.dm.metadata.mapstruct.mapper.MtdClassifyMapper;
import com.qk.dm.metadata.repositories.MtdClassifyAtlasRepository;
import com.qk.dm.metadata.repositories.MtdClassifyRepository;
import com.qk.dm.metadata.service.MtdClassifyService;
import com.qk.dm.metadata.vo.MtdClassifyInfoVO;
import com.qk.dm.metadata.vo.MtdClassifyListVO;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import com.qk.dm.metadata.vo.PageResultVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangzp
 * @date 2021/7/31 13:10
 * @since 1.0.0
 */
@Service
public class MtdClassifyServiceImpl implements MtdClassifyService {
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final QMtdClassify qMtdClassify = QMtdClassify.mtdClassify;
  private final MtdClassifyRepository mtdClassifyRepository;
  private final MtdClassifyAtlasRepository mtdClassifyAtlasRepository;

  @Autowired
  public MtdClassifyServiceImpl(
      EntityManager entityManager,
      MtdClassifyRepository mtdClassifyRepository,
      MtdClassifyAtlasRepository mtdClassifyAtlasRepository) {
    this.entityManager = entityManager;
    this.mtdClassifyRepository = mtdClassifyRepository;
    this.mtdClassifyAtlasRepository = mtdClassifyAtlasRepository;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public void insert(MtdClassifyVO mtdClassifyVO) {
    Predicate predicate =
        qMtdClassify.name.eq(mtdClassifyVO.getName()).and(qMtdClassify.synchStatus.ne(-1));
    if (mtdClassifyRepository.exists(predicate)) {
      throw new BizException("当前要新增的分类为：" + mtdClassifyVO.getName() + " 的数据，已存在！！！");
    }
    MtdClassify mtdClassify = MtdClassifyMapper.INSTANCE.useMtdClassify(mtdClassifyVO);
    mtdClassify.setSynchStatus(2);
    mtdClassifyRepository.save(mtdClassify);
  }

  @Override
  public void update(Long id, MtdClassifyVO mtdClassifyVO) {
    Predicate predicate =
        qMtdClassify
            .name
            .eq(mtdClassifyVO.getName())
            .and(qMtdClassify.synchStatus.ne(-1))
            .and(qMtdClassify.id.ne(id));
    if (mtdClassifyRepository.exists(predicate)) {
      throw new BizException("当前要修改的分类为：" + mtdClassifyVO.getName() + " 的数据，已存在！！！");
    }
    MtdClassify mtdClassify = mtdClassifyRepository.findById(id).orElse(new MtdClassify());
    MtdClassifyMapper.INSTANCE.updateMtdClassifyVO(mtdClassifyVO, mtdClassify);
    mtdClassify.setSynchStatus(mtdClassify.getSynchStatus() == 2 ? 2 : 0);
    mtdClassifyRepository.saveAndFlush(mtdClassify);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Iterable<Long> idSet = idList.stream().map(Long::valueOf).collect(Collectors.toList());
    List<MtdClassify> mtdClassifyList = mtdClassifyRepository.findAllById(idSet);
    // 去除本地绑定
    List<MtdClassifyAtlas> mtdClassifyAtlasList = synchClassAtlas(mtdClassifyList);
    if (!mtdClassifyAtlasList.isEmpty()) {
      mtdClassifyAtlasRepository.saveAll(mtdClassifyAtlasList);
    }
    mtdClassifyList.forEach(item -> item.setSynchStatus(-1));
    mtdClassifyRepository.saveAll(mtdClassifyList);
  }

  public List<MtdClassifyAtlas> synchClassAtlas(List<MtdClassify> mtdClassifyList) {
    List<MtdClassifyAtlas> classifyAtlasList =
        mtdClassifyAtlasRepository.findAllBySynchStatusNot(-1);
    List<String> nameList =
        mtdClassifyList.stream().map(MtdClassify::getName).collect(Collectors.toList());
    return classifyAtlasList.stream()
        .filter(
            i -> CollectionUtils.containsAny(nameList, Arrays.asList(i.getClassify().split(","))))
        .peek(
            mla -> {
              String classify =
                  Stream.of(mla.getClassify().split(","))
                      .filter(y -> !nameList.contains(y))
                      .collect(Collectors.joining(","));
              mla.setClassify(classify.isEmpty() ? mla.getClassify() : classify);
              mla.setSynchStatus(classify.isEmpty() ? -1 : 0);
            })
        .collect(Collectors.toList());
  }

  @Override
  public PageResultVO<MtdClassifyInfoVO> listByPage(MtdClassifyListVO mtdClassifyListVO) {
    Map<String, Object> map;
    try {
      map = queryMtdClassifyByParams(mtdClassifyListVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<MtdClassify> list = (List<MtdClassify>) map.get("list");
    long total = (long) map.get("total");

    List<MtdClassifyInfoVO> mtdLabelsVOList =
        list.stream()
            .map(MtdClassifyMapper.INSTANCE::useMtdClassifyInfoVO)
            .collect(Collectors.toList());

    return new PageResultVO<>(
        total,
        mtdClassifyListVO.getPagination().getPage(),
        mtdClassifyListVO.getPagination().getSize(),
        mtdLabelsVOList);
  }

  @Override
  public List<MtdClassifyInfoVO> listByAll(MtdClassifyVO mtdClassifyVO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, MtdClassifyMapper.INSTANCE.useMtdClassifyListVO(mtdClassifyVO));
    List<MtdClassify> mtdLabelsList =
        jpaQueryFactory
            .select(qMtdClassify)
            .from(qMtdClassify)
            .where(booleanBuilder)
            .orderBy(qMtdClassify.id.asc())
            .fetch();
    return mtdLabelsList.stream()
        .map(MtdClassifyMapper.INSTANCE::useMtdClassifyInfoVO)
        .collect(Collectors.toList());
  }

  private Map<String, Object> queryMtdClassifyByParams(MtdClassifyListVO mtdClassifyVO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, mtdClassifyVO);
    Map<String, Object> result = new HashMap<>(2);
    long count =
        jpaQueryFactory
            .select(qMtdClassify.count())
            .from(qMtdClassify)
            .where(booleanBuilder)
            .fetchOne();
    List<MtdClassify> mtdLabelsList =
        jpaQueryFactory
            .select(qMtdClassify)
            .from(qMtdClassify)
            .where(booleanBuilder)
            .orderBy(qMtdClassify.id.asc())
            .offset(
                (mtdClassifyVO.getPagination().getPage() - 1)
                    * mtdClassifyVO.getPagination().getSize())
            .limit(mtdClassifyVO.getPagination().getSize())
            .fetch();
    result.put("list", mtdLabelsList);
    result.put("total", count);
    return result;
  }

  public void checkCondition(BooleanBuilder booleanBuilder, MtdClassifyListVO mtdClassifyVO) {
    if (!StringUtils.isEmpty(mtdClassifyVO.getName())) {
      booleanBuilder.and(qMtdClassify.name.contains(mtdClassifyVO.getName()));
    }
    booleanBuilder.and(qMtdClassify.synchStatus.ne(-1));
  }
}
