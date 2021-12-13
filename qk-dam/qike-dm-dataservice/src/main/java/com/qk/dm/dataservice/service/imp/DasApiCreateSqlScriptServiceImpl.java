package com.qk.dm.dataservice.service.imp;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateSqlScript;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiCreateSqlScript;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateSqlScriptMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateSqlScriptRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiCreateSqlScriptService;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.types.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据服务_新建API_脚本方式
 *
 * @author wjq
 * @date 20210830
 * @since 1.0.0
 */
@Service
public class DasApiCreateSqlScriptServiceImpl implements DasApiCreateSqlScriptService {

  private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
  private static final QDasApiCreateSqlScript qDasApiCreateSqlScript =
      QDasApiCreateSqlScript.dasApiCreateSqlScript;

  private final DasApiBasicInfoService dasApiBasicInfoService;
  private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
  private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;

  @Autowired
  public DasApiCreateSqlScriptServiceImpl(
      DasApiBasicInfoService dasApiBasicInfoService,
      DasApiBasicInfoRepository dasApiBasicinfoRepository,
      DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository) {
    this.dasApiBasicInfoService = dasApiBasicInfoService;
    this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
    this.dasApiCreateSqlScriptRepository = dasApiCreateSqlScriptRepository;
  }

  @Override
  public DasApiCreateSqlScriptVO detail(String apiId) {
    // 获取API基础信息
    Optional<DasApiBasicInfo> onDasApiBasicInfo =
        dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
    if (onDasApiBasicInfo.isEmpty()) {
      throw new BizException("查询不到对应的API基础信息!!!");
    }
    // 获取注册API信息
    Optional<DasApiCreateSqlScript> onDasApiCreateSqlScript =
        dasApiCreateSqlScriptRepository.findOne(qDasApiCreateSqlScript.apiId.eq(apiId));
    if (onDasApiCreateSqlScript.isEmpty()) {
      DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
      return DasApiCreateSqlScriptVO.builder().dasApiBasicInfoVO(dasApiBasicInfoVO).build();
    }

    DasApiCreateSqlScript dasApiCreateSqlScript = onDasApiCreateSqlScript.get();
    DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO =
        DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScriptVO(
            onDasApiCreateSqlScript.get());
    // API基础信息,设置入参定义VO转换对象
    DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
    dasApiCreateSqlScriptVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
    // 新建API配置信息,设置请求/响应/排序参数VO转换对象
    setDasApiCreateSqlScriptVOParams(dasApiCreateSqlScript, dasApiCreateSqlScriptVO);
    return dasApiCreateSqlScriptVO;
  }

  private void setDasApiCreateSqlScriptVOParams(
      DasApiCreateSqlScript dasApiCreateSqlScript,
      DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
    if (null != dasApiCreateSqlScript.getApiRequestParas()
        && dasApiCreateSqlScript.getApiRequestParas().length() > 0) {
      dasApiCreateSqlScriptVO.setApiCreateRequestParasVOS(
          GsonUtil.fromJsonString(
              dasApiCreateSqlScript.getApiRequestParas(),
              new TypeToken<List<DasApiCreateRequestParasVO>>() {}.getType()));
    }
    if (null != dasApiCreateSqlScript.getApiResponseParas()
        && dasApiCreateSqlScript.getApiResponseParas().length() > 0) {
      dasApiCreateSqlScriptVO.setApiCreateResponseParasVOS(
          GsonUtil.fromJsonString(
              dasApiCreateSqlScript.getApiResponseParas(),
              new TypeToken<List<DasApiCreateResponseParasVO>>() {}.getType()));
    }
    if (null != dasApiCreateSqlScript.getApiOrderParas()
        && dasApiCreateSqlScript.getApiOrderParas().length() > 0) {
      dasApiCreateSqlScriptVO.setApiCreateOrderParasVOS(
          GsonUtil.fromJsonString(
              dasApiCreateSqlScript.getApiResponseParas(),
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
  public void insert(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
    String apiId = UUID.randomUUID().toString().replaceAll("-", "");
    // 保存API基础信息
    DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateSqlScriptVO.getDasApiBasicInfoVO();
    if (dasApiBasicInfoVO == null) {
      throw new BizException("当前新增的API所对应的基础信息为空!!!");
    }
    dasApiBasicInfoVO.setApiId(apiId);
    dasApiBasicInfoService.insert(dasApiBasicInfoVO);

    // 保存新建API信息
    DasApiCreateSqlScript dasApiCreateConfig =
        DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScript(dasApiCreateSqlScriptVO);

    // 新建API设置配置参数
    dasApiCreateConfig.setApiRequestParas(
        GsonUtil.toJsonString(dasApiCreateSqlScriptVO.getApiCreateRequestParasVOS()));
    dasApiCreateConfig.setApiResponseParas(
        GsonUtil.toJsonString(dasApiCreateSqlScriptVO.getApiCreateResponseParasVOS()));
    dasApiCreateConfig.setApiOrderParas(
        GsonUtil.toJsonString(dasApiCreateSqlScriptVO.getApiCreateOrderParasVOS()));

    dasApiCreateConfig.setApiId(apiId);
    dasApiCreateConfig.setGmtCreate(new Date());
    dasApiCreateConfig.setGmtModified(new Date());
    dasApiCreateConfig.setDelFlag(0);
    dasApiCreateSqlScriptRepository.save(dasApiCreateConfig);
  }

  @Transactional
  @Override
  public void update(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
    // 更新API基础信息
    DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateSqlScriptVO.getDasApiBasicInfoVO();
    dasApiBasicInfoService.update(dasApiBasicInfoVO);
    // 更新新建API
    DasApiCreateSqlScript dasApiCreate =
        DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScript(dasApiCreateSqlScriptVO);

    // 新建API设置配置参数
    dasApiCreate.setApiRequestParas(
        GsonUtil.toJsonString(dasApiCreateSqlScriptVO.getApiCreateRequestParasVOS()));
    dasApiCreate.setApiResponseParas(
        GsonUtil.toJsonString(dasApiCreateSqlScriptVO.getApiCreateResponseParasVOS()));
    dasApiCreate.setApiOrderParas(
        GsonUtil.toJsonString(dasApiCreateSqlScriptVO.getApiCreateOrderParasVOS()));

    dasApiCreate.setGmtModified(new Date());
    dasApiCreate.setDelFlag(0);
    Predicate predicate = qDasApiCreateSqlScript.apiId.eq(dasApiCreate.getApiId());
    boolean exists = dasApiCreateSqlScriptRepository.exists(predicate);
    if (exists) {
      dasApiCreateSqlScriptRepository.saveAndFlush(dasApiCreate);
    } else {
      throw new BizException("当前要新增的API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
    }
  }
}
