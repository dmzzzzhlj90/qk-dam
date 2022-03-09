package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dm.authority.entity.*;
import com.qk.dm.authority.mapstruct.QxServiceMapper;
import com.qk.dm.authority.repositories.QkQxEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesRepository;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.service.UserEmpowerService;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/3/9 10:25
 * @since 1.0.0
 */
@Service
public class UserEmpowerServiceImpl implements UserEmpowerService {
  private final AtyUserGroupService atyUserGroupService;
  private final AtyUserRoleService atyUserRoleService;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private final QkQxResourcesRepository qkQxResourcesRepository;
  private final QQxEmpower qQxEmpower = QQxEmpower.qxEmpower;
  private final QQxService qQxService = QQxService.qxService;
  private final QQxResources qQxResources = QQxResources.qxResources;
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower =QQkQxResourcesEmpower.qkQxResourcesEmpower;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;

  public UserEmpowerServiceImpl(AtyUserGroupService atyUserGroupService,
      AtyUserRoleService atyUserRoleService,
      QkQxEmpowerRepository qkQxEmpowerRepository,
      QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository,
      QkQxResourcesRepository qkQxResourcesRepository, EntityManager entityManager) {
    this.atyUserGroupService = atyUserGroupService;
    this.atyUserRoleService = atyUserRoleService;
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
    this.qkQxResourcesEmpowerRepository = qkQxResourcesEmpowerRepository;
    this.qkQxResourcesRepository = qkQxResourcesRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }
  /**
   * 查询服务信息
   * @param realm
   * @param userId
   * @return
   */
  @Override
  public List<ServiceVO> queryServicesByUserId(String realm, String userId) {
    List<String> idList = getIdList(realm,userId);
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

  private List<String> getIdList(String realm, String userId) {
    List<String> idList = new ArrayList<>();
    //1根据用户id和域获取用户分组
    List<AtyGroupInfoVO> userGroup = atyUserGroupService.getUserGroup(realm, userId);
    //2根据用户id、域、客户端获取用户角色
    List<AtyClientRoleInfoVO> userClientRole = atyUserRoleService.getUserClientRole(realm, userId);
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
   * 查询授权信息
   *
   * @param realm
   * @param serviceId
   * @param userId
   * @return
   */
  @Override
  public List<String> queryEmpower(String realm, String serviceId, String userId) {
      List<String> idList = getIdList(realm, userId);
     return getEmpowerList(idList,serviceId);
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
       return qxResourcesList.stream().map(QxResources::getPath).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
      }
    }
    return new ArrayList<String>();
  }

  private List<String> getResourcesUuid(List<String> idList, String serviceId) {
    List<String> list = new ArrayList<>();
    if (StringUtils.isNotBlank(serviceId)){
      List<QxEmpower> qxempowerList = (List<QxEmpower>) qkQxEmpowerRepository.findAll(qQxEmpower.empoerId.in(idList).and(qQxEmpower.serviceId.eq(serviceId)));
      list=getEmpower(qxempowerList);
    }else{
      List<QxEmpower> qxempowerList = (List<QxEmpower>) qkQxEmpowerRepository.findAll(qQxEmpower.empoerId.in(idList));
      list=getEmpower(qxempowerList);
    }
    return list;
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