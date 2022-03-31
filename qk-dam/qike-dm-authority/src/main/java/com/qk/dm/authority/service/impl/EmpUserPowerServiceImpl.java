package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.*;
import com.qk.dm.authority.mapstruct.QxResourcesMapper;
import com.qk.dm.authority.mapstruct.QxServiceMapper;
import com.qk.dm.authority.repositories.QkQxEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesRepository;
import com.qk.dm.authority.repositories.QkQxServiceRepository;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.service.EmpUserPowerService;
import com.qk.dm.authority.vo.params.UserEmpParamVO;
import com.qk.dm.authority.vo.params.UserEmpPowerParamVO;
import com.qk.dm.authority.vo.powervo.EmpResourceUrlVO;
import com.qk.dm.authority.vo.powervo.ResourceOutVO;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/3/9 10:25
 * @since 1.0.0
 */
@Service
public class EmpUserPowerServiceImpl implements EmpUserPowerService {
  private final AtyUserGroupService atyUserGroupService;
  private final AtyUserRoleService atyUserRoleService;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private final QkQxServiceRepository qkQxServiceRepository;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private final QkQxResourcesRepository qkQxResourcesRepository;
  private final QQxEmpower qQxEmpower = QQxEmpower.qxEmpower;
  private final QQxService qQxService = QQxService.qxService;
  private final QQxResources qQxResources = QQxResources.qxResources;
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower =QQkQxResourcesEmpower.qkQxResourcesEmpower;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;

  public EmpUserPowerServiceImpl(AtyUserGroupService atyUserGroupService,
      AtyUserRoleService atyUserRoleService,
      QkQxEmpowerRepository qkQxEmpowerRepository,
      QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository,
      QkQxResourcesRepository qkQxResourcesRepository,
      EntityManager entityManager,QkQxServiceRepository qkQxServiceRepository) {
    this.atyUserGroupService = atyUserGroupService;
    this.atyUserRoleService = atyUserRoleService;
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
    this.qkQxResourcesEmpowerRepository = qkQxResourcesEmpowerRepository;
    this.qkQxResourcesRepository = qkQxResourcesRepository;
    this.entityManager = entityManager;
    this.qkQxServiceRepository = qkQxServiceRepository;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  /**
   * 查询服务信息
   * @param userEmpParamVO
   * @return
   */
  @Override
  public List<ServiceVO> queryServicesByUserId(UserEmpParamVO userEmpParamVO) {
    List<String> idList = getIdList(userEmpParamVO.getRealm(),userEmpParamVO.getUserId(),userEmpParamVO.getClientId());
    //3根据用户id、角色id、用户分组id返回授权服务信息
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, idList);
    List<QxEmpower> qxEmpowerList = jpaQueryFactory.select(qQxEmpower).from(qQxEmpower).where(booleanBuilder).orderBy(qQxEmpower.gmtCreate.desc()).fetch();
    if (CollectionUtils.isNotEmpty(qxEmpowerList)){
      List<String> serviceidList = qxEmpowerList.stream().map(QxEmpower::getServiceId).collect(Collectors.toList());
      List<QxService> qxServiceList = getServices(serviceidList);
      return  QxServiceMapper.INSTANCE.of(qxServiceList);
    }
    return new ArrayList<ServiceVO>();
  }

