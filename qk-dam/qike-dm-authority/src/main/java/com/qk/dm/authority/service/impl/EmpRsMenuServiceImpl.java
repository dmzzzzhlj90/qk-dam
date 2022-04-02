package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.QQkQxResourcesEmpower;
import com.qk.dm.authority.entity.QQkQxResourcesMenu;
import com.qk.dm.authority.entity.QkQxResourcesEmpower;
import com.qk.dm.authority.entity.QkQxResourcesMenu;
import com.qk.dm.authority.mapstruct.QxResourcesMenuMapper;
import com.qk.dm.authority.repositories.QkQxEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesMenuRepository;
import com.qk.dm.authority.service.EmpRsMenuService;
import com.qk.dm.authority.vo.params.ResourceParamVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuQueryVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源
 * @author zys
 * @date 2022/3/31 14:42
 * @since 1.0.0
 */
@Service
public class EmpRsMenuServiceImpl implements EmpRsMenuService {
  private final QQkQxResourcesMenu qQkQxResourcesMenu=QQkQxResourcesMenu.qkQxResourcesMenu;
  private final QkQxResourcesMenuRepository qkQxResourcesMenuRepository;
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower=QQkQxResourcesEmpower.qkQxResourcesEmpower;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;


  public EmpRsMenuServiceImpl(
      QkQxResourcesMenuRepository qkQxResourcesMenuRepository,
      QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository,
      QkQxEmpowerRepository qkQxEmpowerRepository) {
    this.qkQxResourcesMenuRepository = qkQxResourcesMenuRepository;
    this.qkQxResourcesEmpowerRepository = qkQxResourcesEmpowerRepository;
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
  }


  /**
   * 新增资源
   * @param resourceMenuVO
   */
  @Override
  public void addResourceMenu(ResourceMenuVO resourceMenuVO) {
    resourceMenuVO.setResourcesId(UUID.randomUUID().toString());
    QkQxResourcesMenu qkQxResourcesMenu = QxResourcesMenuMapper.INSTANCE.qxResourcesMenu(resourceMenuVO);
    BooleanExpression predicate = qQkQxResourcesMenu.name.eq(resourceMenuVO.getName()).and(qQkQxResourcesMenu.serviceId.eq(resourceMenuVO.getServiceId()));
    boolean exists = qkQxResourcesMenuRepository.exists(predicate);
    if (exists){
      throw new BizException(
          "当前新增名称为:"
              + resourceMenuVO.getName()
              + " 的资源数据，已存在！！！");
    }else {
      qkQxResourcesMenuRepository.save(qkQxResourcesMenu);
    }
  }

  /**
   * 编辑资源
   * @param resourceMenuVO
   */
  @Override
  public void updateResourceMenu(ResourceMenuVO resourceMenuVO) {
    QkQxResourcesMenu qkQxResourcesMenuOne = qkQxResourcesMenuRepository
        .findById(resourceMenuVO.getId()).orElse(null);
    if (Objects.isNull(qkQxResourcesMenuOne)){
      throw new BizException("当前需修改的名称为"+resourceMenuVO.getName()+"的资源数据不存在");
    }
    QxResourcesMenuMapper.INSTANCE.from(resourceMenuVO,qkQxResourcesMenuOne);
    QkQxResourcesMenu qkQxResourcesMenu = QxResourcesMenuMapper.INSTANCE.qxResourcesMenu(resourceMenuVO);
    qkQxResourcesMenuRepository.saveAndFlush(qkQxResourcesMenu);
  }

  /**
   *删除资源
   * @param id
   */
  @Override
  @Transactional
  public void deleteResourceMenu(Long id) {
    ArrayList<Long> ids = new ArrayList<>();
    ids.add(id);
    getResoursMenuId(id,ids);
    List<QkQxResourcesMenu> qkQxResourcesMenuList = (List<QkQxResourcesMenu>) qkQxResourcesMenuRepository.findAll(qQkQxResourcesMenu.id.in(ids));
    if (CollectionUtils.isEmpty(qkQxResourcesMenuList)) {
      throw new BizException("当前需删除的资源数据不存在");
    }
    dealRsEmpMessage(qkQxResourcesMenuList);
  }

  private void dealRsEmpMessage(List<QkQxResourcesMenu> qkQxResourcesMenuList) {
    qkQxResourcesMenuList.stream().forEach(qxResources->{
        deleteResourceEmpower(qxResources.getResourcesId());
        qkQxResourcesMenuRepository.deleteById(qxResources.getId());
    });
  }

  /**
   * 根据资源id删除资源授权关系表
   * @param id
   */
  private void deleteResourceEmpower(String id) {
    BooleanExpression expression = qQkQxResourcesEmpower.resourceUuid.eq(id);
    boolean exists = qkQxResourcesEmpowerRepository.exists(expression);
    if (exists){
      dealEmpPower(id);
      qkQxResourcesEmpowerRepository.deleteALLByResourceUuid(id);
    }
  }

