package com.qk.dm.dataservice.service.imp;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.entity.DasApiBasicinfo;
import com.qk.dm.dataservice.entity.QDasApiBasicinfo;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicinfoRepository;
import com.qk.dm.dataservice.service.DataServiceApiBasicInfoService;
import com.qk.dm.dataservice.service.DataServiceApiDirService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicinfoVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public class DataServiceApiBasicInfoServiceImpl implements DataServiceApiBasicInfoService {
  private static final QDasApiBasicinfo qDasApiBasicinfo = QDasApiBasicinfo.dasApiBasicinfo;

  private final DataServiceApiDirService dataServiceApiDirService;
  private final DasApiBasicinfoRepository dasApiBasicinfoRepository;
  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;

  @Autowired
  public DataServiceApiBasicInfoServiceImpl(
      DataServiceApiDirService dataServiceApiDirService,
      DasApiBasicinfoRepository dasApiBasicinfoRepository,
      EntityManager entityManager) {
    this.dataServiceApiDirService = dataServiceApiDirService;
    this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public PageResultVO<DasApiBasicinfoVO> getDasApiBasicInfo(
      DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
    List<DasApiBasicinfoVO> dasApiBasicInfoVOList = new ArrayList<>();
    Map<String, Object> map = null;
    try {
      map = queryDasApiBasicInfoByParams(dasApiBasicInfoParamsVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DasApiBasicinfo> list = (List<DasApiBasicinfo>) map.get("list");
    long total = (long) map.get("total");

    list.forEach(
        dasApiBasicInfo -> {
          DasApiBasicinfoVO dasApiBasicinfoVO =
              DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(dasApiBasicInfo);
          dasApiBasicInfoVOList.add(dasApiBasicinfoVO);
        });
    return new PageResultVO<>(
        total,
        dasApiBasicInfoParamsVO.getPagination().getPage(),
        dasApiBasicInfoParamsVO.getPagination().getSize(),
        dasApiBasicInfoVOList);
  }

  @Override
  public void addDasApiBasicInfo(DasApiBasicinfoVO dasApiBasicInfoVO) {
    DasApiBasicinfo dasApiBasicinfo =
        DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfo(dasApiBasicInfoVO);
    dasApiBasicinfo.setGmtCreate(new Date());
    dasApiBasicinfo.setGmtModified(new Date());

    Predicate predicate = qDasApiBasicinfo.apiName.eq(dasApiBasicinfo.getApiName());
    boolean exists = dasApiBasicinfoRepository.exists(predicate);
    if (exists) {
      throw new BizException("当前要新增的API标准名称为:" + dasApiBasicinfo.getApiName() + " 的数据，已存在！！！");
    }
    dasApiBasicinfoRepository.save(dasApiBasicinfo);
  }

  @Override
  public void updateDasApiBasicInfo(DasApiBasicinfoVO dasApiBasicInfoVO) {
    DasApiBasicinfo dasApiBasicinfo =
        DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfo(dasApiBasicInfoVO);
    dasApiBasicinfo.setGmtModified(new Date());
    Predicate predicate = qDasApiBasicinfo.apiName.eq(dasApiBasicinfo.getApiName());
    boolean exists = dasApiBasicinfoRepository.exists(predicate);
    if (exists) {
      dasApiBasicinfoRepository.saveAndFlush(dasApiBasicinfo);
    } else {
      throw new BizException("当前要新增的API标准名称为:" + dasApiBasicinfo.getApiName() + " 的数据，不存在！！！");
    }
  }

  @Override
  public void deleteDasApiBasicInfo(Long delId) {
    boolean exists = dasApiBasicinfoRepository.exists(qDasApiBasicinfo.id.eq(delId));
    if (exists) {
      dasApiBasicinfoRepository.deleteById(delId);
    }
  }

  @Transactional
  @Override
  public void bulkDeleteDasApiBasicInfo(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Set<Long> idSet = new HashSet<>();
    idList.forEach(id -> idSet.add(Long.valueOf(id)));
    List<DasApiBasicinfo> apiBasicInfoList = dasApiBasicinfoRepository.findAllById(idSet);
    dasApiBasicinfoRepository.deleteInBatch(apiBasicInfoList);
  }

  public Map<String, Object> queryDasApiBasicInfoByParams(
      DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
    Map<String, Object> result = new HashMap<>();
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, qDasApiBasicinfo, dasApiBasicInfoParamsVO);
    long count =
        jpaQueryFactory
            .select(qDasApiBasicinfo.count())
            .from(qDasApiBasicinfo)
            .where(booleanBuilder)
            .fetchOne();
    List<DasApiBasicinfo> dasApiBasicInfoList =
        jpaQueryFactory
            .select(qDasApiBasicinfo)
            .from(qDasApiBasicinfo)
            .where(booleanBuilder)
            .orderBy(qDasApiBasicinfo.apiName.asc())
            .offset(
                (dasApiBasicInfoParamsVO.getPagination().getPage() - 1)
                    * dasApiBasicInfoParamsVO.getPagination().getSize())
            .limit(dasApiBasicInfoParamsVO.getPagination().getSize())
            .fetch();
    result.put("list", dasApiBasicInfoList);
    result.put("total", count);
    return result;
  }

  public void checkCondition(
      BooleanBuilder booleanBuilder,
      QDasApiBasicinfo qDasApiBasicinfo,
      DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
    if (!StringUtils.isEmpty(dasApiBasicInfoParamsVO.getApiDirId())) {
      Set<String> apiDirIdSet = new HashSet<>();
      dataServiceApiDirService.getApiDirId(apiDirIdSet, dasApiBasicInfoParamsVO.getApiDirId());
      booleanBuilder.and(qDasApiBasicinfo.apiId.in(apiDirIdSet));
    }
    if (!StringUtils.isEmpty(dasApiBasicInfoParamsVO.getApiName())) {
      booleanBuilder.and(qDasApiBasicinfo.apiName.contains(dasApiBasicInfoParamsVO.getApiName()));
    }
    if (!StringUtils.isEmpty(dasApiBasicInfoParamsVO.getBeginDay())
        && !StringUtils.isEmpty(dasApiBasicInfoParamsVO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate(
              "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDasApiBasicinfo.gmtModified);
      booleanBuilder.and(
          dateExpr.between(
              dasApiBasicInfoParamsVO.getBeginDay(), dasApiBasicInfoParamsVO.getEndDay()));
    }
  }
}
