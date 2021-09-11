package com.qk.dm.datastandards.service.impl;

import static com.qk.dm.datastandards.entity.QDsdCodeInfo.dsdCodeInfo;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdCodeInfo;
import com.qk.dm.datastandards.entity.QDsdBasicinfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.repositories.DsdCodeInfoRepository;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.vo.CodeTableFieldsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoParamsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoVO;
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
  private final DsdCodeInfoRepository dsdCodeInfoRepository;

  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;

  public DataStandardBasicInfoServiceImpl(
      DsdBasicinfoRepository dsdBasicinfoRepository,
      DataStandardDirService dataStandardDirService,
      DsdCodeInfoRepository dsdCodeInfoRepository,
      EntityManager entityManager) {
    this.dsdBasicinfoRepository = dsdBasicinfoRepository;
    this.dataStandardDirService = dataStandardDirService;
    this.dsdCodeInfoRepository = dsdCodeInfoRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public PageResultVO<DsdBasicInfoVO> getDsdBasicInfo(DsdBasicInfoParamsVO basicInfoParamsVO) {
    List<DsdBasicInfoVO> dsdBasicinfoVOList = new ArrayList<DsdBasicInfoVO>();
    Map<String, Object> map = null;
    try {
      map = queryDsdBasicinfoByParams(basicInfoParamsVO);
    } catch (ParseException e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DsdBasicinfo> list = (List<DsdBasicinfo>) map.get("list");
    long total = (long) map.get("total");

    list.forEach(
        dsd -> {
          DsdBasicInfoVO dsdBasicinfoVO = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsd);
          String dsdLevel = dsdBasicinfoVO.getDsdLevel();
          dsdBasicinfoVO.setDsdLevelName(dsdLevel.split("/")[dsdLevel.split("/").length - 1]);
          dsdBasicinfoVOList.add(dsdBasicinfoVO);
        });
    return new PageResultVO<>(
        total,
        basicInfoParamsVO.getPagination().getPage(),
        basicInfoParamsVO.getPagination().getSize(),
        dsdBasicinfoVOList);
  }

  @Override
  public void addDsdBasicinfo(DsdBasicInfoVO dsdBasicinfoVO) {
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
  public void updateDsdBasicinfo(DsdBasicInfoVO dsdBasicinfoVO) {
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
  public List<CodeTableFieldsVO> getCodeFieldByCodeDirId(String codeDirId) {
    final Optional<DsdCodeInfo> dsdCodeInfoOptional =
        dsdCodeInfoRepository.findOne(dsdCodeInfo.codeDirId.eq(codeDirId));
    if (dsdCodeInfoOptional.isPresent()) {
      String tableConfFieldsStr = dsdCodeInfoOptional.get().getTableConfFields();
      List<CodeTableFieldsVO> codeTableFieldsVOList =
          GsonUtil.fromJsonString(
              tableConfFieldsStr, new TypeToken<List<CodeTableFieldsVO>>() {}.getType());
      return codeTableFieldsVOList;
    }
    return new ArrayList<>();
  }

  public Map<String, Object> queryDsdBasicinfoByParams(DsdBasicInfoParamsVO basicInfoParamsVO)
      throws ParseException {
    QDsdBasicinfo qDsdBasicinfo = QDsdBasicinfo.dsdBasicinfo;
    Map<String, Object> result = new HashMap<>();
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, qDsdBasicinfo, basicInfoParamsVO);
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
                (basicInfoParamsVO.getPagination().getPage() - 1)
                    * basicInfoParamsVO.getPagination().getSize())
            .limit(basicInfoParamsVO.getPagination().getSize())
            .fetch();
    result.put("list", dsdBasicInfoList);
    result.put("total", count);
    return result;
  }

  public void checkCondition(
      BooleanBuilder booleanBuilder,
      QDsdBasicinfo qDsdBasicinfo,
      DsdBasicInfoParamsVO basicInfoParamsVO) {
    if (!StringUtils.isEmpty(basicInfoParamsVO.getDsdLevelId())) {
      Set<String> dsdLevelIdSet = new HashSet<>();
      dataStandardDirService.getDsdId(dsdLevelIdSet, basicInfoParamsVO.getDsdLevelId());
      booleanBuilder.and(qDsdBasicinfo.dsdLevelId.in(dsdLevelIdSet));
    }
    if (!StringUtils.isEmpty(basicInfoParamsVO.getDsdName())) {
      booleanBuilder.and(qDsdBasicinfo.dsdName.contains(basicInfoParamsVO.getDsdName()));
    }
    if (!StringUtils.isEmpty(basicInfoParamsVO.getDsdCode())) {
      booleanBuilder.and(qDsdBasicinfo.dsdCode.contains(basicInfoParamsVO.getDsdCode()));
    }
    if (!StringUtils.isEmpty(basicInfoParamsVO.getBeginDay())
        && !StringUtils.isEmpty(basicInfoParamsVO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate(
              "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDsdBasicinfo.gmtModified);
      booleanBuilder.and(
          dateExpr.between(basicInfoParamsVO.getBeginDay(), basicInfoParamsVO.getEndDay()));
    }
  }
}
