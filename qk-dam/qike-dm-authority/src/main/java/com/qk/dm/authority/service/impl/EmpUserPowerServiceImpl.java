package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.*;
import com.qk.dm.authority.mapstruct.QxResourcesMenuMapper;
import com.qk.dm.authority.mapstruct.QxServiceMapper;
import com.qk.dm.authority.repositories.*;
import com.qk.dm.authority.service.AtyUserGroupService;
import com.qk.dm.authority.service.AtyUserRoleService;
import com.qk.dm.authority.service.EmpUserPowerService;
import com.qk.dm.authority.vo.params.UserEmpParamVO;
import com.qk.dm.authority.vo.params.UserEmpPowerParamVO;
import com.qk.dm.authority.vo.powervo.EmpResourceUrlVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuQueryVO;
import com.qk.dm.authority.vo.powervo.ServiceQueryVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/3/9 10:25
 * @since 1.0.0
 */
@Service
public class EmpUserPowerServiceImpl implements EmpUserPowerService {
  private final QQkQxResourcesApi qQkQxResourcesApi = QQkQxResourcesApi.qkQxResourcesApi;
  private final QQkQxResourcesMenu qQkQxResourcesMenu = QQkQxResourcesMenu.qkQxResourcesMenu;
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower =QQkQxResourcesEmpower.qkQxResourcesEmpower;
  private final QQxEmpower qQxEmpower = QQxEmpower.qxEmpower;
  private final QQxService qQxService = QQxService.qxService;
  private final QkQxResourcesApiRepository qkQxResourcesApiRepository;
  private final QkQxResourcesMenuRepository qkQxResourcesMenuRepository;
  private final AtyUserGroupService atyUserGroupService;
  private final AtyUserRoleService atyUserRoleService;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private final QkQxServiceRepository qkQxServiceRepository;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final EmpRsMenuServiceImpl empRsMenuServiceImpl;
  public EmpUserPowerServiceImpl(
      QkQxResourcesApiRepository qkQxResourcesApiRepository,
      QkQxResourcesMenuRepository qkQxResourcesMenuRepository,
      AtyUserGroupService atyUserGroupService,
      AtyUserRoleService atyUserRoleService,
      QkQxEmpowerRepository qkQxEmpowerRepository,
      QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository,
      EntityManager entityManager,QkQxServiceRepository qkQxServiceRepository1,
      EmpRsMenuServiceImpl empRsMenuServiceImpl) {
    this.qkQxResourcesApiRepository = qkQxResourcesApiRepository;
    this.qkQxResourcesMenuRepository = qkQxResourcesMenuRepository;
    this.atyUserGroupService = atyUserGroupService;
    this.atyUserRoleService = atyUserRoleService;
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
    this.qkQxResourcesEmpowerRepository = qkQxResourcesEmpowerRepository;
    this.entityManager = entityManager;
    this.qkQxServiceRepository = qkQxServiceRepository1;
    this.empRsMenuServiceImpl = empRsMenuServiceImpl;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  /**
   * ??????????????????
   * @param userEmpParamVO
   * @return
   */
  @Override
  public List<ServiceQueryVO> queryServicesByUserId(UserEmpParamVO userEmpParamVO) {
    List<String> idList = getIdList(userEmpParamVO.getRealm(),userEmpParamVO.getUserId(),userEmpParamVO.getClientId());
    //3????????????id?????????id???????????????id????????????????????????
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, idList);
    List<QxEmpower> qxEmpowerList = jpaQueryFactory.select(qQxEmpower).from(qQxEmpower).where(booleanBuilder).orderBy(qQxEmpower.gmtCreate.desc()).fetch();
    if (CollectionUtils.isNotEmpty(qxEmpowerList)){
      List<String> serviceidList = qxEmpowerList.stream().map(QxEmpower::getServiceId).collect(Collectors.toList());
      List<QxService> qxServiceList = getServices(serviceidList);
      return  QxServiceMapper.INSTANCE.serviceQueryVOof(qxServiceList);
    }
    return new ArrayList<ServiceQueryVO>();
  }

