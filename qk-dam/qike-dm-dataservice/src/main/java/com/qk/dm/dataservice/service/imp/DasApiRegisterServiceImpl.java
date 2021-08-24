package com.qk.dm.dataservice.service.imp;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiRegister;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiRegisterMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiRegisterService;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjq
 * @date 20210817
 * @since 1.0.0
 */
@Service
public class DasApiRegisterServiceImpl implements DasApiRegisterService {
  private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
  private static final QDasApiRegister qDasApiRegister = QDasApiRegister.dasApiRegister;

  private final DasApiBasicInfoService dasApiBasicInfoService;
  private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
  private final DasApiRegisterRepository dasApiRegisterRepository;
  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;

  @Autowired
  public DasApiRegisterServiceImpl(
      DasApiBasicInfoService dasApiBasicInfoService,
      DasApiBasicInfoRepository dasApiBasicinfoRepository,
      DasApiRegisterRepository dasApiRegisterRepository,
      EntityManager entityManager) {
    this.dasApiBasicInfoService = dasApiBasicInfoService;
    this.dasApiRegisterRepository = dasApiRegisterRepository;
    this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public DasApiRegisterVO getDasApiRegisterInfoByApiId(String apiId) {
    // 获取API基础信息
    Optional<DasApiBasicInfo> onDasApiBasicInfo =
        dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
    if (onDasApiBasicInfo.isEmpty()) {
      throw new BizException("查询不到对应的API基础信息!!!");
    }
    // 获取注册API信息
    Optional<DasApiRegister> onDasApiRegister =
        dasApiRegisterRepository.findOne(qDasApiRegister.apiId.eq(apiId));
    if (onDasApiRegister.isEmpty()) {
      DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
      return DasApiRegisterVO.builder().dasApiBasicInfoVO(dasApiBasicInfoVO).build();
    }
    DasApiRegister dasApiRegister = onDasApiRegister.get();
    DasApiRegisterVO dasApiRegisterVO =
        DasApiRegisterMapper.INSTANCE.useDasApiRegisterVO(dasApiRegister);
    // API基础信息,设置入参定义VO转换对象
    DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
    dasApiRegisterVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
    // 注册API配置信息,设置后端参数VO转换对象
    setRegisterVOBackendAndConstantsParams(dasApiRegister, dasApiRegisterVO);
    return dasApiRegisterVO;
  }

  private void setRegisterVOBackendAndConstantsParams(
      DasApiRegister dasApiRegister, DasApiRegisterVO dasApiRegisterVO) {
    if (null != dasApiRegister.getBackendRequestParas()
        && dasApiRegister.getBackendRequestParas().length() > 0) {
      dasApiRegisterVO.setDasApiRegisterBackendParaVO(
          GsonUtil.fromJsonString(
              dasApiRegister.getBackendRequestParas(),
              new TypeToken<List<DasApiRegisterBackendParaVO>>() {}.getType()));
    }

    if (null != dasApiRegister.getBackendConstants()
        && dasApiRegister.getBackendConstants().length() > 0) {

      dasApiRegisterVO.setDasApiRegisterConstantParaVO(
          GsonUtil.fromJsonString(
              dasApiRegister.getBackendConstants(),
              new TypeToken<List<DasApiRegisterConstantParaVO>>() {}.getType()));
    }
  }

  private DasApiBasicInfoVO setDasApiBasicInfoDelInputParam(
      Optional<DasApiBasicInfo> onDasApiBasicInfo) {
    DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();
    DasApiBasicInfoVO dasApiBasicInfoVO =
        DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(dasApiBasicInfo);
    String defInputParam = dasApiBasicInfo.getDefInputParam();
    if (defInputParam != null && defInputParam.length() != 0) {
      dasApiBasicInfoVO.setDasApiBasicInfoRequestParasVO(
          GsonUtil.fromJsonString(
              dasApiBasicInfo.getDefInputParam(),
              new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {}.getType()));
    }
    return dasApiBasicInfoVO;
  }

  @Transactional
  @Override
  public void addDasApiRegister(DasApiRegisterVO dasApiRegisterVO) {
    String apiId = UUID.randomUUID().toString().replaceAll("-", "");
    // 保存API基础信息
    DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getDasApiBasicInfoVO();
    if (dasApiBasicInfoVO == null) {
      throw new BizException("当前新增的API所对应的基础信息为空!!!");
    }
    dasApiBasicInfoVO.setApiId(apiId);
    dasApiBasicInfoService.addDasApiBasicInfo(dasApiBasicInfoVO);

    // 保存注册API信息
    DasApiRegister dasApiRegister =
        DasApiRegisterMapper.INSTANCE.useDasApiRegister(dasApiRegisterVO);
    dasApiRegister.setBackendRequestParas(
        GsonUtil.toJsonString(dasApiRegisterVO.getDasApiRegisterBackendParaVO()));
    dasApiRegister.setBackendConstants(
        GsonUtil.toJsonString(dasApiRegisterVO.getDasApiRegisterConstantParaVO()));
    dasApiRegister.setApiId(apiId);
    dasApiRegister.setGmtCreate(new Date());
    dasApiRegister.setGmtModified(new Date());
    dasApiRegister.setDelFlag(0);
    dasApiRegisterRepository.save(dasApiRegister);
  }

  @Transactional
  @Override
  public void updateDasApiRegister(DasApiRegisterVO dasApiRegisterVO) {
    // 更新API基础信息
    DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getDasApiBasicInfoVO();
    dasApiBasicInfoService.updateDasApiBasicInfo(dasApiBasicInfoVO);
    // 更新注册API
    DasApiRegister dasApiRegister =
        DasApiRegisterMapper.INSTANCE.useDasApiRegister(dasApiRegisterVO);
    dasApiRegister.setBackendRequestParas(
        GsonUtil.toJsonString(dasApiRegisterVO.getDasApiRegisterBackendParaVO()));
    dasApiRegister.setBackendConstants(
        GsonUtil.toJsonString(dasApiRegisterVO.getDasApiRegisterConstantParaVO()));
    dasApiRegister.setGmtModified(new Date());
    dasApiRegister.setDelFlag(0);
    Predicate predicate = qDasApiRegister.apiId.eq(dasApiRegister.getApiId());
    boolean exists = dasApiRegisterRepository.exists(predicate);
    if (exists) {
      dasApiRegisterRepository.saveAndFlush(dasApiRegister);
    } else {
      throw new BizException(
          "当前要新增的注册API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
    }
  }

  @Override
  public Map<String, String> getRegisterBackendParaHeaderInfo() {
    return DasConstant.getRegisterBackendParaHeaderInfo();
  }

  @Override
  public Map<String, String> getRegisterConstantParaHeaderInfo() {
    return DasConstant.getRegisterConstantParaHeaderInfo();
  }
}
