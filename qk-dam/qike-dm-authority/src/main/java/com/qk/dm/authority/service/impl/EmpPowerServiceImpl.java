package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.entity.*;
import com.qk.dm.authority.mapstruct.QxEmpowerMapper;
import com.qk.dm.authority.repositories.QkQxEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxServiceRepository;
import com.qk.dm.authority.service.EmpPowerService;
import com.qk.dm.authority.vo.params.EmpowerParamVO;
import com.qk.dm.authority.vo.params.EmpowerQueryVO;
import com.qk.dm.authority.vo.powervo.EmpowerAllVO;
import com.qk.dm.authority.vo.powervo.EmpowerVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/2/24 11:24
 * @since 1.0.0
 */
@Service
public class EmpPowerServiceImpl implements EmpPowerService {
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower=QQkQxResourcesEmpower.qkQxResourcesEmpower;
  private final QQxEmpower qQxEmpower = QQxEmpower.qxEmpower;
  private final QQxService qQxService = QQxService.qxService;
  private final QkQxServiceRepository qkQxServiceRepository;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;

  public EmpPowerServiceImpl(QkQxServiceRepository qkQxServiceRepository,
      QkQxEmpowerRepository qkQxEmpowerRepository,
      QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository,
      EntityManager entityManager) {
    this.qkQxServiceRepository = qkQxServiceRepository;
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
    this.qkQxResourcesEmpowerRepository = qkQxResourcesEmpowerRepository;
    this.entityManager = entityManager;
  }
  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addEmpower(EmpowerVO empowerVO) {
    empowerVO.setEmpowerId(UUID.randomUUID().toString());
    QxEmpower qxEmpower = QxEmpowerMapper.INSTANCE.qxEmpower(empowerVO);
    //?????????????????????id?????????id?????????????????????????????????
    BooleanExpression predicate = qQxEmpower.empoerId.eq(qxEmpower.getEmpoerId()).and(qQxEmpower.serviceId.eq(qxEmpower.getServiceId())).and(qQxEmpower.powerType.eq(qxEmpower.getPowerType()));
    boolean exists = qkQxEmpowerRepository.exists(predicate);
    if (exists){
      throw new BizException(
          empowerVO.getEmpoerName()+"??????????????????????????????");
    }else {
      //todo ??????keycloak?????????????????????,???????????????????????????????????????????????????????????????????????????
      //keyClocakEmpowerApi.addPower(empowerVO);
      QxEmpower qxEmpower1 = qkQxEmpowerRepository.save(qxEmpower);
      addResourceEmpower(qxEmpower1.getEmpowerId(),empowerVO.getResourceSigns());
    }
  }

  /**
   * ???????????????????????????
   * @param emoperId
   * @param list
   */
  private void addResourceEmpower(String emoperId, List<String> list) {
    List<QkQxResourcesEmpower> qkQxResourcesEmpowerList = new ArrayList<>();
    list.forEach(s -> {
      QkQxResourcesEmpower qkQxResourcesEmpower = new QkQxResourcesEmpower();
      qkQxResourcesEmpower.setResourceUuid(s);
      qkQxResourcesEmpower.setEmpowerUuid(emoperId);
      qkQxResourcesEmpowerList.add(qkQxResourcesEmpower);
    });
    qkQxResourcesEmpowerRepository.saveAll(qkQxResourcesEmpowerList);
  }

  @Override
  public EmpowerVO EmpowerDetails(Long id) {
    QxEmpower qxEmpower = qkQxEmpowerRepository.findById(id).orElse(null);
    if (Objects.isNull(qxEmpower)){
      throw new BizException(
          "????????????????????????"
      );
    }
    EmpowerVO empowerVO = QxEmpowerMapper.INSTANCE.qxEmpowerVO(qxEmpower);
    empowerVO.setResourceSigns(queryResources(qxEmpower.getEmpowerId()));
    return empowerVO;
  }

