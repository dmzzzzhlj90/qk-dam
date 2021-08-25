package com.qk.dm.dataservice.service.imp;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiDatasourceConfig;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiDatasourceConfig;
import com.qk.dm.dataservice.feign.DataSourceFeign;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiDataSourceConfigMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiDatasourceConfigRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiDataSourceConfigService;
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
 * @date 2021/8/23 17:26
 * @since 1.0.0
 */
@Service
public class DasApiDataSourceConfigServiceImpl implements DasApiDataSourceConfigService {

  private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
  private static final QDasApiDatasourceConfig qDasApiDatasourceConfig =
      QDasApiDatasourceConfig.dasApiDatasourceConfig;

  private final DasApiBasicInfoService dasApiBasicInfoService;
  private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
  private final DasApiDatasourceConfigRepository dasApiDatasourceConfigRepository;
  private final DataSourceFeign dataSourceFeign;
  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Autowired
  public DasApiDataSourceConfigServiceImpl(
          DasApiBasicInfoService dasApiBasicInfoService,
          DasApiBasicInfoRepository dasApiBasicinfoRepository,
          DasApiDatasourceConfigRepository dasApiDatasourceConfigRepository,
          DataSourceFeign dataSourceFeign, EntityManager entityManager) {
    this.dasApiBasicInfoService = dasApiBasicInfoService;
    this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
    this.dasApiDatasourceConfigRepository = dasApiDatasourceConfigRepository;
    this.dataSourceFeign = dataSourceFeign;
    this.entityManager = entityManager;
  }

  @Override
  public DasApiDataSourceConfigVO getDasDataSourceConfigInfoByApiId(String apiId) {
    // 获取API基础信息
    Optional<DasApiBasicInfo> onDasApiBasicInfo =
        dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
    if (onDasApiBasicInfo.isEmpty()) {
      throw new BizException("查询不到对应的API基础信息!!!");
    }
    // 获取注册API信息
    Optional<DasApiDatasourceConfig> onDasApiDatasourceConfig =
        dasApiDatasourceConfigRepository.findOne(qDasApiDatasourceConfig.apiId.eq(apiId));
    if (onDasApiDatasourceConfig.isEmpty()) {
      DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
      return DasApiDataSourceConfigVO.builder().dasApiBasicInfoVO(dasApiBasicInfoVO).build();
    }

    DasApiDatasourceConfig dasApiDatasourceConfig = onDasApiDatasourceConfig.get();
    DasApiDataSourceConfigVO dasApiDataSourceConfigVO =
        DasApiDataSourceConfigMapper.INSTANCE.useDasApiDataSourceConfigVO(
            onDasApiDatasourceConfig.get());
    // API基础信息,设置入参定义VO转换对象
    DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
    dasApiDataSourceConfigVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
    // 新建API配置信息,设置请求/响应/排序参数VO转换对象
    setDataSourceConfVOParams(dasApiDatasourceConfig, dasApiDataSourceConfigVO);
    return dasApiDataSourceConfigVO;
  }

