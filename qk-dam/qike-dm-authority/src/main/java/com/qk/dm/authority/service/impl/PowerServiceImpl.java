package com.qk.dm.authority.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.entity.QQxService;
import com.qk.dm.authority.entity.QxService;
import com.qk.dm.authority.mapstruct.QxServiceMapper;
import com.qk.dm.authority.repositories.QkQxServiceRepository;
import com.qk.dm.authority.service.PowerService;
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

/**权限管理-服务
 * @author zys
 * @date 2022/2/24 11:23
 * @since 1.0.0
 */
@Service
public class PowerServiceImpl implements PowerService {
  private final QkQxServiceRepository qkQxServiceRepository;
  private final QQxService qQxService=QQxService.qxService;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;

  public PowerServiceImpl(QkQxServiceRepository qkQxServiceRepository,
      EntityManager entityManager) {
    this.qkQxServiceRepository = qkQxServiceRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public void addService(ServiceVO serviceVO) {
    serviceVO.setServiceid(UUID.randomUUID().toString());
    QxService qxService = QxServiceMapper.INSTANCE.qxService(serviceVO);
    BooleanExpression predicate = qQxService.serviceName.eq(qxService.getServiceName());
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
    qkQxServiceRepository.delete(qxService);
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