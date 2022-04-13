package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataservice.biz.HiveSqlExecutor;
import com.qk.dm.dataservice.biz.MysqlSqlExecutor;
import com.qk.dm.dataservice.constant.*;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateConfig;
import com.qk.dm.dataservice.entity.QDasApiCreateConfig;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateConfigMapper;
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

    private static final QDasApiCreateConfig qDasApiCreateConfig = QDasApiCreateConfig.dasApiCreateConfig;


    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;
    private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;


    @Autowired
    public DasApiCreateConfigServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService,
            DasApiCreateConfigRepository dasApiCreateConfigRepository,
            DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
        this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
    }

    @Override
    public DasApiCreateConfigVO detail(DasApiBasicInfo dasApiBasicInfo, DasApiCreateConfig dasApiCreateConfig) {
        //构建新建API配置方式
        DasApiCreateConfigVO dasApiCreateConfigVO = DasApiCreateConfigVO.builder().build();
        // API基础信息,设置入参定义VO转换对象
        DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(dasApiBasicInfo);
        dasApiCreateConfigVO.setApiBasicInfoVO(dasApiBasicInfoVO);
        // 新建API配置方式,配置信息VO转换
        DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO = DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfigDefinitionVO(dasApiCreateConfig);
        // 新建API配置信息,设置请求/响应/排序参数VO转换对象
        setDasApiCreateVOParams(dasApiCreateConfig, apiCreateConfigDefinitionVO);
        dasApiCreateConfigVO.setApiCreateDefinitionVO(apiCreateConfigDefinitionVO);
        //设置DEBUG调试参数
        dasApiCreateConfigVO.setDebugApiParasVOS(Lists.newArrayList());

        return dasApiCreateConfigVO;
    }

    /**
     * 新建API配置信息,设置请求/响应/排序参数VO转换对象
     *
     * @param dasApiCreateConfig
     * @param apiCreateConfigDefinitionVO
     */
    private void setDasApiCreateVOParams(DasApiCreateConfig dasApiCreateConfig, DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO) {
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
                    GsonUtil.fromJsonString(dasApiCreateConfig.getApiOrderParas(),
                            new TypeToken<List<DasApiCreateOrderParasVO>>() {
                            }.getType()));
        }
    }

    /**
     * API基础信息,设置入参定义VO转换对象
     *
     * @param dasApiBasicInfo
     * @return
     */
    @Override
    public DasApiBasicInfoVO setDasApiBasicInfoDelInputParam(DasApiBasicInfo dasApiBasicInfo) {
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
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.CREATE_API.getCode());
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
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.CREATE_API.getCode());
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
    public Object debugModel(DasApiCreateConfigVO apiCreateConfigVO) {
        // 1.生成查询SQL(根据数据源类型)
        //数据源连接类型
        DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO = apiCreateConfigVO.getApiCreateDefinitionVO();
        // 新建API接口请求参数,定义入参(对应元数据映射关系)
        Map<String, List<DasApiCreateRequestParasVO>> mappingParams =
                apiCreateConfigDefinitionVO.getApiCreateRequestParasVOS().stream()
                        .collect(Collectors.groupingBy(DasApiCreateRequestParasVO::getParaName));

        // 真实请求参数(SQL where条件,使用字段参数)
        Map<String, String> reqParams = apiCreateConfigVO.getDebugApiParasVOS().stream()
                .collect(Collectors.toMap(
                        DebugApiParasVO::getParaName, DebugApiParasVO::getValue));

        // 响应参数(SQL返回值映射查询数据)
        List<DasApiCreateResponseParasVO> responseParas = apiCreateConfigDefinitionVO.getApiCreateResponseParasVOS();
        // 2.执行查询SQL(根据数据源类型)
        // 获取数据库连接信息
        ConnectBasicInfo connectBasicInfo = getConnectBasicInfo(apiCreateConfigDefinitionVO);
        //执行SQL 查询数据
        return getSearchData(apiCreateConfigDefinitionVO, mappingParams, reqParams, responseParas, connectBasicInfo);
    }

    /**
     * 执行SQL 查询数据
     *
     * @param apiCreateConfigDefinitionVO
     * @param mappingParams
     * @param reqParams
     * @param responseParas
     * @param connectBasicInfo
     * @return
     */
    private Object getSearchData(DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO,
                                 Map<String, List<DasApiCreateRequestParasVO>> mappingParams,
                                 Map<String, String> reqParams,
                                 List<DasApiCreateResponseParasVO> responseParas,
                                 ConnectBasicInfo connectBasicInfo) {
        Object resSearchData = null;
        //schema
        String connectType = apiCreateConfigDefinitionVO.getConnectType();
        //数据库
        String dataBaseName = apiCreateConfigDefinitionVO.getDataBaseName();
        //表名称
        String tableName = apiCreateConfigDefinitionVO.getTableName();
        // 排序查询,默认使用首个参数的排序方式
        String orderByStr = getOrderByParaSqlStr(apiCreateConfigDefinitionVO);

        List<Map<String, Object>> searchData = null;
        if (ConnTypeEnum.MYSQL.getName().equalsIgnoreCase(connectType)) {
            // mysql 执行sql获取查询结果集
            searchData = new MysqlSqlExecutor(connectBasicInfo, dataBaseName, reqParams, responseParas)
                    .mysqlExecuteSQL(tableName, null, mappingParams, orderByStr).searchData();
        } else if (ConnTypeEnum.HIVE.getName().equalsIgnoreCase(connectType)) {
            // hive 执行sql获取查询结果集
            searchData = new HiveSqlExecutor(connectBasicInfo, dataBaseName, reqParams, responseParas)
                    .hiveExecuteSQL(tableName, null, mappingParams).searchData();
        }

        // 是否为详情单表数据
        if (null != searchData && searchData.size() == 1) {
            resSearchData = searchData.stream().findFirst().get();
        } else {
            resSearchData = searchData;
        }
        return resSearchData;
    }

    /**
     * 排序SQL片段
     *
     * @param apiCreateConfigDefinitionVO
     * @return
     */
    private String getOrderByParaSqlStr(DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO) {
        String orderByStr = "";
        List<DasApiCreateOrderParasVO> orderParas = apiCreateConfigDefinitionVO.getApiCreateOrderParasVOS();
        if (null != orderParas && orderParas.size() > 0) {
            // 默认使用首个参数的排序方式
            DasApiCreateOrderParasVO orderParasVO = orderParas.stream().findFirst().get();
            String orderType = orderParasVO.getOrderType();
            if (CreateParamSortStyleEnum.ASC.getCode().equalsIgnoreCase(orderType) ||
                    CreateParamSortStyleEnum.DESC.getCode().equalsIgnoreCase(orderType)) {
                //排序字段
                List<String> orderCols = orderParas.stream().map(DasApiCreateOrderParasVO::getColumnName).collect(Collectors.toList());
                String orderColStr = String.join(",", orderCols);
                orderByStr += SqlExecuteUtils.ORDER_BY + orderColStr + " " + orderType;
            }
        }
        return orderByStr;
    }

    /**
     * 获取数据库连接信息
     *
     * @param apiCreateConfigDefinitionVO
     * @return
     */
    private ConnectBasicInfo getConnectBasicInfo(DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO) {
        Map<String, ConnectBasicInfo> dataSourceInfo = dataBaseInfoDefaultApi
                .getDataSourceMap(Lists.newArrayList(apiCreateConfigDefinitionVO.getDataSourceName()));
        ConnectBasicInfo connectBasicInfo = dataSourceInfo.get(apiCreateConfigDefinitionVO.getDataSourceName());
        return connectBasicInfo;
    }


}
