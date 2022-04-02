package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.*;
import com.qk.dm.authority.mapstruct.QxResourcesApiMapper;
import com.qk.dm.authority.repositories.QkQxEmpowerRepository;
import com.qk.dm.authority.repositories.QkQxResourcesApiRepository;
import com.qk.dm.authority.repositories.QkQxResourcesEmpowerRepository;
import com.qk.dm.authority.service.EmpRsApiService;
import com.qk.dm.authority.vo.params.ApiPageResourcesParamVO;
import com.qk.dm.authority.vo.params.ApiResourcesParamVO;
import com.qk.dm.authority.vo.powervo.ResourceApiVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**访问（api）
 * @author zys
 * @date 2022/3/31 14:40
 * @since 1.0.0
 */
@Service
public class EmpRsApiServiceImpl implements EmpRsApiService {
  private final QQkQxResourcesEmpower qQkQxResourcesEmpower=QQkQxResourcesEmpower.qkQxResourcesEmpower;
  private final QQkQxResourcesApi qQkQxResourcesApi = QQkQxResourcesApi.qkQxResourcesApi;
  private final QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository;
  private final QkQxResourcesApiRepository qkQxResourcesApiRepository;
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;


  public EmpRsApiServiceImpl(
      QkQxResourcesEmpowerRepository qkQxResourcesEmpowerRepository,
      QkQxResourcesApiRepository qkQxResourcesApiRepository,
      QkQxEmpowerRepository qkQxEmpowerRepository, EntityManager entityManager) {
    this.qkQxResourcesEmpowerRepository = qkQxResourcesEmpowerRepository;
    this.qkQxResourcesApiRepository = qkQxResourcesApiRepository;
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }
  /**
   * 新增访问（api）
   * @param resourceApiVO
   */
  @Override
  public void addApiResource(ResourceApiVO resourceApiVO) {
    resourceApiVO.setResourcesId(UUID.randomUUID().toString());
    QkQxResourcesApi qkQxResourcesApi = QxResourcesApiMapper.INSTANCE.qxApiResources(resourceApiVO);
    BooleanExpression predicate = qQkQxResourcesApi.name.eq(resourceApiVO.getName())
        .and(qQkQxResourcesApi.serviceId.eq(resourceApiVO.getServiceId()));
    boolean exists = qkQxResourcesApiRepository.exists(predicate);
    if (exists){
      throw new BizException(
          "当前新增名称为:"
              + resourceApiVO.getName()
              + " 的API数据，已存在！！！");
    }else {
      qkQxResourcesApiRepository.save(qkQxResourcesApi);
    }
  }
  /**
   * 编辑访问（api）
   * @param resourceApiVO
   */
  @Override
  public void updateApiResource(ResourceApiVO resourceApiVO) {

    QkQxResourcesApi qkQxResourcesApiOne = qkQxResourcesApiRepository.findById(resourceApiVO.getId()).orElse(null);
    if (Objects.isNull(qkQxResourcesApiOne)){
      throw new BizException("当前需修改的名称为"+resourceApiVO.getName()+"的API数据不存在");
    }
    QxResourcesApiMapper.INSTANCE.from(resourceApiVO,qkQxResourcesApiOne);
    qkQxResourcesApiRepository.saveAndFlush(qkQxResourcesApiOne);
  }

  /**
   * 删除访问（api）
   * @param ids
   */
  @Override
  @Transactional
  public void deleteApiResource(String ids) {
    List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<QkQxResourcesApi> qkQxResourcesList = (List<QkQxResourcesApi>) qkQxResourcesApiRepository
        .findAll(qQkQxResourcesApi.id.in(idList));
    if (CollectionUtils.isEmpty(qkQxResourcesList)) {
      throw new BizException("当前需删除的数据不存在");
    }
    dealApiRsMessage(qkQxResourcesList);
  }

  private void dealApiRsMessage(List<QkQxResourcesApi> qkQxResourcesList) {
    qkQxResourcesList.stream().forEach(qkQxResourcesApi->{
        deleteResourceEmpower(qkQxResourcesApi.getResourcesId());
      qkQxResourcesApiRepository.deleteById(qkQxResourcesApi.getId());
    });
  }

