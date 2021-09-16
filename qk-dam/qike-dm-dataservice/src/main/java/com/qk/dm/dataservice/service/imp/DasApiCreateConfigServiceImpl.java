package com.qk.dm.dataservice.service.imp;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.utils.ConnectInfoConvertUtils;
import com.qk.dam.metedata.entity.*;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateConfig;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiCreateConfig;
import com.qk.dm.dataservice.feign.DataSourceFeign;
import com.qk.dm.dataservice.feign.MetaDataFeign;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateConfigMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiCreateConfigService;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据服务_新建API_配置方式
 *
 * @author wjq
 * @date 2021/8/23 17:26
 * @since 1.0.0
 */
@Service
public class DasApiCreateConfigServiceImpl implements DasApiCreateConfigService {

  private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
  private static final QDasApiCreateConfig qDasApiCreateConfig =
      QDasApiCreateConfig.dasApiCreateConfig;

  private final DasApiBasicInfoService dasApiBasicInfoService;
  private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
  private final DasApiCreateConfigRepository dasApiCreateConfigRepository;

  private final DataSourceFeign dataSourceFeign;
  private final MetaDataFeign metaDataFeign;

  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Autowired
  public DasApiCreateConfigServiceImpl(
      DasApiBasicInfoService dasApiBasicInfoService,
      DasApiBasicInfoRepository dasApiBasicinfoRepository,
      DasApiCreateConfigRepository dasApiCreateConfigRepository,
      DataSourceFeign dataSourceFeign,
      MetaDataFeign metaDataFeign,
      EntityManager entityManager) {
    this.dasApiBasicInfoService = dasApiBasicInfoService;
    this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
    this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
    this.dataSourceFeign = dataSourceFeign;
    this.metaDataFeign = metaDataFeign;
    this.entityManager = entityManager;
  }

  @Override
  public DasApiCreateConfigVO getDasApiCreateConfigInfoByApiId(String apiId) {

    // 获取API基础信息
    Optional<DasApiBasicInfo> onDasApiBasicInfo =
        dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
    if (onDasApiBasicInfo.isEmpty()) {
      throw new BizException("查询不到对应的API基础信息!!!");
    }
    // 获取注册API信息
    Optional<DasApiCreateConfig> onDasApiCreateConfig =
        dasApiCreateConfigRepository.findOne(qDasApiCreateConfig.apiId.eq(apiId));
    if (onDasApiCreateConfig.isEmpty()) {
      DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
      return DasApiCreateConfigVO.builder().dasApiBasicInfoVO(dasApiBasicInfoVO).build();
    }

    DasApiCreateConfig dasApiCreate = onDasApiCreateConfig.get();
    DasApiCreateConfigVO dasApiCreateConfigVO =
        DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfigVO(onDasApiCreateConfig.get());
    // API基础信息,设置入参定义VO转换对象
    DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
    dasApiCreateConfigVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
    // 新建API配置信息,设置请求/响应/排序参数VO转换对象
    setDasApiCreateVOParams(dasApiCreate, dasApiCreateConfigVO);
    return dasApiCreateConfigVO;
  }

