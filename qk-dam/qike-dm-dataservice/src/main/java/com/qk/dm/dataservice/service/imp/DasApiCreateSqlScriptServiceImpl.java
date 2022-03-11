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
import com.qk.dm.dataservice.constant.ApiTypeEnum;
import com.qk.dm.dataservice.constant.CreateSqlRequestParamHeaderInfoEnum;
import com.qk.dm.dataservice.entity.*;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateSqlScriptMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateSqlScriptRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiCreateSqlScriptService;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据服务_新建API_脚本方式
 *
 * @author wjq
 * @date 20210830
 * @since 1.0.0
 */
@Service
public class DasApiCreateSqlScriptServiceImpl implements DasApiCreateSqlScriptService {

    private static final QDasApiCreateSqlScript qDasApiCreateSqlScript = QDasApiCreateSqlScript.dasApiCreateSqlScript;

    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;
    private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;

    @Autowired
    public DasApiCreateSqlScriptServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService,
            DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository,
            DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiCreateSqlScriptRepository = dasApiCreateSqlScriptRepository;
        this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
    }

    @Override
    public DasApiCreateSqlScriptVO detail(DasApiBasicInfo dasApiBasicInfo,DasApiCreateSqlScript dasApiCreateSqlScript) {
        DasApiCreateSqlScriptVO apiCreateSqlScriptVO = DasApiCreateSqlScriptVO.builder().build();

        // API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(dasApiBasicInfo);
        apiCreateSqlScriptVO.setApiBasicInfoVO(dasApiBasicInfoVO);

        // 新建API脚本方式,配置信息
        DasApiCreateSqlScriptDefinitionVO apiCreateSqlScriptDefinitionVO =
                DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScriptDefinitionVO(dasApiCreateSqlScript);
        // 新建API配置信息,设置请求/响应/排序参数VO转换对象
        setDasApiCreateSqlScriptVOParams(dasApiCreateSqlScript, apiCreateSqlScriptDefinitionVO);
        apiCreateSqlScriptVO.setApiCreateDefinitionVO(apiCreateSqlScriptDefinitionVO);

        return apiCreateSqlScriptVO;
    }

    /**
     * 入参定义
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

    private void setDasApiCreateSqlScriptVOParams(DasApiCreateSqlScript dasApiCreateSqlScript,
                                                  DasApiCreateSqlScriptDefinitionVO apiCreateSqlScriptDefinitionVO) {
        //入参
        if (null != dasApiCreateSqlScript.getApiRequestParas() && dasApiCreateSqlScript.getApiRequestParas().length() > 0) {
            apiCreateSqlScriptDefinitionVO.setApiCreateSqlRequestParasVOS(
                    GsonUtil.fromJsonString(dasApiCreateSqlScript.getApiRequestParas(),
                            new TypeToken<List<DasApiCreateRequestParasVO>>() {
                            }.getType()));
        }

        //响应
//        if (null != dasApiCreateSqlScript.getApiResponseParas()
//                && dasApiCreateSqlScript.getApiResponseParas().length() > 0) {
//            dasApiCreateSqlScriptVO.setApiCreateSqlRequestParasVOS(
//                    GsonUtil.fromJsonString(dasApiCreateSqlScript.getApiResponseParas(),
//                            new TypeToken<List<DasApiCreateResponseParasVO>>() {}.getType()));
//        }
        //排序
        if (null != dasApiCreateSqlScript.getApiOrderParas() && dasApiCreateSqlScript.getApiOrderParas().length() > 0) {
            apiCreateSqlScriptDefinitionVO.setApiCreateOrderParasVOS(
                    GsonUtil.fromJsonString(dasApiCreateSqlScript.getApiResponseParas(),
                            new TypeToken<List<DasApiCreateOrderParasVO>>() {
                            }.getType()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateSqlScriptVO.getApiBasicInfoVO();
        if (dasApiBasicInfoVO == null) {
            throw new BizException("当前新增的API所对应的基础信息为空!!!");
        }
        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.CREATE_API.getCode());
        dasApiBasicInfoService.insert(dasApiBasicInfoVO);

        // 保存新建API信息
        DasApiCreateSqlScriptDefinitionVO apiCreateSqlScriptDefinitionVO = dasApiCreateSqlScriptVO.getApiCreateDefinitionVO();
        DasApiCreateSqlScript apiCreateSqlScript = DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScript(apiCreateSqlScriptDefinitionVO);

        // 新建API设置配置参数
        apiCreateSqlScript.setApiRequestParas(
                GsonUtil.toJsonString(apiCreateSqlScriptDefinitionVO.getApiCreateSqlRequestParasVOS()));
//        dasApiCreateConfig.setApiResponseParas(
//                GsonUtil.toJsonString(dasApiCreateSqlScriptVO.getApiCreateResponseParasVOS()));
        apiCreateSqlScript.setApiOrderParas(
                GsonUtil.toJsonString(apiCreateSqlScriptDefinitionVO.getApiCreateOrderParasVOS()));

        apiCreateSqlScript.setApiId(apiId);
        apiCreateSqlScript.setGmtCreate(new Date());
        apiCreateSqlScript.setGmtModified(new Date());
        apiCreateSqlScript.setDelFlag(0);
        dasApiCreateSqlScriptRepository.save(apiCreateSqlScript);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
        // 更新API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateSqlScriptVO.getApiBasicInfoVO();
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.CREATE_API.getCode());
        dasApiBasicInfoService.update(dasApiBasicInfoVO);
        // 更新新建API
        DasApiCreateSqlScriptDefinitionVO apiCreateSqlScriptDefinitionVO = dasApiCreateSqlScriptVO.getApiCreateDefinitionVO();
        DasApiCreateSqlScript apiCreateSqlScript = DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScript(apiCreateSqlScriptDefinitionVO);

        // 新建API设置配置参数
        apiCreateSqlScript.setApiRequestParas(
                GsonUtil.toJsonString(apiCreateSqlScriptDefinitionVO.getApiCreateSqlRequestParasVOS()));
//        dasApiCreate.setApiResponseParas(
//                GsonUtil.toJsonString(dasApiCreateSqlScriptVO.getApiCreateResponseParasVOS()));
        apiCreateSqlScript.setApiOrderParas(
                GsonUtil.toJsonString(apiCreateSqlScriptDefinitionVO.getApiCreateOrderParasVOS()));

        apiCreateSqlScript.setGmtModified(new Date());
        apiCreateSqlScript.setDelFlag(0);
        Predicate predicate = qDasApiCreateSqlScript.apiId.eq(apiCreateSqlScript.getApiId());
        boolean exists = dasApiCreateSqlScriptRepository.exists(predicate);
        if (exists) {
            dasApiCreateSqlScriptRepository.saveAndFlush(apiCreateSqlScript);
        } else {
            throw new BizException("当前要新增的API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
        }
    }

    @Override
    public LinkedList<Map<String, Object>> getParamHeaderInfo() {
        return CreateSqlRequestParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public DebugApiResultVO debugModel(DasApiCreateSqlScriptVO apiCreateSqlScriptVO) {
        // 1.生成查询SQL(根据数据源类型)
        //数据源连接类型
        DasApiCreateSqlScriptDefinitionVO apiCreateSqlScriptDefinitionVO = apiCreateSqlScriptVO.getApiCreateDefinitionVO();

        // 真实请求参数(SQL where条件,使用字段参数)
        Map<String, String> reqParams = apiCreateSqlScriptVO.getDebugApiParasVOS().stream()
                .collect(Collectors.toMap(DebugApiParasVO::getParaName, DebugApiParasVO::getValue));

        // 2.执行查询SQL(根据数据源类型)
        // 获取数据库连接信息
        Map<String, ConnectBasicInfo> dataSourceInfo = dataBaseInfoDefaultApi
                .getDataSourceMap(Lists.newArrayList(apiCreateSqlScriptDefinitionVO.getDataSourceName()));
        ConnectBasicInfo connectBasicInfo = dataSourceInfo.get(apiCreateSqlScriptDefinitionVO.getDataSourceName());

        //执行SQL 查询数据
        List<Map<String, Object>> searchData = getSearchData(apiCreateSqlScriptDefinitionVO, reqParams, connectBasicInfo);
        return DebugApiResultVO.builder().resultData(searchData).build();
    }

    /**
     *
     *
     * @param apiCreateSqlScriptDefinitionVO
     * @param reqParams
     * @param connectBasicInfo
     * @return
     */
    private List<Map<String, Object>> getSearchData(DasApiCreateSqlScriptDefinitionVO apiCreateSqlScriptDefinitionVO,
                                                    Map<String, String> reqParams,
                                                    ConnectBasicInfo connectBasicInfo) {
        List<Map<String, Object>> searchData = null;

        // schema
        String connectType = apiCreateSqlScriptDefinitionVO.getConnectType();
        // 数据库
        String dataBaseName = apiCreateSqlScriptDefinitionVO.getDataBaseName();
        // SQL片段
        String sqlPara = apiCreateSqlScriptDefinitionVO.getSqlPara();

        if (ConnTypeEnum.MYSQL.getName().equalsIgnoreCase(connectType)) {
            // mysql 执行sql获取查询结果集
            searchData = new MysqlSqlExecutor(connectBasicInfo, dataBaseName, reqParams, null)
                    .mysqlExecuteSQL(null, sqlPara, null).searchDataSqlPara();
        } else if (ConnTypeEnum.HIVE.getName().equalsIgnoreCase(connectType)) {
            // hive 执行sql获取查询结果集
            searchData = new HiveSqlExecutor(connectBasicInfo, dataBaseName, reqParams, null)
                    .hiveExecuteSQL(null, sqlPara, null).searchDataSqlPara();
        }
        return searchData;
    }

}