  private void setDataSourceConfVOParams(
      DasApiDatasourceConfig dasApiDatasourceConfig,
      DasApiDataSourceConfigVO dasApiDataSourceConfigVO) {
    if (null != dasApiDatasourceConfig.getApiRequestParas()
        && dasApiDatasourceConfig.getApiRequestParas().length() > 0) {
      dasApiDataSourceConfigVO.setApiDataSourceConfRequestParasVOS(
          GsonUtil.fromJsonString(
              dasApiDatasourceConfig.getApiRequestParas(),
              new TypeToken<List<DasApiDataSourceConfRequestParasVO>>() {}.getType()));
    }
    if (null != dasApiDatasourceConfig.getApiResponseParas()
        && dasApiDatasourceConfig.getApiResponseParas().length() > 0) {
      dasApiDataSourceConfigVO.setApiDataSourceConfResponseParasVOS(
          GsonUtil.fromJsonString(
              dasApiDatasourceConfig.getApiResponseParas(),
              new TypeToken<List<DasApiDataSourceConfResponseParasVO>>() {}.getType()));
    }
    if (null != dasApiDatasourceConfig.getApiOrderParas()
        && dasApiDatasourceConfig.getApiOrderParas().length() > 0) {
      dasApiDataSourceConfigVO.setApiDataSourceConfOrderParasVOS(
          GsonUtil.fromJsonString(
              dasApiDatasourceConfig.getApiResponseParas(),
              new TypeToken<List<DasApiDataSourceConfOrderParasVO>>() {}.getType()));
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
  public void addDasDataSourceConfig(DasApiDataSourceConfigVO dasDataSourceConfigVO) {
    String apiId = UUID.randomUUID().toString().replaceAll("-", "");
    // 保存API基础信息
    DasApiBasicInfoVO dasApiBasicInfoVO = dasDataSourceConfigVO.getDasApiBasicInfoVO();
    if (dasApiBasicInfoVO == null) {
      throw new BizException("当前新增的API所对应的基础信息为空!!!");
    }
    dasApiBasicInfoVO.setApiId(apiId);
    dasApiBasicInfoService.addDasApiBasicInfo(dasApiBasicInfoVO);

    // 保存新建API信息
    DasApiDatasourceConfig dasApiDatasourceConfig =
        DasApiDataSourceConfigMapper.INSTANCE.useDasApiDatasourceConfig(dasDataSourceConfigVO);

    // 新建API设置配置参数
    dasApiDatasourceConfig.setApiRequestParas(
        GsonUtil.toJsonString(dasDataSourceConfigVO.getApiDataSourceConfRequestParasVOS()));
    dasApiDatasourceConfig.setApiResponseParas(
        GsonUtil.toJsonString(dasDataSourceConfigVO.getApiDataSourceConfResponseParasVOS()));
    dasApiDatasourceConfig.setApiOrderParas(
        GsonUtil.toJsonString(dasDataSourceConfigVO.getApiDataSourceConfOrderParasVOS()));

    dasApiDatasourceConfig.setApiId(apiId);
    dasApiDatasourceConfig.setGmtCreate(new Date());
    dasApiDatasourceConfig.setGmtModified(new Date());
    dasApiDatasourceConfig.setDelFlag(0);
    dasApiDatasourceConfigRepository.save(dasApiDatasourceConfig);
  }

  @Transactional
  @Override
  public void updateDasDataSourceConfig(DasApiDataSourceConfigVO dasDataSourceConfigVO) {
    // 更新API基础信息
    DasApiBasicInfoVO dasApiBasicInfoVO = dasDataSourceConfigVO.getDasApiBasicInfoVO();
    dasApiBasicInfoService.updateDasApiBasicInfo(dasApiBasicInfoVO);
    // 更新新建API
    DasApiDatasourceConfig dasApiDatasourceConfig =
        DasApiDataSourceConfigMapper.INSTANCE.useDasApiDatasourceConfig(dasDataSourceConfigVO);

    // 新建API设置配置参数
    dasApiDatasourceConfig.setApiRequestParas(
        GsonUtil.toJsonString(dasDataSourceConfigVO.getApiDataSourceConfRequestParasVOS()));
    dasApiDatasourceConfig.setApiResponseParas(
        GsonUtil.toJsonString(dasDataSourceConfigVO.getApiDataSourceConfResponseParasVOS()));
    dasApiDatasourceConfig.setApiOrderParas(
        GsonUtil.toJsonString(dasDataSourceConfigVO.getApiDataSourceConfOrderParasVOS()));

    dasApiDatasourceConfig.setGmtModified(new Date());
    dasApiDatasourceConfig.setDelFlag(0);
    Predicate predicate = qDasApiDatasourceConfig.apiId.eq(dasApiDatasourceConfig.getApiId());
    boolean exists = dasApiDatasourceConfigRepository.exists(predicate);
    if (exists) {
      dasApiDatasourceConfigRepository.saveAndFlush(dasApiDatasourceConfig);
    } else {
      throw new BizException(
          "当前要新增的注册API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
    }
  }

  @Override
  public Map<String, String> getDSConfigRequestParaHeaderInfo() {
    return DasConstant.getDSConfigRequestParaHeaderInfo();
  }

  @Override
  public Map<String, String> getDSConfigResponseParaHeaderInfo() {
    return DasConstant.getDSConfigResponseParaHeaderInfo();
  }

  @Override
  public Map<String, String> getDSConfigOrderParaHeaderInfo() {
    return DasConstant.getDSConfigOrderParaHeaderInfo();
  }

  @Override
  public List<String> getDSConfigParasCompareSymbol() {
    return DasConstant.getDSConfigParasCompareSymbol();
  }

  @Override
  public Map<String, String> getDSConfigParasSortStyle() {
    return DasConstant.getDSConfigParasSortStyle();
  }

  @Override
  public List<String> getAllConnType() {
    return dataSourceFeign.getAllConnType().getData();
  }

  @Override
  public DefaultCommonResult getDataSourceByType(String type) {
    return dataSourceFeign.getDataSourceByType(type);
  }

}