  private void setDasApiCreateVOParams(
      DasApiCreateConfig dasApiCreateConfig, DasApiCreateConfigVO dasApiCreateConfigVO) {
    if (null != dasApiCreateConfig.getApiRequestParas()
        && dasApiCreateConfig.getApiRequestParas().length() > 0) {
      dasApiCreateConfigVO.setApiCreateRequestParasVOS(
          GsonUtil.fromJsonString(
              dasApiCreateConfig.getApiRequestParas(),
              new TypeToken<List<DasApiCreateRequestParasVO>>() {}.getType()));
    }
    if (null != dasApiCreateConfig.getApiResponseParas()
        && dasApiCreateConfig.getApiResponseParas().length() > 0) {
      dasApiCreateConfigVO.setApiCreateResponseParasVOS(
          GsonUtil.fromJsonString(
              dasApiCreateConfig.getApiResponseParas(),
              new TypeToken<List<DasApiCreateResponseParasVO>>() {}.getType()));
    }
    if (null != dasApiCreateConfig.getApiOrderParas()
        && dasApiCreateConfig.getApiOrderParas().length() > 0) {
      dasApiCreateConfigVO.setApiCreateOrderParasVOS(
          GsonUtil.fromJsonString(
              dasApiCreateConfig.getApiResponseParas(),
              new TypeToken<List<DasApiCreateOrderParasVO>>() {}.getType()));
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
  public void addDasApiCreateConfig(DasApiCreateConfigVO dasApiCreateConfigVO) {
    String apiId = UUID.randomUUID().toString().replaceAll("-", "");
    // 保存API基础信息
    DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateConfigVO.getDasApiBasicInfoVO();
    if (dasApiBasicInfoVO == null) {
      throw new BizException("当前新增的API所对应的基础信息为空!!!");
    }
    dasApiBasicInfoVO.setApiId(apiId);
    dasApiBasicInfoService.addDasApiBasicInfo(dasApiBasicInfoVO);

    // 保存新建API信息
    DasApiCreateConfig dasApiCreateConfig =
        DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfig(dasApiCreateConfigVO);

    // 新建API设置配置参数
    dasApiCreateConfig.setApiRequestParas(
        GsonUtil.toJsonString(dasApiCreateConfigVO.getApiCreateRequestParasVOS()));
    dasApiCreateConfig.setApiResponseParas(
        GsonUtil.toJsonString(dasApiCreateConfigVO.getApiCreateResponseParasVOS()));
    dasApiCreateConfig.setApiOrderParas(
        GsonUtil.toJsonString(dasApiCreateConfigVO.getApiCreateOrderParasVOS()));

    dasApiCreateConfig.setApiId(apiId);
    dasApiCreateConfig.setGmtCreate(new Date());
    dasApiCreateConfig.setGmtModified(new Date());
    dasApiCreateConfig.setDelFlag(0);
    dasApiCreateConfigRepository.save(dasApiCreateConfig);
  }

  @Transactional
  @Override
  public void updateDasApiCreateConfig(DasApiCreateConfigVO dasApiCreateConfigVO) {
    // 更新API基础信息
    DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateConfigVO.getDasApiBasicInfoVO();
    dasApiBasicInfoService.updateDasApiBasicInfo(dasApiBasicInfoVO);
    // 更新新建API
    DasApiCreateConfig dasApiCreate =
        DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfig(dasApiCreateConfigVO);

    // 新建API设置配置参数
    dasApiCreate.setApiRequestParas(
        GsonUtil.toJsonString(dasApiCreateConfigVO.getApiCreateRequestParasVOS()));
    dasApiCreate.setApiResponseParas(
        GsonUtil.toJsonString(dasApiCreateConfigVO.getApiCreateResponseParasVOS()));
    dasApiCreate.setApiOrderParas(
        GsonUtil.toJsonString(dasApiCreateConfigVO.getApiCreateOrderParasVOS()));

    dasApiCreate.setGmtModified(new Date());
    dasApiCreate.setDelFlag(0);
    Predicate predicate = qDasApiCreateConfig.apiId.eq(dasApiCreate.getApiId());
    boolean exists = dasApiCreateConfigRepository.exists(predicate);
    if (exists) {
      dasApiCreateConfigRepository.saveAndFlush(dasApiCreate);
    } else {
      throw new BizException("当前要新增的API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
    }
  }

  // ========================参数配置表头信息=====================================
  @Override
  public Map<String, String> getDasApiCreateRequestParaHeaderInfo() {
    return DasConstant.getDasApiCreateRequestParaHeaderInfo();
  }

  @Override
  public Map<String, String> getDasApiCreateResponseParaHeaderInfo() {
    return DasConstant.getDasApiCreateResponseParaHeaderInfo();
  }

  @Override
  public Map<String, String> getDasApiCreateOrderParaHeaderInfo() {
    return DasConstant.getDasApiCreateOrderParaHeaderInfo();
  }

  @Override
  public List<String> getDasApiCreateParasCompareSymbol() {
    return DasConstant.getDasApiCreateParasCompareSymbol();
  }

  @Override
  public Map<String, String> getDasApiCreateParasSortStyle() {
    return DasConstant.getDasApiCreateParasSortStyle();
  }

  // ========================数据源服务API调用=====================================
  @Override
  public List<String> getAllConnType() {
    return dataSourceFeign.getAllConnType().getData();
  }

  @Override
  public List<ResultDatasourceInfo> getResultDataSourceByType(String type) {
    return dataSourceFeign.getResultDataSourceByType(type).getData();
  }

  @Override
  public ResultDatasourceInfo getResultDataSourceByConnectName(String connectName) {
    ResultDatasourceInfo resultDatasourceInfo =
        dataSourceFeign.getResultDataSourceByConnectName(connectName).getData();
    ConnectBasicInfo connectInfo =
        ConnectInfoConvertUtils.getConnectInfo(
            resultDatasourceInfo.getDbType(), resultDatasourceInfo.getConnectBasicInfoJson());
    return resultDatasourceInfo;
  }

  // ========================元数据服务API调用=====================================
  @Override
  public List<MtdAtlasEntityType> getAllEntityType() {
    return metaDataFeign.getAllEntityType().getData();
  }

  @Override
  public MtdApi mtdDetail(MtdApiParams mtdApiParams) {
    return metaDataFeign.mtdDetail(mtdApiParams).getData();
  }

  @Override
  public List<String> getAllDataBase(String dbType) {
    String type = dbType.split("-")[1];
    DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult =
        metaDataFeign.mtdDetail(MtdApiParams.builder().typeName(type + "_db").build());
    List<MtdApiDb> mtdApiDbs = mtdApiDefaultCommonResult.getData().getEntities();
    return mtdApiDbs.stream().map(MtdApiDb::getDisplayText).collect(Collectors.toList());
  }

  @Override
  public List<String> getAllTable(String dbType, String server, String dbName) {
    String type = dbType.split("-")[1];
    DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult =
        metaDataFeign.mtdDetail(
            MtdApiParams.builder().typeName(type + "_db").server(server).dbName(dbName).build());
    List<MtdTables> mtdTablesList = mtdApiDefaultCommonResult.getData().getTables();
    return mtdTablesList.stream().map(MtdTables::getDisplayText).collect(Collectors.toList());
  }

  @Override
  public List getAllColumn(String dbType, String server, String dbName, String tableName) {
    String type = dbType.split("-")[1];
    DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult =
        metaDataFeign.mtdDetail(
            MtdApiParams.builder()
                .typeName(type + "_table")
                .server(server)
                .dbName(dbName)
                .tableName(tableName)
                .build());
    return mtdApiDefaultCommonResult.getData().getColumns();
  }
}