  private List<QxService> getServices(List<String> serviceidList) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkServiceCondition(booleanBuilder, serviceidList);
    return jpaQueryFactory.select(qQxService).from(qQxService).where(booleanBuilder).orderBy(qQxService.gmtCreate.desc()).fetch();
  }

  private void checkServiceCondition(BooleanBuilder booleanBuilder, List<String> serviceidList) {
    if (CollectionUtils.isNotEmpty(serviceidList)){
      booleanBuilder.and(qQxService.serviceid.in(serviceidList));
    }
  }

  private void checkCondition(BooleanBuilder booleanBuilder, List<String> idList) {
    if (CollectionUtils.isNotEmpty(idList)) {
      booleanBuilder.and(qQxEmpower.empoerId.in(idList));
    }
  }

  private List<String> getIdList(String realm, String userId ,String clientId) {
    List<String> idList = new ArrayList<>();
    //1根据用户id和域获取用户分组
    List<AtyGroupInfoVO> userGroup = atyUserGroupService.getUserGroup(realm, userId);
    //2根据用户id、域、客户端获取用户角色
    List<AtyClientRoleInfoVO> userClientRole = atyUserRoleService.getUserClientRole(realm, userId,clientId);
    if (CollectionUtils.isNotEmpty(userGroup)){
      List<String> groupIds = userGroup.stream().map(AtyGroupInfoVO::getId).collect(Collectors.toList());
      idList.addAll(groupIds);
    }
   if (CollectionUtils.isNotEmpty(userClientRole)){
     List<String> roleIds = userClientRole.stream().map(AtyClientRoleInfoVO::getId).collect(Collectors.toList());
     idList.addAll(roleIds);
   }
    idList.add(userId);
    return idList;
  }

  /**
   * 查询授权api
   * @param userEmpPowerParamVO
   * @return
   */
  @Override
  public List<String> queryEmpower(UserEmpPowerParamVO userEmpPowerParamVO) {
      List<String> idList = getIdList(userEmpPowerParamVO.getRealm(),userEmpPowerParamVO.getUserId(),userEmpPowerParamVO.getClientId());
     return getEmpowerList(idList,userEmpPowerParamVO.getServiceId());
  }

  /**
   * 查询收取资源
   * @param userEmpPowerParamVO
   * @return
   */
  @Override
  public List<EmpResourceUrlVO> queryEmpowerResource(
      UserEmpPowerParamVO userEmpPowerParamVO) {
    List<String> idList = getIdList(userEmpPowerParamVO.getRealm(),userEmpPowerParamVO.getUserId(),userEmpPowerParamVO.getClientId());
    return getEmpowerResource(idList,userEmpPowerParamVO.getServiceId());
  }

  private List<EmpResourceUrlVO> getEmpowerResource(List<String> idList,String serviceId) {
    List<String> resourcesUuidList = getResourcesUuid(idList,serviceId);
    return getEmpowerResourceVO(resourcesUuidList,serviceId);
  }

  private List<EmpResourceUrlVO> getEmpowerResourceVO(List<String> resourcesUuidList, String serviceId) {
    if (CollectionUtils.isNotEmpty(resourcesUuidList)){
      List<QxResources> qxResourcesList = (List<QxResources>) qkQxResourcesRepository.findAll(qQxResources.resourcesid.in(resourcesUuidList));
      if (CollectionUtils.isNotEmpty(qxResourcesList)){
        return qeryEmpResourceUrlVO(qxResourcesList,serviceId);
      }
    }
    return new ArrayList<EmpResourceUrlVO>();
  }

  private List<EmpResourceUrlVO> qeryEmpResourceUrlVO(
      List<QxResources> qxResourcesList, String serviceId) {
    Optional<QxService> service = qkQxServiceRepository.findOne(qQxService.serviceid.eq(serviceId));
    String serviceName = service.isPresent() ? getServiceName(service) : QxConstant.SERVICE_NAME;
    List<QxResources> resourcesList = qxResourcesList.stream().filter(
        qxResources -> qxResources.getType().equals(QxConstant.RESOURCE_TYPE)).collect(Collectors.toList())
        .stream().distinct().collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(resourcesList)){
      List<ResourceOutVO> resourceOutVOList = QxResourcesMapper.INSTANCE.of(resourcesList);
      List<ResourceOutVO> resourceOutVOLists = buildByResource(resourceOutVOList, serviceName);
     return QxResourcesMapper.INSTANCE.resourceUrlOF(resourceOutVOLists);
    }
    return new ArrayList<EmpResourceUrlVO>();
  }

  private String getServiceName(Optional<QxService> serviceList) {
    return  serviceList.get().getServiceName();
  }

  private List<ResourceOutVO> buildByResource(
      List<ResourceOutVO> resourceOutVOList, String name) {
    List<ResourceOutVO> trees = new ArrayList<>();
    ResourceOutVO resourceOutVO = ResourceOutVO.builder().id(QxConstant.DIRID).name(name).resourcesid(QxConstant.RESOURCEID).build();
    trees.add(findChildren(resourceOutVO, resourceOutVOList));
    return trees;
  }

  private ResourceOutVO findChildren(ResourceOutVO resourceOutVO, List<ResourceOutVO> resourceOutVOList) {
    resourceOutVO.setChildrenList(new ArrayList<>());
    if (CollectionUtils.isNotEmpty(resourceOutVOList)){
      resourceOutVOList.forEach(resourceOutVO1 -> {
        if (resourceOutVO.getId().equals(resourceOutVO1.getPid())){
          resourceOutVO.getChildrenList().add(findChildren(resourceOutVO1,resourceOutVOList));
        }
      });
    }
    return resourceOutVO;
  }

  private List<String> getEmpowerList(List<String> idList, String serviceId) {
   List<String> resourcesUuidList = getResourcesUuid(idList,serviceId);
    return getUserPower(resourcesUuidList);
  }

  /**
   * 根据资源UUID获取资源权限
   * @param resourcesUuidList
   * @return
   */
  private List<String> getUserPower(List<String> resourcesUuidList) {
    if (CollectionUtils.isNotEmpty(resourcesUuidList)){
      List<QxResources> qxResourcesList = (List<QxResources>) qkQxResourcesRepository.findAll(qQxResources.resourcesid.in(resourcesUuidList));
      if (CollectionUtils.isNotEmpty(qxResourcesList)){
        return qxResourcesList.stream().filter(qxResources -> qxResources.getType().equals(QxConstant.API_TYPE)).map(QxResources::getPath).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

      }
    }
    return new ArrayList<String>();
  }

  private List<String> getResourcesUuid(List<String> idList, String serviceId) {
    Predicate predicate = StringUtils.isNotBlank(serviceId) ?
        qQxEmpower.empoerId.in(idList).and(qQxEmpower.serviceId.eq(serviceId)) :
        qQxEmpower.empoerId.in(idList);
    List<QxEmpower> qxempowerList = (List<QxEmpower>) qkQxEmpowerRepository.findAll(predicate);
    return getEmpower(qxempowerList);
  }

  private List<String> getEmpower(List<QxEmpower> qxempowerList) {
    if (CollectionUtils.isNotEmpty(qxempowerList)){
      List<String> empowerIdList = qxempowerList.stream().map(QxEmpower::getEmpowerId).collect(Collectors.toList());
      List<QkQxResourcesEmpower> qkQxResourcesEmpowerList = (List<QkQxResourcesEmpower>) qkQxResourcesEmpowerRepository.findAll(qQkQxResourcesEmpower.empowerUuid.in(empowerIdList));
      if (CollectionUtils.isNotEmpty(qkQxResourcesEmpowerList)){
       return qkQxResourcesEmpowerList.stream().map(QkQxResourcesEmpower::getResourceUuid).collect(Collectors.toList());
      }
    }
    return new ArrayList<String>();
  }
}