  /**
   * ???????????????????????????-????????????????????????id??????
   * @param id
   * @return
   */
  private List<String> queryResources(String id) {
    List<QkQxResourcesEmpower> byEmpowerId = qkQxResourcesEmpowerRepository.findByEmpowerUuid(id);
    return  byEmpowerId.stream().map(QkQxResourcesEmpower::getResourceUuid).collect(Collectors.toList());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateEmpower(EmpowerVO empowerVO) {
    QxEmpower qxEmpower = qkQxEmpowerRepository.findById(empowerVO.getId())
        .orElse(null);
    if (Objects.isNull(qxEmpower)){
      throw new BizException("????????????????????????????????????");
    }
    //TODO ?????????????????????????????????
    QxEmpowerMapper.INSTANCE.from(empowerVO,qxEmpower);
    //QxEmpower qxEmpower1 = QxEmpowerMapper.INSTANCE.qxEmpower(empowerVO);
    //keyClocakEmpowerApi.addPower(empowerVO);
    qkQxEmpowerRepository.saveAndFlush(qxEmpower);
    updateResourceEmpower(qxEmpower.getEmpowerId(),empowerVO.getResourceSigns());
  }

  /**
   * ????????????-???????????????
   * @param id
   * @param resourceSign
   */
  private void updateResourceEmpower(String id, List<String> resourceSign) {
    BooleanExpression expression = qQkQxResourcesEmpower.empowerUuid.eq(id);
    boolean exists = qkQxResourcesEmpowerRepository.exists(expression);
    if (exists){
      qkQxResourcesEmpowerRepository.deleteAllByEmpowerUuid(id);
    }
    addResourceEmpower(id,resourceSign);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteEmpower(Long id) {
    QxEmpower qxEmpower = qkQxEmpowerRepository.findById(id).orElse(null);
    if (Objects.isNull(qxEmpower)){
      throw new BizException(
          "?????????????????????????????????"
      );
    }
    //TODO ??????????????????????????????
    //EmpowerVO empowerVO = QxEmpowerMapper.INSTANCE.qxEmpowerVO(qxEmpower);
    //keyClocakEmpowerApi.deletePower(empowerVO);
    deleteResourceEmpoer(qxEmpower.getEmpowerId());
    qkQxEmpowerRepository.delete(qxEmpower);
  }

  /**
   * ????????????id????????????-?????????????????????
   * @param id
   */
  void deleteResourceEmpoer(String id) {
    BooleanExpression expression = qQkQxResourcesEmpower.empowerUuid.eq(id);
    boolean exists = qkQxResourcesEmpowerRepository.exists(expression);
    if (exists){
      qkQxResourcesEmpowerRepository.deleteAllByEmpowerUuid(id);
    }
  }

  @Override
  public PageResultVO<EmpowerVO> queryEmpower(EmpowerParamVO empowerParamVO) {
    Map<String, Object> map;
    List<EmpowerVO> modelPhysicalVOList = new ArrayList<>();
    try {
      map = queryByParams(empowerParamVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("????????????!!!");
    }
    if (MapUtils.isNotEmpty(map)){
      List<QxEmpower> list = (List<QxEmpower>) map.get("list");
      if (CollectionUtils.isNotEmpty(list)){
        modelPhysicalVOList= QxEmpowerMapper.INSTANCE.of(list);
      }
    }
    return new PageResultVO<>(
        (long) map.get("total"),
        empowerParamVO.getPagination().getPage(),
        empowerParamVO.getPagination().getSize(),
        modelPhysicalVOList);
  }

  /**
   *
   * @param empowerQueryVO
   * @return
   */
  @Override
  public PageResultVO<EmpowerAllVO> queryAllEmpower(EmpowerQueryVO empowerQueryVO) {
    Map<String, Object> map;
    List<EmpowerAllVO> empowerVO = new ArrayList<>();
    try {
      map = queryByEmpQueryVO(empowerQueryVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("????????????!!!");
    }
    if (MapUtils.isNotEmpty(map)){
      List<QxEmpower> list = (List<QxEmpower>) map.get("list");
      if (CollectionUtils.isNotEmpty(list)){
         empowerVO = getEmpowerVO(list);
      }
    }
    return new PageResultVO<>(
        (long) map.get("total"),
        empowerQueryVO.getPagination().getPage(),
        empowerQueryVO.getPagination().getSize(),
        empowerVO);
  }

  private Map<String,Object> queryByEmpQueryVO(EmpowerQueryVO empowerQueryVO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkQueryCondition(booleanBuilder, empowerQueryVO);
    Map<String, Object> result = new HashMap<>();
    long count =jpaQueryFactory.select(qQxEmpower.count()).from(qQxEmpower).where(booleanBuilder).fetchOne();
    List<QxEmpower> empowerList =
        jpaQueryFactory
            .select(qQxEmpower)
            .from(qQxEmpower)
            .where(booleanBuilder)
            .orderBy(qQxEmpower.gmtModified.asc())
            .offset(
                (long) (empowerQueryVO.getPagination().getPage() - 1)
                    * empowerQueryVO.getPagination().getSize())
            .limit(empowerQueryVO.getPagination().getSize())
            .fetch();
    result.put("list", empowerList);
    result.put("total", count);
    return result;
  }

  private void checkQueryCondition(BooleanBuilder booleanBuilder, EmpowerQueryVO empowerQueryVO) {
    if (!Objects.isNull(empowerQueryVO.getId())) {
      booleanBuilder.and(qQxEmpower.empoerId.eq(empowerQueryVO.getId()));
    }
  }

  /**
   * ??????????????????????????????????????????id???????????????????????????????????????
   * @param empid
   */
  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void deleteEmpPower(String empid) {
    List<QxEmpower> empList = (List<QxEmpower>) qkQxEmpowerRepository.findAll(qQxEmpower.empoerId.eq(empid));
    if (CollectionUtils.isNotEmpty(empList)){
      dealEmpMessage(empList);
      qkQxEmpowerRepository.deleteAll(empList);
    }
  }

  private void dealEmpMessage(List<QxEmpower> empList) {
    List<String> empoeridList = empList.stream().map(QxEmpower::getEmpowerId).collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(empoeridList)){
      jpaQueryFactory.delete(qQkQxResourcesEmpower).where(qQkQxResourcesEmpower.empowerUuid.in(empoeridList)).execute();
    }
  }

  private List<EmpowerAllVO> getEmpowerVO(List<QxEmpower> qxEmpoerList) {
    if (CollectionUtils.isNotEmpty(qxEmpoerList)){
      List<String> serviceIdList = qxEmpoerList.stream().map(QxEmpower::getServiceId).collect(Collectors.toList());
      List<QxService> serviceList = (List<QxService>) qkQxServiceRepository.findAll(qQxService.serviceId.in(serviceIdList));
      return queryEmpowers(serviceList,qxEmpoerList);
    }
    return new ArrayList<EmpowerAllVO>();
  }

  private List<EmpowerAllVO> queryEmpowers(List<QxService> serviceList, List<QxEmpower> qxEmpoerList) {
    if (CollectionUtils.isNotEmpty(serviceList)){
      Map<String,String> map = serviceList.stream().collect(Collectors.toMap(QxService::getServiceId, QxService::getServiceName, (k1, k2) -> k2));
       List<EmpowerAllVO> list = QxEmpowerMapper.INSTANCE.ofEmpowerAllVO(qxEmpoerList);
       list.forEach(empowerAllVO -> {
         empowerAllVO.setServiceName(map.get(empowerAllVO.getServiceId()));
       });
       return list;
    }
    return new ArrayList<EmpowerAllVO>();
  }

  private Map<String,Object> queryByParams(EmpowerParamVO empowerParamVO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, empowerParamVO);
    Map<String, Object> result = new HashMap<>();
    long count =jpaQueryFactory.select(qQxEmpower.count()).from(qQxEmpower).where(booleanBuilder).fetchOne();
    List<QxEmpower> empowerList =
        jpaQueryFactory
            .select(qQxEmpower)
            .from(qQxEmpower)
            .where(booleanBuilder)
            .orderBy(qQxEmpower.gmtCreate.asc())
            .offset(
                (long) (empowerParamVO.getPagination().getPage() - 1)
                    * empowerParamVO.getPagination().getSize())
            .limit(empowerParamVO.getPagination().getSize())
            .fetch();
    result.put("list", empowerList);
    result.put("total", count);
    return result;
  }

  private void checkCondition(BooleanBuilder booleanBuilder, EmpowerParamVO empowerParamVO) {
    if (!Objects.isNull(empowerParamVO.getEmpoerName())) {
      booleanBuilder.and(qQxEmpower.empoerName.eq(empowerParamVO.getEmpoerName()));
    }
    if(!Objects.isNull(empowerParamVO.getServiceId())){
      booleanBuilder.and(qQxEmpower.serviceId.eq(empowerParamVO.getServiceId()));
    }
  }

}