package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataservice.constant.*;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateConfig;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiCreateConfig;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateConfigMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiCreateConfigService;
import com.qk.dm.dataservice.utils.SqlExecuteUtils;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    private static final QDasApiCreateConfig qDasApiCreateConfig = QDasApiCreateConfig.dasApiCreateConfig;

    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;


    @Autowired
    public DasApiCreateConfigServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService,
            DasApiBasicInfoRepository dasApiBasicinfoRepository,
            DasApiCreateConfigRepository dasApiCreateConfigRepository) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
    }

    @Override
    public DasApiCreateConfigVO detail(String apiId) {

        // 获取API基础信息
        Optional<DasApiBasicInfo> onDasApiBasicInfo = dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
        if (onDasApiBasicInfo.isEmpty()) {
            throw new BizException("查询不到对应的API基础信息!!!");
        }
        // 获取注册API信息
        Optional<DasApiCreateConfig> onDasApiCreateConfig = dasApiCreateConfigRepository.findOne(qDasApiCreateConfig.apiId.eq(apiId));
        if (onDasApiCreateConfig.isEmpty()) {
            DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
            return DasApiCreateConfigVO.builder().dasApiBasicInfoVO(dasApiBasicInfoVO).build();
        }

        DasApiCreateConfig dasApiCreate = onDasApiCreateConfig.get();
        DasApiCreateConfigVO dasApiCreateConfigVO = DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfigVO(onDasApiCreateConfig.get());
        // API基础信息,设置入参定义VO转换对象
        DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
        dasApiCreateConfigVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
        // 新建API配置信息,设置请求/响应/排序参数VO转换对象
        setDasApiCreateVOParams(dasApiCreate, dasApiCreateConfigVO);
        return dasApiCreateConfigVO;
    }

    /**
     * 新建API配置信息,设置请求/响应/排序参数VO转换对象
     *
     * @param dasApiCreateConfig
     * @param dasApiCreateConfigVO
     */
    private void setDasApiCreateVOParams(DasApiCreateConfig dasApiCreateConfig, DasApiCreateConfigVO dasApiCreateConfigVO) {
        if (null != dasApiCreateConfig.getApiRequestParas() && dasApiCreateConfig.getApiRequestParas().length() > 0) {
            dasApiCreateConfigVO.setApiCreateRequestParasVOS(
                    GsonUtil.fromJsonString(dasApiCreateConfig.getApiRequestParas(),
                            new TypeToken<List<DasApiCreateRequestParasVO>>() {
                            }.getType()));
        }
        if (null != dasApiCreateConfig.getApiResponseParas() && dasApiCreateConfig.getApiResponseParas().length() > 0) {
            dasApiCreateConfigVO.setApiCreateResponseParasVOS(
                    GsonUtil.fromJsonString(dasApiCreateConfig.getApiResponseParas(),
                            new TypeToken<List<DasApiCreateResponseParasVO>>() {
                            }.getType()));
        }
        if (null != dasApiCreateConfig.getApiOrderParas() && dasApiCreateConfig.getApiOrderParas().length() > 0) {
            dasApiCreateConfigVO.setApiCreateOrderParasVOS(
                    GsonUtil.fromJsonString(dasApiCreateConfig.getApiResponseParas(),
                            new TypeToken<List<DasApiCreateOrderParasVO>>() {
                            }.getType()));
        }
    }

    /**
     * API基础信息,设置入参定义VO转换对象
     *
     * @param onDasApiBasicInfo
     * @return
     */
    private DasApiBasicInfoVO setDasApiBasicInfoDelInputParam(Optional<DasApiBasicInfo> onDasApiBasicInfo) {
        DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();
        DasApiBasicInfoVO dasApiBasicInfoVO = DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(dasApiBasicInfo);
        String defInputParam = dasApiBasicInfo.getDefInputParam();
        if (defInputParam != null && defInputParam.length() != 0) {
            dasApiBasicInfoVO.setDasApiBasicInfoRequestParasVO(
                    GsonUtil.fromJsonString(dasApiBasicInfo.getDefInputParam(),
                            new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
                            }.getType()));
        }
        return dasApiBasicInfoVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DasApiCreateConfigVO dasApiCreateConfigVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateConfigVO.getDasApiBasicInfoVO();
        if (dasApiBasicInfoVO == null) {
            throw new BizException("当前新增的API所对应的基础信息为空!!!");
        }
        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoService.insert(dasApiBasicInfoVO);

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DasApiCreateConfigVO dasApiCreateConfigVO) {
        // 更新API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateConfigVO.getDasApiBasicInfoVO();
        dasApiBasicInfoService.update(dasApiBasicInfoVO);
        // 更新新建API
        DasApiCreateConfig dasApiCreate = DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfig(dasApiCreateConfigVO);

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
    public LinkedList<Map<String, Object>> getRequestParamHeaderInfo() {
        return CreateRequestParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public LinkedList<Map<String, Object>> getResponseParamHeaderInfo() {
        return CreateResponseParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public LinkedList<Map<String, Object>> getOrderParamHeaderInfo() {
        return CreateOrderParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public List<String> getParamCompareSymbol() {
        return DasConstant.getDasApiCreateParamCompareSymbol();
    }

    @Override
    public Map<String, String> getParamSortStyle() {
        return CreateParamSortStyleEnum.getAllValue();
    }

    @Override
    public LinkedList<Map<String, Object>> getParamHeaderInfo() {
        return CreateConfigParamHeaderEnum.getAllValue();
    }

    @Override
    public DebugApiResultVO debugModel(DasApiCreateConfigVO apiCreateConfigVO) {
        // 1.生成查询SQL(根据数据源类型)
        //数据源连接类型
        String connectType = apiCreateConfigVO.getConnectType();
        //表名称
        String tableName = apiCreateConfigVO.getTableName();
        //新建API接口定义入参(对应元数据映射关系)
        Map<String, String> mappingParams = apiCreateConfigVO.getApiCreateRequestParasVOS().stream()
                .collect(Collectors.toMap(DasApiCreateRequestParasVO::getParaName, DasApiCreateRequestParasVO::getMappingName));

        //真实请求参数(SQL where条件,使用字段参数)
        Map<String, String> reqParams = apiCreateConfigVO.getDebugApiParasVOS().stream()
                .collect(Collectors.toMap(DebugApiParasVO::getParaName, DebugApiParasVO::getValue));

        //响应参数(SQL返回值映射查询数据)
        List<String> resParams = apiCreateConfigVO.getApiCreateResponseParasVOS()
                .stream().map(DasApiCreateResponseParasVO::getMappingName).collect(Collectors.toList());
        //生成查询sql
        String executeSql = SqlExecuteUtils.mysqlExecuteSQL(tableName, reqParams, resParams,mappingParams);

        // 2.执行查询SQL(根据数据源类型)


        return  DebugApiResultVO.builder().resultData("").build();
    }

}