  private List<QxService> getServices(List<String> serviceidList) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkServiceCondition(booleanBuilder, serviceidList);
    return jpaQueryFactory.select(qQxService).from(qQxService).where(booleanBuilder).orderBy(qQxService.gmtCreate.desc()).fetch();
  }

  private void checkServiceCondition(BooleanBuilder booleanBuilder, List<String> serviceidList) {
    if (CollectionUtils.isNotEmpty(serviceidList)){
      booleanBuilder.and(qQxService.serviceId.in(serviceidList));
    }
  }

  private void checkCondition(BooleanBuilder booleanBuilder, List<String> idList) {
    if (CollectionUtils.isNotEmpty(idList)) {
      booleanBuilder.and(qQxEmpower.empoerId.in(idList));
    }
  }

  private List<String> getIdList(String realm, String userId ,String clientId) {
    List<String> idList = new ArrayList<>();
    //1????????????id????????????????????????
    List<AtyGroupInfoVO> userGroup = atyUserGroupService.getUserGroup(realm, userId);
    //2????????????id????????????????????????????????????
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
   * ????????????api
   * @param userEmpPowerParamVO
   * @return
   */
  @Override
  public List<String> queryEmpower(UserEmpPowerParamVO userEmpPowerParamVO) {
      List<String> idList = getIdList(userEmpPowerParamVO.getRealm(),userEmpPowerParamVO.getUserId(),userEmpPowerParamVO.getClientId());
     return getEmpowerList(idList,userEmpPowerParamVO.getServiceId());
  }

  private List<String> getEmpowerList(List<String> idList, String serviceId) {
   List<String> resourcesUuidList = getResourcesUuid(idList,serviceId);
    return getUserPower(resourcesUuidList);
  }

  /**
   * ????????????UUID??????????????????
   * @param resourcesUuidList
   * @return
   */
  private List<String> getUserPower(List<String> resourcesUuidList) {
    if (CollectionUtils.isNotEmpty(resourcesUuidList)){
      List<QkQxResourcesApi> resourcesApiList = (List<QkQxResourcesApi>) qkQxResourcesApiRepository.findAll(qQkQxResourcesApi.resourcesId.in(resourcesUuidList));
      if (CollectionUtils.isNotEmpty(resourcesApiList)){
        return resourcesApiList.stream().map(QkQxResourcesApi::getPath).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
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

  /**
   * ????????????id??????????????????
   * @param userEmpPowerParamVO
   * @return
   */
  @Override
  public List<EmpResourceUrlVO> queryEmpowerResource(UserEmpPowerParamVO userEmpPowerParamVO) {
    List<String> idList = getIdList(userEmpPowerParamVO.getRealm(),userEmpPowerParamVO.getUserId(),userEmpPowerParamVO.getClientId());
    return getResourcesList(idList,userEmpPowerParamVO.getServiceId());
  }

  private List<EmpResourceUrlVO> getResourcesList(List<String> idList, String serviceId) {
    List<String> resourcesUuidList = getResourcesUuid(idList,serviceId);
    return getUserResouces(resourcesUuidList,serviceId);
  }

  private List<EmpResourceUrlVO> getUserResouces(List<String> resourcesUuidList, String serviceId) {
    //??????????????????????????????????????????
    List<QkQxResourcesMenu> qkQxResourcesMenuAllList = qkQxResourcesMenuRepository.findByServiceId(serviceId);
    if (CollectionUtils.isNotEmpty(resourcesUuidList)){
      //?????????????????????????????????
      List<QkQxResourcesMenu> qxResourcesMenuList = (List<QkQxResourcesMenu>) qkQxResourcesMenuRepository.findAll(qQkQxResourcesMenu.resourcesId.in(resourcesUuidList));
      //???????????????????????????????????????
      List<QkQxResourcesMenu> qxResourcesMenusList = getResoucesMenuList(qkQxResourcesMenuAllList,qxResourcesMenuList);
      //????????????
      return getEmpResouccUrlVO(qxResourcesMenusList,serviceId);
    }
    return new ArrayList<EmpResourceUrlVO>();
  }

  private List<EmpResourceUrlVO> getEmpResouccUrlVO(List<QkQxResourcesMenu> qxResourcesMenusList, String serviceId) {
    //??????????????????
    QxService qxService = qkQxServiceRepository.findByServiceId(serviceId);
    String name = !Objects.isNull(qxService) ? qxService.getServiceName() : QxConstant.SERVICE_NAME;
    if (CollectionUtils.isNotEmpty(qxResourcesMenusList)){
      //???????????????
      List<QkQxResourcesMenu> qxResourcesMenuList = qxResourcesMenusList.stream().distinct().collect(Collectors.toList());
      List<ResourceMenuQueryVO> resourceMenuQueryVOList = QxResourcesMenuMapper.INSTANCE
          .ResourceMenuQueryVOof(qxResourcesMenuList);
      List<ResourceMenuQueryVO> trees = new ArrayList<>();
      ResourceMenuQueryVO resourceMenuQueryVO = ResourceMenuQueryVO.builder().id(QxConstant.DIRID).title(name).value(QxConstant.RESOURCEID).build();
      trees.add(empRsMenuServiceImpl.findChildren(resourceMenuQueryVO, resourceMenuQueryVOList));
      List<EmpResourceUrlVO> empResourceUrlVOS = QxResourcesMenuMapper.INSTANCE.ofEmpResourceUrlVO(trees);
      return empResourceUrlVOS;
    }
    return new ArrayList<EmpResourceUrlVO>();
  }

  private List<QkQxResourcesMenu> getResoucesMenuList(List<QkQxResourcesMenu> qkQxResourcesMenuAllList, List<QkQxResourcesMenu> qxResourcesMenuList) {
    if (CollectionUtils.isNotEmpty(qkQxResourcesMenuAllList)&& CollectionUtils.isNotEmpty(qxResourcesMenuList)){
      Map<Long,QkQxResourcesMenu> qkQxResourcesMenuMap = qkQxResourcesMenuAllList.stream().collect(Collectors.toMap(QkQxResourcesMenu::getId,qkQxResourcesMenu -> qkQxResourcesMenu));
      List<QkQxResourcesMenu> list = new ArrayList<>();
      qxResourcesMenuList.forEach(qkQxResourcesMenu -> {
      getAllResourceMenu(list,qkQxResourcesMenu,qkQxResourcesMenuMap);
      });
      return list;
    }
    return new ArrayList<QkQxResourcesMenu>();
  }

  /**
   * ?????????????????????????????????????????????
   * @param list
   * @param qkQxResourcesMenu
   * @param qkQxResourcesMenuMap
   */
  private void getAllResourceMenu(List<QkQxResourcesMenu> list, QkQxResourcesMenu qkQxResourcesMenu,Map<Long,QkQxResourcesMenu> qkQxResourcesMenuMap) {
    list.add(qkQxResourcesMenu);
    QkQxResourcesMenu qxResourcesMenu = qkQxResourcesMenuMap.get(qkQxResourcesMenu.getPid());
    if (!Objects.isNull(qxResourcesMenu)){
      this.getAllResourceMenu(list,qxResourcesMenu,qkQxResourcesMenuMap);
    }
  }
}