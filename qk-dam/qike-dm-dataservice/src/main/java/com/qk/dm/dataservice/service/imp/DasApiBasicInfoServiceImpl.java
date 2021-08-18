package com.qk.dm.dataservice.service.imp;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiDatasourceConfigRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
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
public class DasApiBasicInfoServiceImpl implements DasApiBasicInfoService {
  private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;

  private final DasApiDirService dasApiDirService;
  private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
  private final DasApiRegisterRepository dasApiRegisterRepository;
  private final DasApiDatasourceConfigRepository dasApiDatasourceConfigRepository;

  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;

  @Autowired
  public DasApiBasicInfoServiceImpl(
      DasApiDirService dasApiDirService,
      DasApiBasicInfoRepository dasApiBasicinfoRepository,
      DasApiRegisterRepository dasApiRegisterRepository,
      DasApiDatasourceConfigRepository dasApiDatasourceConfigRepository,
      EntityManager entityManager) {
    this.dasApiDirService = dasApiDirService;
    this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
    this.dasApiRegisterRepository = dasApiRegisterRepository;
    this.dasApiDatasourceConfigRepository = dasApiDatasourceConfigRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public PageResultVO<DasApiBasicInfoVO> getDasApiBasicInfo(
      DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
    List<DasApiBasicInfoVO> dasApiBasicInfoVOList = new ArrayList<>();
    Map<String, Object> map = null;
    try {
      map = queryDasApiBasicInfoByParams(dasApiBasicInfoParamsVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DasApiBasicInfo> list = (List<DasApiBasicInfo>) map.get("list");
    long total = (long) map.get("total");

    list.forEach(
        dasApiBasicInfo -> {
          DasApiBasicInfoVO dasApiBasicinfoVO =
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
  public void addDasApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO) {
    DasApiBasicInfo dasApiBasicInfo = transformToEntity(dasApiBasicInfoVO);
    dasApiBasicInfo.setGmtCreate(new Date());
    dasApiBasicInfo.setGmtModified(new Date());

    Predicate predicate = qDasApiBasicInfo.apiId.eq(dasApiBasicInfo.getApiId());
    boolean exists = dasApiBasicinfoRepository.exists(predicate);
    if (exists) {
      throw new BizException("当前要新增的API标准名称为:" + dasApiBasicInfo.getApiName() + " 的数据，已存在！！！");
    }
    dasApiBasicinfoRepository.save(dasApiBasicInfo);
  }

  @Override
  public void updateDasApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO) {
    DasApiBasicInfo dasApiBasicInfo = transformToEntity(dasApiBasicInfoVO);
    dasApiBasicInfo.setGmtModified(new Date());
    Predicate predicate = qDasApiBasicInfo.apiId.eq(dasApiBasicInfo.getApiId());
    boolean exists = dasApiBasicinfoRepository.exists(predicate);
    if (exists) {
      dasApiBasicinfoRepository.saveAndFlush(dasApiBasicInfo);
    } else {
      throw new BizException("当前要新增的API标准名称为:" + dasApiBasicInfo.getApiName() + " 的数据，不存在！！！");
    }
  }

  @Transactional
  @Override
  public void deleteDasApiBasicInfo(Long delId) {
    Optional<DasApiBasicInfo> onDasApiBasicInfo =
        dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.id.eq(delId));
    if (onDasApiBasicInfo.isPresent()) {
      // 根据API类型,对应清除配置信息
      DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();
      String apiType = dasApiBasicInfo.getApiType();
      String apiId = dasApiBasicInfo.getApiId();
      if (DasConstant.REGISTER_API_CODE.equalsIgnoreCase(apiType)) {
        dasApiRegisterRepository.deleteByApiId(apiId);
      }
      if (DasConstant.DM_SOURCE_API_CODE.equalsIgnoreCase(apiType)) {
        dasApiDatasourceConfigRepository.deleteByApiId(apiId);
      }
      // 删除AIP基本信息
      dasApiBasicinfoRepository.deleteById(delId);
    }
  }

  @Transactional
  @Override
  public void bulkDeleteDasApiBasicInfo(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Set<Long> idSet = new HashSet<>();
    idList.forEach(id -> idSet.add(Long.valueOf(id)));
    List<DasApiBasicInfo> apiBasicInfoList = dasApiBasicinfoRepository.findAllById(idSet);
    dasApiBasicinfoRepository.deleteInBatch(apiBasicInfoList);
  }

  @Override
  public List<Map<String, String>> getApiType() {
    return DasConstant.getApiType();
  }

  @Override
  public List<Map<String, String>> getDMSourceType() {
    return DasConstant.getDMSourceType();
  }

  private DasApiBasicInfo transformToEntity(DasApiBasicInfoVO dasApiBasicInfoVO) {
    return DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfo(dasApiBasicInfoVO);
  }

  public Map<String, Object> queryDasApiBasicInfoByParams(
      DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
    Map<String, Object> result = new HashMap<>();
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, qDasApiBasicInfo, dasApiBasicInfoParamsVO);
    long count =
        jpaQueryFactory
            .select(qDasApiBasicInfo.count())
            .from(qDasApiBasicInfo)
            .where(booleanBuilder)
            .fetchOne();
    List<DasApiBasicInfo> dasApiBasicInfoList =
        jpaQueryFactory
            .select(qDasApiBasicInfo)
            .from(qDasApiBasicInfo)
            .where(booleanBuilder)
            .orderBy(qDasApiBasicInfo.apiName.asc())
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
      QDasApiBasicInfo qDasApiBasicinfo,
      DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
    if (!StringUtils.isEmpty(dasApiBasicInfoParamsVO.getApiDirId())) {
      Set<String> apiDirIdSet = new HashSet<>();
      dasApiDirService.getApiDirId(apiDirIdSet, dasApiBasicInfoParamsVO.getApiDirId());
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
