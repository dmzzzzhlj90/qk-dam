package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.entity.QQxEmpower;
import com.qk.dm.authority.entity.QxEmpower;
import com.qk.dm.authority.keycloak.keyClocakEmpowerApi;
import com.qk.dm.authority.mapstruct.QxEmpowerMapper;
import com.qk.dm.authority.repositories.QkQxEmpowerRepository;
import com.qk.dm.authority.service.PowerEmpowerService;
import com.qk.dm.authority.vo.params.EmpowerParamVO;
import com.qk.dm.authority.vo.powervo.EmpowerVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * @author zys
 * @date 2022/2/24 11:24
 * @since 1.0.0
 */
@Service
public class PowerEmpowerServiceImpl implements PowerEmpowerService {
  private final QkQxEmpowerRepository qkQxEmpowerRepository;
  private final QQxEmpower qQxEmpower = QQxEmpower.qxEmpower;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  @Autowired
  private keyClocakEmpowerApi keyClocakEmpowerApi;


  public PowerEmpowerServiceImpl(QkQxEmpowerRepository qkQxEmpowerRepository,EntityManager entityManager) {
    this.qkQxEmpowerRepository = qkQxEmpowerRepository;
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
      keyClocakEmpowerApi.addPower(empowerVO);
      qkQxEmpowerRepository.save(qxEmpower);
    }
  }

  @Override
  public EmpowerVO EmpowerDetails(Long id) {
    QxEmpower qxEmpower = qkQxEmpowerRepository.findById(id).orElse(null);
    if (Objects.isNull(qxEmpower)){
      throw new BizException(
          "当前查询数据为空"
      );
    }
    return QxEmpowerMapper.INSTANCE.qxEmpowerVO(qxEmpower);
  }

  @Override
  public void updateEmpower(EmpowerVO empowerVO) {
    QxEmpower qxEmpower = qkQxEmpowerRepository.findById(empowerVO.getId())
        .orElse(null);
    if (Objects.isNull(qxEmpower)){
      throw new BizException("当前需修改的的数据不存在");
    }
    QxEmpower qxEmpower1 = QxEmpowerMapper.INSTANCE.qxEmpower(empowerVO);
    //TODO 修改对应授权主体的属性
    keyClocakEmpowerApi.addPower(empowerVO);
    qkQxEmpowerRepository.saveAndFlush(qxEmpower1);
  }

  @Override
  public void deleteEmpower(Long id) {
    QxEmpower qxEmpower = qkQxEmpowerRepository.findById(id).orElse(null);
    if (Objects.isNull(qxEmpower)){
      throw new BizException(
          "当前需删除的数据不存在"
      );
    }
    //TODO 删除授权主体中的属性
    EmpowerVO empowerVO = QxEmpowerMapper.INSTANCE.qxEmpowerVO(qxEmpower);
    keyClocakEmpowerApi.deletePower(empowerVO);
    qkQxEmpowerRepository.delete(qxEmpower);
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