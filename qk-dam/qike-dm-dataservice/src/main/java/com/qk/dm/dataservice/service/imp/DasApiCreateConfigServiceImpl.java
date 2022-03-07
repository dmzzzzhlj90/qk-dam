package com.qk.dm.dataservice.service.imp;

import cn.hutool.db.Entity;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataservice.biz.MysqlSqlExecutor;
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
    private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;


    @Autowired
    public DasApiCreateConfigServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService,
            DasApiBasicInfoRepository dasApiBasicinfoRepository,
            DasApiCreateConfigRepository dasApiCreateConfigRepository, DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
        this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
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
            return DasApiCreateConfigVO.builder().apiBasicInfoVO(dasApiBasicInfoVO).build();
        }

        DasApiCreateConfigVO dasApiCreateConfigVO = DasApiCreateConfigVO.builder().build();

        // API基础信息,设置入参定义VO转换对象
        DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
        dasApiCreateConfigVO.setApiBasicInfoVO(dasApiBasicInfoVO);
        // 新建API配置方式,配置信息VO转换
        DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO = DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfigDefinitionVO(onDasApiCreateConfig.get());
        // 新建API配置信息,设置请求/响应/排序参数VO转换对象
        setDasApiCreateVOParams(onDasApiCreateConfig.get(), apiCreateConfigDefinitionVO);
        dasApiCreateConfigVO.setApiCreateDefinitionVO(apiCreateConfigDefinitionVO);

        return dasApiCreateConfigVO;
    }

    /**
     * 新建API配置信息,设置请求/响应/排序参数VO转换对象
     *
     * @param dasApiCreateConfig
     * @param apiCreateConfigDefinitionVO
     */
    private void setDasApiCreateVOParams(DasApiCreateConfig dasApiCreateConfig,
                                         DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO) {
        if (null != dasApiCreateConfig.getApiRequestParas() && dasApiCreateConfig.getApiRequestParas().length() > 0) {
            apiCreateConfigDefinitionVO.setApiCreateRequestParasVOS(
                    GsonUtil.fromJsonString(dasApiCreateConfig.getApiRequestParas(),
                            new TypeToken<List<DasApiCreateRequestParasVO>>() {
                            }.getType()));
        }
        if (null != dasApiCreateConfig.getApiResponseParas() && dasApiCreateConfig.getApiResponseParas().length() > 0) {
            apiCreateConfigDefinitionVO.setApiCreateResponseParasVOS(
                    GsonUtil.fromJsonString(dasApiCreateConfig.getApiResponseParas(),
                            new TypeToken<List<DasApiCreateResponseParasVO>>() {
                            }.getType()));
        }
        if (null != dasApiCreateConfig.getApiOrderParas() && dasApiCreateConfig.getApiOrderParas().length() > 0) {
            apiCreateConfigDefinitionVO.setApiCreateOrderParasVOS(
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
            dasApiBasicInfoVO.setApiBasicInfoRequestParasVOS(
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
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateConfigVO.getApiBasicInfoVO();
        if (dasApiBasicInfoVO == null) {
            throw new BizException("当前新增的API所对应的基础信息为空!!!");
        }
        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoService.insert(dasApiBasicInfoVO);

        // 保存新建API信息
        DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO = dasApiCreateConfigVO.getApiCreateDefinitionVO();
        DasApiCreateConfig dasApiCreateConfig =
                DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfig(apiCreateConfigDefinitionVO);

        // 新建API设置配置参数
        dasApiCreateConfig.setApiRequestParas(
                GsonUtil.toJsonString(apiCreateConfigDefinitionVO.getApiCreateRequestParasVOS()));
        dasApiCreateConfig.setApiResponseParas(
                GsonUtil.toJsonString(apiCreateConfigDefinitionVO.getApiCreateResponseParasVOS()));
        dasApiCreateConfig.setApiOrderParas(
                GsonUtil.toJsonString(apiCreateConfigDefinitionVO.getApiCreateOrderParasVOS()));

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
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateConfigVO.getApiBasicInfoVO();
        dasApiBasicInfoService.update(dasApiBasicInfoVO);
        // 更新新建API
        DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO = dasApiCreateConfigVO.getApiCreateDefinitionVO();
        DasApiCreateConfig dasApiCreateConfig = DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfig(apiCreateConfigDefinitionVO);

        // 新建API设置配置参数
        dasApiCreateConfig.setApiRequestParas(
                GsonUtil.toJsonString(apiCreateConfigDefinitionVO.getApiCreateRequestParasVOS()));
        dasApiCreateConfig.setApiResponseParas(
                GsonUtil.toJsonString(apiCreateConfigDefinitionVO.getApiCreateResponseParasVOS()));
        dasApiCreateConfig.setApiOrderParas(
                GsonUtil.toJsonString(apiCreateConfigDefinitionVO.getApiCreateOrderParasVOS()));

        dasApiCreateConfig.setGmtModified(new Date());
        dasApiCreateConfig.setDelFlag(0);
        Predicate predicate = qDasApiCreateConfig.apiId.eq(dasApiCreateConfig.getApiId());
        boolean exists = dasApiCreateConfigRepository.exists(predicate);
        if (exists) {
            dasApiCreateConfigRepository.saveAndFlush(dasApiCreateConfig);
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
        return OperationSymbolEnum.getAllValue();
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
        DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO = apiCreateConfigVO.getApiCreateDefinitionVO();
        String connectType = apiCreateConfigDefinitionVO.getConnectType();
        //数据库
        String dataBaseName = apiCreateConfigDefinitionVO.getDataBaseName();
        //表名称
        String tableName = apiCreateConfigDefinitionVO.getTableName();
        // 新建API接口定义入参(对应元数据映射关系)
        Map<String, List<DasApiCreateRequestParasVO>> mappingParams =
                apiCreateConfigDefinitionVO.getApiCreateRequestParasVOS().stream()
                        .collect(Collectors.groupingBy(DasApiCreateRequestParasVO::getParaName));

        // 真实请求参数(SQL where条件,使用字段参数)
        Map<String, String> reqParams = apiCreateConfigVO.getDebugApiParasVOS().stream()
                .collect(Collectors.toMap(
                        DebugApiParasVO::getParaName, DebugApiParasVO::getValue));

        // 响应参数(SQL返回值映射查询数据)
        Map<String,String> resParaMap = apiCreateConfigDefinitionVO.getApiCreateResponseParasVOS()
                .stream().collect(Collectors.toMap(
                        DasApiCreateResponseParasVO::getMappingName,DasApiCreateResponseParasVO::getParaName));

        // 2.执行查询SQL(根据数据源类型)
        // 获取数据库连接信息
        Map<String, ConnectBasicInfo> dataSourceInfo = getDataSourceInfo(Lists.newArrayList(apiCreateConfigDefinitionVO.getDataSourceName()));
        ConnectBasicInfo connectBasicInfo = dataSourceInfo.get(apiCreateConfigDefinitionVO.getDataSourceName());

        List<Map<String, Object>> searchData = null;
        if (ConnTypeEnum.MYSQL.getName().equalsIgnoreCase(connectType)) {
            // mysql 执行sql获取查询结果集
            searchData = new MysqlSqlExecutor(connectBasicInfo, dataBaseName, reqParams, resParaMap)
                    .mysqlExecuteSQL(tableName, null, mappingParams).searchData();
        } else if (ConnTypeEnum.HIVE.getName().equalsIgnoreCase(connectType)) {
            // hive 执行sql获取查询结果集
        }

        return DebugApiResultVO.builder().resultData(searchData).build();
    }

    /**
     * 获取数据源连接信息
     *
     * @param dataSourceNames
     * @return
     */
    private Map<String, ConnectBasicInfo> getDataSourceInfo(List<String> dataSourceNames ) {
        Map<String, ConnectBasicInfo> dataSourceMap = null;
        try {
            dataSourceMap = dataBaseInfoDefaultApi.getDataSourceMap(dataSourceNames);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("连接数据源服务失败!!!");
        }
        if (dataSourceMap.size() > 0) {
            return dataSourceMap;
        } else {
            throw new BizException("未获取到对应数据源连接信息!!!");
        }
    }

}
