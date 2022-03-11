package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.QQkQxResourcesEmpower;
import com.qk.dm.authority.entity.QQxResources;
import com.qk.dm.authority.entity.QkQxResourcesEmpower;
import com.qk.dm.authority.entity.QxResources;
import com.qk.dm.authority.mapstruct.QxResourcesMapper;
import com.qk.dm.authority.repositories.QkQxEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesRepository;
import com.qk.dm.authority.service.EmpRsService;
import com.qk.dm.authority.vo.params.ApiResourcesParamVO;
import com.qk.dm.authority.vo.params.PowerResourcesParamVO;
import com.qk.dm.authority.vo.params.ResourceParamVO;
import com.qk.dm.authority.vo.powervo.ResourceOutVO;
import com.qk.dm.authority.vo.powervo.ResourceVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**权限管理-资源
 * @author zys
 * @date 2022/2/24 11:24
 * @since 1.0.0
 */
@Service
public class EmpRsServiceImpl implements EmpRsService {
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower=QQkQxResourcesEmpower.qkQxResourcesEmpower;
  private final QkQxResourcesRepository qkQxResourcesRepository;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private final QQxResources qQxResources= QQxResources.qxResources;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;

  public EmpRsServiceImpl(QkQxResourcesRepository qkQxResourcesRepository,
      QkQxEmpowerRepository qkQxEmpowerRepository,
      QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository,
      EntityManager entityManager) {
    this.qkQxResourcesRepository = qkQxResourcesRepository;
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
    this.qkQxResourcesEmpowerRepository = qkQxResourcesEmpowerRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }
  @Override
  public void addResource(ResourceVO resourceVO) {
    resourceVO.setResourcesid(UUID.randomUUID().toString());
    QxResources qxResources = QxResourcesMapper.INSTANCE.qxResources(resourceVO);
    BooleanExpression predicate = qQxResources.name.eq(resourceVO.getName()).and(qQxResources.serviceId.eq(resourceVO.getServiceId())).and(qQxResources.type.eq(resourceVO.getType()));
    boolean exists = qkQxResourcesRepository.exists(predicate);
    if (exists){
      throw new BizException(
          "当前新增名称为:"
              + resourceVO.getName()
              + " 的数据，已存在！！！");
    }else {
      qkQxResourcesRepository.save(qxResources);
    }
  }

  @Override
  public void updateResource(ResourceVO resourceVO) {
    QxResources qxResources = qkQxResourcesRepository.findById(resourceVO.getId()).orElse(null);
    if (Objects.isNull(qxResources)){
      throw new BizException("当前需修改的名称为"+resourceVO.getName()+"的数据不存在");
    }
    QxResources qxResource = QxResourcesMapper.INSTANCE.qxResources(resourceVO);
    qkQxResourcesRepository.saveAndFlush(qxResource);
  }

  @Override
  @Transactional
  public void deleteResource(Long id) {
    QxResources qxResources = qkQxResourcesRepository.findById(id).orElse(null);
    if (Objects.isNull(qxResources)) {
      throw new BizException("当前需删除的数据不存在");
    }
    if (qxResources.getType()==QxConstant.API_TYPE) {
      deleteResourceEmpower(qxResources.getResourcesid());
      qkQxResourcesRepository.deleteById(id);
    } else {
      List<QxResources> qxResourcesList = qkQxResourcesRepository.findByPid(qxResources.getId());
      if (CollectionUtils.isNotEmpty(qxResourcesList)){
        throw new BizException(
            "存在子节点，请先删除"
        );
      }else{
        deleteResourceEmpower(qxResources.getResourcesid());
        qkQxResourcesRepository.deleteById(id);
      }
    }
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
        if (CollectionUtils.isNotEmpty(qkQxResourcesEmpowerList) && qkQxResourcesEmpowerList.size()<=QxConstant.RS_EMP){
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

  @Override
  public List<ResourceOutVO> queryResource(ResourceParamVO resourceParamVO) {
    List<ResourceOutVO> resourceOutVOList = new ArrayList<>();
    List<QxResources> qxResourcesList = qkQxResourcesRepository.findByServiceId(resourceParamVO.getServiceId());
    //筛选资源数据
    List<QxResources> list = qxResourcesList.stream().filter(qxResources -> qxResources.getType().equals(QxConstant.RESOURCE_TYPE)).collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(list)){
      resourceOutVOList = QxResourcesMapper.INSTANCE.of(list);
    }
    return buildByResource(resourceOutVOList,resourceParamVO.getName());
  }

  @Override
  public List<ResourceVO> queryResourceApi(ApiResourcesParamVO apiResourcesParamVO) {
    List<ResourceVO> resourceVOList = new ArrayList<>();
    List<QxResources> qxResourcesList = qkQxResourcesRepository.findByServiceId(apiResourcesParamVO.getServiceId());
    //筛选资源数据
    List<QxResources> list = qxResourcesList.stream().filter(qxResources -> qxResources.getPid() == QxConstant.PID
    ).collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(list)){
      resourceVOList = QxResourcesMapper.INSTANCE.qxResourcesOf(list);
    }
    return resourceVOList;
  }

  @Override
  public List<ResourceVO> queryauthorized(PowerResourcesParamVO powerResourcesParamVO) {
    List<QxResources> qxResourcesList =new ArrayList<>();
    List<QxResources> byServiceId = qkQxResourcesRepository.findByServiceId(powerResourcesParamVO.getServiceId());
    if (powerResourcesParamVO.getType()==QxConstant.API_TYPE){
       qxResourcesList = byServiceId.stream().filter(qxResources ->powerResourcesParamVO.getResourceSign().contains(qxResources.getId().toString())).collect(Collectors.toList());
    }else{
      qxResourcesList = byServiceId.stream().filter(qxResources ->powerResourcesParamVO.getResourceSign().contains(qxResources.getId().toString())).collect(Collectors.toList());
    }
    return  qxResourcesList.stream().map(QxResourcesMapper.INSTANCE::qxResourceVO).collect(Collectors.toList());
  }

  @Override
  public Boolean qeryRsEmp(Long id) {
    QxResources qxResources = qkQxResourcesRepository.findById(id).orElse(null);
    if (Objects.isNull(qxResources)) {
      throw new BizException("当前需删除的数据不存在");
    }
    List<QkQxResourcesEmpower> rsEmpList = qkQxResourcesEmpowerRepository
        .findByResourceUuid(qxResources.getResourcesid());
    if (CollectionUtils.isNotEmpty(rsEmpList)){
      return true;
    }
    return false;
  }

  private List<ResourceOutVO> buildByResource(
      List<ResourceOutVO> resourceOutVOList, String name) {
    List<ResourceOutVO> trees = new ArrayList<>();
    ResourceOutVO resourceOutVO = ResourceOutVO.builder().id(QxConstant.DIRID).name(name).build();
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
}