  private void deleteResourceEmpower(String resourcesid) {
    BooleanExpression expression = qQkQxResourcesEmpower.resourceUuid.eq(resourcesid);
    boolean exists = qkQxResourcesEmpowerRepository.exists(expression);
    if (exists){
      dealEmpPower(resourcesid);
      qkQxResourcesEmpowerRepository.deleteALLByResourceUuid(resourcesid);
    }
  }
  /**
   * 处理授权表信息（如果授权信息只存在当前一条授权资源，则删除授权信息否则不删除授权信息）
   * @param resourcesid
   */
  private void dealEmpPower(String resourcesid) {
    Map<String,List<QkQxResourcesEmpower>> map = getRsEmpMap();
    List<QkQxResourcesEmpower> qxRsEmpList = qkQxResourcesEmpowerRepository.findByResourceUuid(resourcesid);
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
   * 根据服务UUID查询API（不分页-新增授权使用）
   * @param apiResourcesParamVO 查询api条件
   */
  @Override
  public List<ResourceApiVO> queryApiResource(ApiResourcesParamVO apiResourcesParamVO) {
    List<ResourceApiVO> resourceApiVOList = new ArrayList<>();
    List<QkQxResourcesApi> qxApiResourcesList = qkQxResourcesApiRepository.findByServiceId(apiResourcesParamVO.getServiceId());
    if (CollectionUtils.isNotEmpty(qxApiResourcesList)){
      resourceApiVOList = QxResourcesApiMapper.INSTANCE.qxApiResourcesOf(qxApiResourcesList);
    }
    return resourceApiVOList;
  }


  /**
   * 根据服务UUID、api名称查询api信息--分页查询
   * @param apiPageResourcesParamVO 分页查询api信息条件
   */
  @Override
  public PageResultVO<ResourceApiVO> queryApiPageEmpower(ApiPageResourcesParamVO apiPageResourcesParamVO) {
    Map<String, Object> map;
    List<ResourceApiVO> resourceApiVOList = new ArrayList<>();
    try {
      map = queryByParams(apiPageResourcesParamVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    if (MapUtils.isNotEmpty(map)){
      List<QkQxResourcesApi> qkQxResourcesApiList = (List<QkQxResourcesApi>) map.get("list");
      if (CollectionUtils.isNotEmpty(qkQxResourcesApiList)){
        resourceApiVOList = QxResourcesApiMapper.INSTANCE.qxApiResourcesOf(qkQxResourcesApiList);
      }
    }
    return new PageResultVO<>(
        (long) map.get("total"),
        apiPageResourcesParamVO.getPagination().getPage(),
        apiPageResourcesParamVO.getPagination().getSize(),
        resourceApiVOList);
  }

  private Map<String,Object> queryByParams(ApiPageResourcesParamVO apiPageResourcesParamVO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkResource(booleanBuilder, apiPageResourcesParamVO);
    Map<String, Object> result = new HashMap<>();
    long count =jpaQueryFactory.select(qQkQxResourcesApi.count()).from(qQkQxResourcesApi).where(booleanBuilder).fetchOne();
    List<QkQxResourcesApi> qkQxResourcesApiList = jpaQueryFactory.select(qQkQxResourcesApi)
        .from(qQkQxResourcesApi).where(booleanBuilder)
        .orderBy(qQkQxResourcesApi.gmtModified.asc()).offset(
            (long) (apiPageResourcesParamVO.getPagination().getPage() - 1)
                * apiPageResourcesParamVO.getPagination().getSize())
        .limit(apiPageResourcesParamVO.getPagination().getSize()).fetch();
    result.put("list", qkQxResourcesApiList);
    result.put("total", count);
    return result;
  }

  private void checkResource(BooleanBuilder booleanBuilder, ApiPageResourcesParamVO apiPageResourcesParamVO) {
    if (!Objects.isNull(apiPageResourcesParamVO.getName())) {
      booleanBuilder.and(qQkQxResourcesApi.name.eq(apiPageResourcesParamVO.getName()));
    }
    if(!Objects.isNull(apiPageResourcesParamVO.getServiceId())){
      booleanBuilder.and(qQkQxResourcesApi.serviceId.eq(apiPageResourcesParamVO.getServiceId()));
    }
  }

  /**
   * 查询删除的访问（api）是否已经授权
   * @param ids api的id字符串用“,”分隔
   * @return DefaultCommonResult<Boolean> 返回值为true表示存在为false表示不存在
   */
  @Override
  public Boolean qeryApiRsEmp(String ids) {
    List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<QkQxResourcesApi> qkQxResourcesApiList = qkQxResourcesApiRepository.findAllById(idList);
    if (CollectionUtils.isEmpty(qkQxResourcesApiList)) {
      throw new BizException("当前需删除的数据不存在");
    }
    List<String> resourcesIdList = qkQxResourcesApiList.stream().map(QkQxResourcesApi::getResourcesId).collect(Collectors.toList());
    List<QkQxResourcesEmpower> rsEmpList = (List<QkQxResourcesEmpower>) qkQxResourcesEmpowerRepository.findAll(qQkQxResourcesEmpower.resourceUuid.in(resourcesIdList));
    if (CollectionUtils.isNotEmpty(rsEmpList)){
      return true;
    }
    return false;
  }
}