  /**
   * 处理授权表信息（如果授权信息只存在当前一条授权资源，则删除授权信息否则不删除授权信息）
   * @param id
   */
  private void dealEmpPower(String id) {
    Map<String,List<QkQxResourcesEmpower>> map = getRsEmpMap();
    List<QkQxResourcesEmpower> qxRsEmpList = qkQxResourcesEmpowerRepository.findByResourceUuid(id);
    if (CollectionUtils.isNotEmpty(qxRsEmpList) && MapUtils.isNotEmpty(map)){
      List<String> EmpowerIdList = qxRsEmpList.stream().map(QkQxResourcesEmpower::getEmpowerUuid).collect(Collectors.toList());
      EmpowerIdList.stream().forEach(empowerUuid->{
        List<QkQxResourcesEmpower> qkQxResourcesEmpowerList = map.get(empowerUuid);
        if (CollectionUtils.isNotEmpty(qkQxResourcesEmpowerList) && qkQxResourcesEmpowerList.size()<= QxConstant.RS_EMP){
          qkQxEmpowerRepository.deleteByEmpowerId(empowerUuid);
        }
      });
    }
  }

  /**
   * 获取资源授权关系，以授权信息uuid为key
   * @return
   */
  private Map<String,List<QkQxResourcesEmpower>> getRsEmpMap() {
    List<QkQxResourcesEmpower> rsEmpList = qkQxResourcesEmpowerRepository.findAll();
    if (CollectionUtils.isNotEmpty(rsEmpList)){
      Map<String,List<QkQxResourcesEmpower>> map = rsEmpList.stream().collect(Collectors.groupingBy(QkQxResourcesEmpower::getEmpowerUuid));
      return map;
    }
    return new HashMap<String,List<QkQxResourcesEmpower>>();
  }

  /**
   * 根据服务UUID查询资源
   * @param resourceParamVO 查询资源条件
   */
  @Override
  public List<ResourceMenuQueryVO> queryResourceMenu(ResourceParamVO resourceParamVO) {
    List<ResourceMenuQueryVO> resourceOutVOList = new ArrayList<>();
    List<QkQxResourcesMenu> qkQxResourcesMenuList = qkQxResourcesMenuRepository.findByServiceId(resourceParamVO.getServiceId());
    if (CollectionUtils.isNotEmpty(qkQxResourcesMenuList)){
      resourceOutVOList = QxResourcesMenuMapper.INSTANCE.ResourceMenuQueryVOof(qkQxResourcesMenuList);
      return buildByResource(resourceOutVOList,resourceParamVO.getName());
    }
    return  new ArrayList<ResourceMenuQueryVO>();
  }

  private List<ResourceMenuQueryVO> buildByResource(List<ResourceMenuQueryVO> resourceOutVOList, String name) {
    List<ResourceMenuQueryVO> trees = new ArrayList<>();
    ResourceMenuQueryVO resourceMenuQueryVO = ResourceMenuQueryVO.builder().id(QxConstant.DIRID).title(name).value(QxConstant.RESOURCEID).build();
    trees.add(findChildren(resourceMenuQueryVO, resourceOutVOList));
    return trees;
  }

  ResourceMenuQueryVO findChildren(ResourceMenuQueryVO resourceMenuQueryVO,
      List<ResourceMenuQueryVO> resourceOutVOList) {
    resourceMenuQueryVO.setChildren(new ArrayList<>());
    if (CollectionUtils.isNotEmpty(resourceOutVOList)){
      resourceOutVOList.forEach(resourceMenuQueryVO1 -> {
        if (resourceMenuQueryVO.getId().equals(resourceMenuQueryVO1.getPid())){
          resourceMenuQueryVO.getChildren().add(findChildren(resourceMenuQueryVO1,resourceOutVOList));
        }
      });
    }
    return resourceMenuQueryVO;
  }

  /**
   * 查询删除的资源是否已经授权
   * @param id 资源id
   */
  @Override
  public Boolean qeryRsMenuEmp(Long id) {
    ArrayList<Long> ids = new ArrayList<>();
    ids.add(id);
    getResoursMenuId(id,ids);
    List<QkQxResourcesMenu> qkQxResourcesMenuList = qkQxResourcesMenuRepository
        .findAllById(ids);
    if (CollectionUtils.isEmpty(qkQxResourcesMenuList)) {
      throw new BizException("当前需删除的数据不存在");
    }
    List<String> resourcesIdList = qkQxResourcesMenuList.stream().map(QkQxResourcesMenu::getResourcesId).collect(Collectors.toList());
    List<QkQxResourcesEmpower> rsEmpList = (List<QkQxResourcesEmpower>) qkQxResourcesEmpowerRepository
        .findAll(qQkQxResourcesEmpower.resourceUuid.in(resourcesIdList));
    if (CollectionUtils.isNotEmpty(rsEmpList)){
      return true;
    }
    return false;
  }

  /**
   * 递归查询当前删除的层级下所有子集的id集合
   * @param id
   * @param ids
   * @return
   */
  private void getResoursMenuId(Long id, List<Long> ids) {
    Optional<QkQxResourcesMenu> qkQxResourcesMenu = qkQxResourcesMenuRepository.findOne(qQkQxResourcesMenu.id.eq(id));
    Iterable<QkQxResourcesMenu> sonResourceMenuList = qkQxResourcesMenuRepository
        .findAll(qQkQxResourcesMenu.pid.eq(qkQxResourcesMenu.get().getId()));
    for (QkQxResourcesMenu qxResourcesMenu : sonResourceMenuList ){
      ids.add(qxResourcesMenu.getId());
      this.getResoursMenuId(qxResourcesMenu.getId(),ids);
    }
  }
}