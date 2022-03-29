package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.entity.*;
import com.qk.dm.authority.mapstruct.QxEmpowerMapper;
import com.qk.dm.authority.mapstruct.QxServiceMapper;
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
  private final QkQxServiceRepository qkQxServiceRepository;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower=QQkQxResourcesEmpower.qkQxResourcesEmpower;
  private final QQxEmpower qQxEmpower = QQxEmpower.qxEmpower;
  private final QQxService qQxService = QQxService.qxService;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  //@Autowired
  //private keyClocakEmpowerApi keyClocakEmpowerApi;


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
  public void addEmpower(EmpowerVO empowerVO) {
    empowerVO.setEmpowerId(UUID.randomUUID().toString());
    QxEmpower qxEmpower = QxEmpowerMapper.INSTANCE.qxEmpower(empowerVO);
    //根据被授权主题id和授权id字符串判断是否重复授权
    BooleanExpression predicate = qQxEmpower.empoerId.eq(qxEmpower.getEmpoerId()).and(qQxEmpower.serviceId.eq(qxEmpower.getServiceId())).and(qQxEmpower.powerType.eq(qxEmpower.getPowerType()));
    boolean exists = qkQxEmpowerRepository.exists(predicate);
    if (exists){
      throw new BizException(
          empowerVO.getEmpoerName()+"已存在当前授权！！！");
    }else {
      //todo 修改keycloak被授权主体属性,根据授权主体的不同选择不同的方法修改授权主体的属性
      //keyClocakEmpowerApi.addPower(empowerVO);
      QxEmpower qxEmpower1 = qkQxEmpowerRepository.save(qxEmpower);
      addResourceEmpower(qxEmpower1.getEmpowerId(),empowerVO.getResourceSigns());
    }
  }

  /**
   * 新增资源和权限关系
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
          "当前查询数据为空"
      );
    }
    EmpowerVO empowerVO = QxEmpowerMapper.INSTANCE.qxEmpowerVO(qxEmpower);
    empowerVO.setResourceSigns(queryResources(qxEmpower.getEmpowerId()));
    return empowerVO;
  }

  /**
   * 根据授权新查询授权-资源关系表的资源id集合
   * @param id
   * @return
   */
  private List<String> queryResources(String id) {
    List<QkQxResourcesEmpower> byEmpowerId = qkQxResourcesEmpowerRepository.findByEmpowerUuid(id);
    return  byEmpowerId.stream().map(QkQxResourcesEmpower::getResourceUuid).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateEmpower(EmpowerVO empowerVO) {
    QxEmpower qxEmpower = qkQxEmpowerRepository.findById(empowerVO.getId())
        .orElse(null);
    if (Objects.isNull(qxEmpower)){
      throw new BizException("当前需修改的的数据不存在");
    }
    //TODO 修改对应授权主体的属性
    QxEmpower qxEmpower1 = QxEmpowerMapper.INSTANCE.qxEmpower(empowerVO);
    //keyClocakEmpowerApi.addPower(empowerVO);
    qkQxEmpowerRepository.saveAndFlush(qxEmpower1);
    updateResourceEmpower(qxEmpower1.getEmpowerId(),empowerVO.getResourceSigns());
  }

  /**
   * 修改权限-授权关系表
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
  @Transactional
  public void deleteEmpower(Long id) {
    QxEmpower qxEmpower = qkQxEmpowerRepository.findById(id).orElse(null);
    if (Objects.isNull(qxEmpower)){
      throw new BizException(
          "当前需删除的数据不存在"
      );
    }
    //TODO 删除授权主体中的属性
    //EmpowerVO empowerVO = QxEmpowerMapper.INSTANCE.qxEmpowerVO(qxEmpower);
    //keyClocakEmpowerApi.deletePower(empowerVO);
    deleteResourceEmpoer(qxEmpower.getEmpowerId());
    qkQxEmpowerRepository.delete(qxEmpower);
  }

  /**
   * 根据授权id删除资源-授权资源表数据
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
      throw new BizException("查询失败!!!");
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
      throw new BizException("查询失败!!!");
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
            .orderBy(qQxEmpower.gmtCreate.desc())
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
   * 根据删除的用户、角色、分组的id删除对应的授权信息和关系表
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
      List<QxService> serviceList = (List<QxService>) qkQxServiceRepository.findAll(qQxService.serviceid.in(serviceIdList));
      return queryEmpowers(serviceList,qxEmpoerList);
    }
    return new ArrayList<EmpowerAllVO>();
  }

  private List<EmpowerAllVO> queryEmpowers(List<QxService> serviceList, List<QxEmpower> qxEmpoerList) {
    if (CollectionUtils.isNotEmpty(serviceList)){
      Map<String,String> map = serviceList.stream().collect(Collectors.toMap(QxService::getServiceid, QxService::getServiceName, (k1, k2) -> k2));
       List<EmpowerAllVO> list = QxServiceMapper.INSTANCE.ofEmpowerAllVO(qxEmpoerList);
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
            .orderBy(qQxEmpower.gmtCreate.desc())
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