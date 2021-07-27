package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.QDsdBasicinfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdBasicinfoParamsVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.text.ParseException;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准标准信息接口实现类
 */
@Service
public class DataStandardBasicInfoServiceImpl implements DataStandardBasicInfoService {
  private final DsdBasicinfoRepository dsdBasicinfoRepository;
  private final DataStandardDirService dataStandardDirService;
  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;

  public DataStandardBasicInfoServiceImpl(
      DsdBasicinfoRepository dsdBasicinfoRepository,
      DataStandardDirService dataStandardDirService,
      EntityManager entityManager) {
    this.dsdBasicinfoRepository = dsdBasicinfoRepository;
    this.dataStandardDirService = dataStandardDirService;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public PageResultVO<DsdBasicinfoVO> getDsdBasicInfo(DsdBasicinfoParamsVO dsdBasicinfoParamsVO) {
    List<DsdBasicinfoVO> dsdBasicinfoVOList = new ArrayList<DsdBasicinfoVO>();
    Map<String, Object> map = null;
    try {
      map = queryDsdBasicinfoByParams(dsdBasicinfoParamsVO);
    } catch (ParseException e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DsdBasicinfo> list = (List<DsdBasicinfo>) map.get("list");
    long total = (long) map.get("total");

    list.forEach(
        dsd -> {
          DsdBasicinfoVO dsdBasicinfoVO = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsd);
          String dsdLevel = dsdBasicinfoVO.getDsdLevel();
          dsdBasicinfoVO.setDsdLevelName(dsdLevel.split("/")[dsdLevel.split("/").length - 1]);
          dsdBasicinfoVOList.add(dsdBasicinfoVO);
        });
    return new PageResultVO<>(
        total,
        dsdBasicinfoParamsVO.getPagination().getPage(),
        dsdBasicinfoParamsVO.getPagination().getSize(),
        dsdBasicinfoVOList);
  }

  private DsdBasicinfo setDsdBasicinfoSearchInfo(DsdBasicinfoParamsVO dsdBasicinfoParamsVO) {
    DsdBasicinfo dsdBasicinfo = new DsdBasicinfo();
    if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdLevelId())) {
      dsdBasicinfo.setDsdLevelId(dsdBasicinfoParamsVO.getDsdLevelId());
    }

