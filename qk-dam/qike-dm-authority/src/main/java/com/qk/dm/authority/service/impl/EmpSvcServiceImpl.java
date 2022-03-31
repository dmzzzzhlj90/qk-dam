package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.entity.*;
import com.qk.dm.authority.mapstruct.QxServiceMapper;
import com.qk.dm.authority.repositories.*;
import com.qk.dm.authority.service.EmpSvcService;
import com.qk.dm.authority.vo.params.ServiceParamVO;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**权限管理-服务
 * @author zys
 * @date 2022/2/24 11:23
 * @since 1.0.0
 */
@Service
public class EmpSvcServiceImpl implements EmpSvcService {
  private final QkQxServiceRepository qkQxServiceRepository;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private final QQxService qQxService=QQxService.qxService;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final QkQxResourcesMenuRepository qkQxResourcesMenuRepository;
  private final QkQxResourcesApiRepository qkQxResourcesApiRepository;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower=QQkQxResourcesEmpower.qkQxResourcesEmpower;

  public EmpSvcServiceImpl(QkQxServiceRepository qkQxServiceRepository,
      QkQxEmpowerRepository qkQxEmpowerRepository, EntityManager entityManager,
      QkQxResourcesMenuRepository qkQxResourcesMenuRepository,
      QkQxResourcesApiRepository qkQxResourcesApiRepository,
      QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository) {
    this.qkQxServiceRepository = qkQxServiceRepository;
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
    this.entityManager = entityManager;
    this.qkQxResourcesMenuRepository = qkQxResourcesMenuRepository;
    this.qkQxResourcesApiRepository = qkQxResourcesApiRepository;
    this.qkQxResourcesEmpowerRepository = qkQxResourcesEmpowerRepository;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public void addService(ServiceVO serviceVO) {
    serviceVO.setServiceid(UUID.randomUUID().toString());
    QxService qxService = QxServiceMapper.INSTANCE.qxService(serviceVO);
    BooleanExpression predicate = qQxService.serviceName.eq(qxService.getServiceName()).and(qQxService.redionid.eq(qxService.getRedionid()));
    boolean exists = qkQxServiceRepository.exists(predicate);
    if (exists){
      throw new BizException(
          "当前新增服务名称为:"
              + qxService.getServiceName()
              + " 的数据，已存在！！！");
    }else {
      qkQxServiceRepository.save(qxService);
    }
  }

  @Override
  public ServiceVO ServiceDetails(Long id) {
    QxService qxService = qkQxServiceRepository.findById(id).orElse(null);
    if (Objects.isNull(qxService)){
      throw new BizException(
          "当前查询数据为空"
      );
    }
    ServiceVO serviceVO = QxServiceMapper.INSTANCE.qxServiceVO(qxService);
    return serviceVO;
  }

  @Override
  public void updateService(ServiceVO serviceVO) {
    QxService qeryQxService = qkQxServiceRepository.findById(serviceVO.getId()).orElse(null);
    if (Objects.isNull(qeryQxService)){
      throw new BizException("当前需修改的名称为"+serviceVO.getServiceName()+"的数据不存在");
    }
    QxService qxService = QxServiceMapper.INSTANCE.qxService(serviceVO);
    qkQxServiceRepository.saveAndFlush(qxService);
  }

  @Override
  public void deleteService(Long id) {
    QxService qxService = qkQxServiceRepository.findById(id).orElse(null);
    if (Objects.isNull(qxService)){
      throw new BizException(
          "当前需删除的数据不存在"
      );
    }
    deleteAssociatedData(qxService);
    qkQxServiceRepository.delete(qxService);
  }

  /**
   * 删除服务的同时删除资源和授权信息
   * @param qxService
   */
  private void deleteAssociatedData(QxService qxService) {
    //根据服务的uuid查询资源信息
    List<QkQxResourcesMenu> qxResourcesMenuList = qkQxResourcesMenuRepository.findByServiceId(qxService.getServiceid());
    //根据服务的uuid查询api信息
    List<QkQxResourcesApi> qkQxResourcesApiList = qkQxResourcesApiRepository.findByServiceId(qxService.getServiceid());
    //根据服务的uuid查询授权信息
    List<QxEmpower> qxEmpowerList = qkQxEmpowerRepository.findByServiceId(qxService.getServiceid());
    //查询
    if (CollectionUtils.isNotEmpty(qxResourcesMenuList)){
      qkQxResourcesMenuRepository.deleteAll(qxResourcesMenuList);
    }
    if (CollectionUtils.isNotEmpty(qkQxResourcesApiList)){
      qkQxResourcesApiRepository.deleteAll(qkQxResourcesApiList);
    }
    if (CollectionUtils.isNotEmpty(qxEmpowerList)){
      List<String> empowerIdList = qxEmpowerList.stream().map(QxEmpower::getEmpowerId).collect(Collectors.toList());
      List<QkQxResourcesEmpower> qkQxResourcesEmpowerList = (List<QkQxResourcesEmpower>) qkQxResourcesEmpowerRepository.findAll(qQkQxResourcesEmpower.empowerUuid.in(empowerIdList));
      qkQxResourcesEmpowerRepository.deleteAll(qkQxResourcesEmpowerList);
      qkQxEmpowerRepository.deleteAll(qxEmpowerList);
    }
  }

  @Override
  public List<ServiceVO> queryServices(ServiceParamVO serviceParamVO) {
    Map<String, Object> map;
    try {
      map = queryByParams(serviceParamVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<QxService> list = (List<QxService>) map.get("list");
   return  QxServiceMapper.INSTANCE.of(list);
  }

  private Map<String,Object> queryByParams(ServiceParamVO serviceParamVO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, serviceParamVO);
    Map<String, Object> result = new HashMap<>();
    List<QxService> serviceList = jpaQueryFactory.select(qQxService).from(qQxService)
        .where(booleanBuilder).orderBy(qQxService.gmtCreate.desc()).fetch();
    result.put("list", serviceList);
    return result;
  }

  private void checkCondition(BooleanBuilder booleanBuilder, ServiceParamVO serviceParamVO) {
    if (!StringUtils.isEmpty(serviceParamVO.getServiceName())) {
      booleanBuilder.and(qQxService.serviceName.contains(serviceParamVO.getServiceName()));
    }
    if (!StringUtils.isEmpty(serviceParamVO.getRedionid())) {
      booleanBuilder.and(qQxService.redionid.contains(serviceParamVO.getRedionid()));
    }
  }
}