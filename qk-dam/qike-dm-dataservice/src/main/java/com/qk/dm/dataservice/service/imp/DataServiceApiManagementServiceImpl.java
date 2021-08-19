package com.qk.dm.dataservice.service.imp;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.entity.DasApplicationManagement;
import com.qk.dm.dataservice.entity.QDasApplicationManagement;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiManagementMapper;
import com.qk.dm.dataservice.repositories.DasApplicationManagementRepository;
import com.qk.dm.dataservice.service.DataServiceApiManagementService;
import com.qk.dm.dataservice.vo.DasApplicationManagementParamsVO;
import com.qk.dm.dataservice.vo.DasApplicationManagementVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * @author zys
 * @date 2021/8/17 14:54
 * @since 1.0.0
 */
@Service
public class DataServiceApiManagementServiceImpl implements DataServiceApiManagementService {
  private static final QDasApplicationManagement qDasApplicationManagement= QDasApplicationManagement.dasApplicationManagement;
  private final DasApplicationManagementRepository dasApplicationManagementRepository;
  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;
  @Autowired
  public DataServiceApiManagementServiceImpl(DasApplicationManagementRepository dasApplicationManagementRepository, EntityManager entityManager) {
    this.dasApplicationManagementRepository = dasApplicationManagementRepository;
    this.entityManager = entityManager;
  }
  
  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public void addDasManagement(DasApplicationManagementVO dasApplicationManagementVO) {
    DasApplicationManagement dasApplicationManagement = DasApiManagementMapper.INSTANCE.useDasApiManagement(dasApplicationManagementVO);
    dasApplicationManagement.setGmtCreate(new Date());
    dasApplicationManagement.setGmtModified(new Date());
    BooleanExpression predicate = qDasApplicationManagement.appId.eq(dasApplicationManagement.getAppId());
    boolean exists = dasApplicationManagementRepository.exists(predicate);
    if (exists) {
      throw new BizException("当前要新增的应用名称为:" + dasApplicationManagement.getAppName() + " 的数据，已存在！！！");
    } else {
      dasApplicationManagementRepository.save(dasApplicationManagement);
    }
  }

  @Override
  public void updateDasManagement(DasApplicationManagementVO dasApplicationManagementVO) {
    DasApplicationManagement dasApplicationManagement = DasApiManagementMapper.INSTANCE.useDasApiManagement(dasApplicationManagementVO);
    dasApplicationManagement.setGmtModified(new Date());
    BooleanExpression predicate = qDasApplicationManagement.appId.eq(dasApplicationManagement.getAppId());
    boolean exists = dasApplicationManagementRepository.exists(predicate);
    if (exists){
      dasApplicationManagementRepository.saveAndFlush(dasApplicationManagement);
    }else {
      throw new BizException("当前要新增的API标准名称为:" + dasApplicationManagement.getAppName() + " 的数据，不存在！！！");
    }
  }

  @Override
  public void deleteDasApiManagement(Long id) {
    boolean exists = dasApplicationManagementRepository.exists(qDasApplicationManagement.id.eq(id));
    if (exists) {
      dasApplicationManagementRepository.deleteById(id);
    }else {
      throw new BizException("当前要删除id为"+id+"的数据不存在");
    }
  }

  @Override
  public void bulkDeleteDasApiManagement(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Set<Long> idSet = new HashSet<>();
    idList.forEach(id -> idSet.add(Long.valueOf(id)));
    List<DasApplicationManagement> apiBasManagementList = dasApplicationManagementRepository.findAllById(idSet);
    dasApplicationManagementRepository.deleteInBatch(apiBasManagementList);
  }

  @Override
  public PageResultVO<DasApplicationManagementVO> getDasApiDasAiManagement(DasApplicationManagementParamsVO dasApplicationManagementParamsVO) {
    List<DasApplicationManagementVO> dasApplicationManagementList = new ArrayList<>();
    Map<String, Object> map = null;
    try {
      map = queryDasManagementParams(dasApplicationManagementParamsVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DasApplicationManagement> list = (List<DasApplicationManagement>) map.get("list");
    long total = (long) map.get("total");
    list.forEach(
            dsaApplicationManagement -> {
              DasApplicationManagementVO dasApplicationManagementVO = DasApiManagementMapper.INSTANCE.useDasApiManagementVO(dsaApplicationManagement);
              dasApplicationManagementList.add(dasApplicationManagementVO);
            });
    return new PageResultVO<>(
            total,
            dasApplicationManagementParamsVO.getPagination().getPage(),
            dasApplicationManagementParamsVO.getPagination().getSize(),
            dasApplicationManagementList);

  }

  @Override
  public void manageMentAuthorization(DasApplicationManagementParamsVO dasApplicationManagementParamsVO) {
    Long id = dasApplicationManagementParamsVO.getId();
    Predicate predicate = qDasApplicationManagement.id.eq(id);
    DasApplicationManagement dasApplicationManagement = dasApplicationManagementRepository.findOne(predicate).orElse(null);
    if (dasApplicationManagement != null){
      dasApplicationManagement.setApiId(dasApplicationManagementParamsVO.getApiIds());
      dasApplicationManagementRepository.saveAndFlush(dasApplicationManagement);
    }else {
      throw new BizException("id为"+id+"的数据不存在");
    }


  }

  private Map<String, Object> queryDasManagementParams(DasApplicationManagementParamsVO dasApplicationManagementParamsVO) {

    Map<String, Object> result = new HashMap<>();
    BooleanBuilder booleanBuilder = new BooleanBuilder();


    checkCondition(booleanBuilder, qDasApplicationManagement, dasApplicationManagementParamsVO);
    long count =
            jpaQueryFactory
                    .select(qDasApplicationManagement.count())
                    .from(qDasApplicationManagement)
                    .where(booleanBuilder)
                    .fetchOne();

    List<DasApplicationManagement> dasApplicationManagementList = jpaQueryFactory
            .select(qDasApplicationManagement)
            .from(qDasApplicationManagement)
            .where(booleanBuilder)
            .orderBy(qDasApplicationManagement.appId.asc())
            .offset(
                    (dasApplicationManagementParamsVO.getPagination().getPage() - 1)
                            * dasApplicationManagementParamsVO.getPagination().getSize())
            .limit(dasApplicationManagementParamsVO.getPagination().getSize())
            .fetch();
    result.put("list", dasApplicationManagementList);
    result.put("total", count);
    return result;
  }

  private void checkCondition(BooleanBuilder booleanBuilder, QDasApplicationManagement qDasApplicationManagement, DasApplicationManagementParamsVO dasApplicationManagementParamsVO) {

    if (!StringUtils.isEmpty(dasApplicationManagementParamsVO.getAppName())) {
      booleanBuilder.and(qDasApplicationManagement.appName.contains(dasApplicationManagementParamsVO.getAppName()));
    }
    if (!StringUtils.isEmpty(dasApplicationManagementParamsVO.getAppType())){
      booleanBuilder.and(qDasApplicationManagement.appType.contains(dasApplicationManagementParamsVO.getAppType()));
    }
    if (!StringUtils.isEmpty(dasApplicationManagementParamsVO.getBeginDay())
            && !StringUtils.isEmpty(dasApplicationManagementParamsVO.getEndDay())) {
      StringTemplate dateExpr =
              Expressions.stringTemplate(
                      "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDasApplicationManagement.gmtModified);
      booleanBuilder.and(
              dateExpr.between(
                      dasApplicationManagementParamsVO.getBeginDay(), dasApplicationManagementParamsVO.getEndDay()));
    }
  }
}
