package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.dataservice.biz.MysqlSqlExecutor;
import com.qk.dm.dataservice.constant.CreateSqlRequestParamHeaderInfoEnum;
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

    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
    private static final QDasApiCreateSqlScript qDasApiCreateSqlScript = QDasApiCreateSqlScript.dasApiCreateSqlScript;

    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;
    private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;

    @Autowired
    public DasApiCreateSqlScriptServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService,
            DasApiBasicInfoRepository dasApiBasicinfoRepository,
            DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository,
            DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.dasApiCreateSqlScriptRepository = dasApiCreateSqlScriptRepository;
        this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
    }

    @Override
    public DasApiCreateSqlScriptVO detail(String apiId) {
        // 获取API基础信息
        Optional<DasApiBasicInfo> onDasApiBasicInfo = dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
        if (onDasApiBasicInfo.isEmpty()) {
            throw new BizException("查询不到对应的API基础信息!!!");
        }
        // 获取注册API信息
        Optional<DasApiCreateSqlScript> onDasApiCreateSqlScript = dasApiCreateSqlScriptRepository.findOne(qDasApiCreateSqlScript.apiId.eq(apiId));

        if (onDasApiCreateSqlScript.isEmpty()) {
            //入参定义
            DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
            return DasApiCreateSqlScriptVO.builder().apiBasicInfoVO(dasApiBasicInfoVO).build();
        }

        DasApiCreateSqlScriptVO apiCreateSqlScriptVO = DasApiCreateSqlScriptVO.builder().build();

        // API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
        apiCreateSqlScriptVO.setApiBasicInfoVO(dasApiBasicInfoVO);

        // 新建API脚本方式,配置信息
        DasApiCreateSqlScriptDefinitionVO apiCreateSqlScriptDefinitionVO =
                DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScriptDefinitionVO(onDasApiCreateSqlScript.get());
        // 新建API配置信息,设置请求/响应/排序参数VO转换对象
        setDasApiCreateSqlScriptVOParams(onDasApiCreateSqlScript.get(), apiCreateSqlScriptDefinitionVO);
        apiCreateSqlScriptVO.setApiCreateDefinitionVO(apiCreateSqlScriptDefinitionVO);

        return apiCreateSqlScriptVO;
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

    /**
     * 入参定义
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
    public void insert(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateSqlScriptVO.getApiBasicInfoVO();
        if (dasApiBasicInfoVO == null) {
            throw new BizException("当前新增的API所对应的基础信息为空!!!");
        }
        dasApiBasicInfoVO.setApiId(apiId);
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
        String connectType = apiCreateSqlScriptDefinitionVO.getConnectType();
        //数据库
        String dataBaseName = apiCreateSqlScriptDefinitionVO.getDataBaseName();
        // 真实请求参数(SQL where条件,使用字段参数)
        Map<String, String> reqParams = apiCreateSqlScriptVO.getDebugApiParasVOS().stream()
                .collect(Collectors.toMap(DebugApiParasVO::getParaName, DebugApiParasVO::getValue));
        //执行SQL片段
        String sqlPara = apiCreateSqlScriptDefinitionVO.getSqlPara();

        // 2.执行查询SQL(根据数据源类型)
        // 获取数据库连接信息
        Map<String, ConnectBasicInfo> dataSourceInfo = dataBaseInfoDefaultApi
                .getDataSourceMap(Lists.newArrayList(apiCreateSqlScriptDefinitionVO.getDataSourceName()));
        ConnectBasicInfo connectBasicInfo = dataSourceInfo.get(apiCreateSqlScriptDefinitionVO.getDataSourceName());

        List<Map<String, Object>> searchData = null;
        if (ConnTypeEnum.MYSQL.getName().equalsIgnoreCase(connectType)) {
            // mysql 执行sql获取查询结果集
            searchData = new MysqlSqlExecutor(connectBasicInfo, dataBaseName, reqParams, null)
                    .mysqlExecuteSQL(null, sqlPara,null).searchDataSqlPara();
        } else if (ConnTypeEnum.HIVE.getName().equalsIgnoreCase(connectType)) {
            // hive 执行sql获取查询结果集
        }
        return DebugApiResultVO.builder().resultData(searchData).build();
    }

}