    if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdName())) {
      dsdBasicinfo.setDsdName(dsdBasicinfoParamsVO.getDsdName());
    }

    if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdCode())) {
      dsdBasicinfo.setDsdCode(dsdBasicinfoParamsVO.getDsdCode());
    }

    return dsdBasicinfo;
  }

  @Override
  public void addDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO) {
    DsdBasicinfo dsdBasicinfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicinfoVO);
    dsdBasicinfo.setGmtCreate(new Date());
    dsdBasicinfo.setGmtModified(new Date());

    Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdCode.eq(dsdBasicinfo.getDsdCode());
    boolean exists = dsdBasicinfoRepository.exists(predicate);
    if (exists) {
      throw new BizException(
          "当前要新增的标准代码ID为："
              + dsdBasicinfo.getDsdCode()
              + "标准名称为:"
              + dsdBasicinfo.getDsdName()
              + " 的数据，已存在！！！");
    }
    dsdBasicinfoRepository.save(dsdBasicinfo);
  }

  @Override
  public void updateDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO) {
    DsdBasicinfo dsdBasicinfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicinfoVO);
    dsdBasicinfo.setGmtModified(new Date());
    Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdCode.eq(dsdBasicinfo.getDsdCode());
    boolean exists = dsdBasicinfoRepository.exists(predicate);
    if (exists) {
      dsdBasicinfoRepository.saveAndFlush(dsdBasicinfo);
    } else {
      throw new BizException(
          "当前要更新的标准代码ID为："
              + dsdBasicinfo.getDsdCode()
              + "标准名称为:"
              + dsdBasicinfo.getDsdName()
              + " 的数据，不存在！！！");
    }
  }

  @Override
  public void deleteDsdBasicinfo(Integer delId) {
    boolean exists = dsdBasicinfoRepository.exists(QDsdBasicinfo.dsdBasicinfo.id.eq(delId));
    if (exists) {
      dsdBasicinfoRepository.deleteById(delId);
    }
  }

  @Transactional
  @Override
  public void bulkDeleteDsdBasicInfo(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Set<Integer> idSet = new HashSet<>();
    idList.forEach(id -> idSet.add(Integer.parseInt(id)));
    List<DsdBasicinfo> basicInfoList = dsdBasicinfoRepository.findAllById(idSet);
    dsdBasicinfoRepository.deleteInBatch(basicInfoList);
  }

  @Override
  public List getDataCapacityByDataType(String dataType) {
    if (DsdConstant.DATA_TYPE_CAPACITY_STRING.equalsIgnoreCase(dataType))
      return DsdConstant.getListString();
    if (DsdConstant.DATA_TYPE_CAPACITY_DOUBLE.equalsIgnoreCase(dataType))
      return DsdConstant.getListDouble();
    if (DsdConstant.DATA_TYPE_CAPACITY_BOOLEAN.equalsIgnoreCase(dataType))
      return DsdConstant.getListBoolean();
    if (DsdConstant.DATA_TYPE_CAPACITY_DECIMAL.equalsIgnoreCase(dataType))
      return DsdConstant.getListDecimal();
    if (DsdConstant.DATA_TYPE_CAPACITY_DATE.equalsIgnoreCase(dataType))
      return DsdConstant.getListDate();
    if (DsdConstant.DATA_TYPE_CAPACITY_TIMESTAMP.equalsIgnoreCase(dataType))
      return DsdConstant.getListTimeStamp();
    return null;
  }

  public Map<String, Object> queryDsdBasicinfoByParams(DsdBasicinfoParamsVO dsdBasicinfoParamsVO)
      throws ParseException {
    QDsdBasicinfo qDsdBasicinfo = QDsdBasicinfo.dsdBasicinfo;
    Map<String, Object> result = new HashMap<>();
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, qDsdBasicinfo, dsdBasicinfoParamsVO);
    long count =
        jpaQueryFactory
            .select(qDsdBasicinfo.count())
            .from(qDsdBasicinfo)
            .where(booleanBuilder)
            .fetchOne();
    List<DsdBasicinfo> dsdBasicInfoList =
        jpaQueryFactory
            .select(qDsdBasicinfo)
            .from(qDsdBasicinfo)
            .where(booleanBuilder)
            .orderBy(qDsdBasicinfo.dsdCode.asc())
            .offset(
                (dsdBasicinfoParamsVO.getPagination().getPage() - 1)
                    * dsdBasicinfoParamsVO.getPagination().getSize())
            .limit(dsdBasicinfoParamsVO.getPagination().getSize())
            .fetch();
    result.put("list", dsdBasicInfoList);
    result.put("total", count);
    return result;
  }

  public void checkCondition(
      BooleanBuilder booleanBuilder,
      QDsdBasicinfo qDsdBasicinfo,
      DsdBasicinfoParamsVO dsdBasicinfoParamsVO) {
    if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdLevelId())) {
      Set<String> dsdLevelIdSet = new HashSet<>();
      dataStandardDirService.getDsdId(dsdLevelIdSet, dsdBasicinfoParamsVO.getDsdLevelId());
      booleanBuilder.and(qDsdBasicinfo.dsdLevelId.in(dsdLevelIdSet));
    }
    if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdName())) {
      booleanBuilder.and(qDsdBasicinfo.dsdName.contains(dsdBasicinfoParamsVO.getDsdName()));
    }
    if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdCode())) {
      booleanBuilder.and(qDsdBasicinfo.dsdCode.contains(dsdBasicinfoParamsVO.getDsdCode()));
    }
    if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getBeginDay())
        && !StringUtils.isEmpty(dsdBasicinfoParamsVO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate(
              "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDsdBasicinfo.gmtModified);
      booleanBuilder.and(
          dateExpr.between(dsdBasicinfoParamsVO.getBeginDay(), dsdBasicinfoParamsVO.getEndDay()));
    }
  }
}
