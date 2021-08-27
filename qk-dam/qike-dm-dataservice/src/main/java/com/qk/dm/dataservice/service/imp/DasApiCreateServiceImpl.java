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
import com.qk.dm.dataservice.entity.DasApiCreate;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiCreate;
import com.qk.dm.dataservice.feign.DataSourceFeign;
import com.qk.dm.dataservice.feign.MetaDataFeign;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiCreateService;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wjq
 * @date 2021/8/23 17:26
 * @since 1.0.0
 */
@Service
public class DasApiCreateServiceImpl implements DasApiCreateService {

    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
    private static final QDasApiCreate qDasApiCreate = QDasApiCreate.dasApiCreate;

    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiCreateRepository dasApiCreateRepository;

    private final DataSourceFeign dataSourceFeign;
    private final MetaDataFeign metaDataFeign;

    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Autowired
    public DasApiCreateServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService,
            DasApiBasicInfoRepository dasApiBasicinfoRepository,
            DasApiCreateRepository dasApiCreateRepository, DataSourceFeign dataSourceFeign,
            MetaDataFeign metaDataFeign,
            EntityManager entityManager) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.dasApiCreateRepository = dasApiCreateRepository;
        this.dataSourceFeign = dataSourceFeign;
        this.metaDataFeign = metaDataFeign;
        this.entityManager = entityManager;
    }

    @Override
    public DasApiCreateVO getDasApiCreateInfoByApiId(String apiId) {
        // 获取API基础信息
        Optional<DasApiBasicInfo> onDasApiBasicInfo =
                dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
        if (onDasApiBasicInfo.isEmpty()) {
            throw new BizException("查询不到对应的API基础信息!!!");
        }
        // 获取注册API信息
        Optional<DasApiCreate> onDasApiCreate =
                dasApiCreateRepository.findOne(qDasApiCreate.apiId.eq(apiId));
        if (onDasApiCreate.isEmpty()) {
            DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
            return DasApiCreateVO.builder().dasApiBasicInfoVO(dasApiBasicInfoVO).build();
        }

        DasApiCreate dasApiCreate = onDasApiCreate.get();
        DasApiCreateVO dasApiCreateVO =
                DasApiCreateMapper.INSTANCE.useDasApiCreateVO(
                        onDasApiCreate.get());
        // API基础信息,设置入参定义VO转换对象
        DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo);
        dasApiCreateVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
        // 新建API配置信息,设置请求/响应/排序参数VO转换对象
        setDasApiCreateVOParams(dasApiCreate, dasApiCreateVO);
        return dasApiCreateVO;
    }

    private void setDasApiCreateVOParams(DasApiCreate dasApiCreate, DasApiCreateVO dasApiCreateVO) {
        if (null != dasApiCreate.getApiRequestParas()
                && dasApiCreate.getApiRequestParas().length() > 0) {
            dasApiCreateVO.setApiCreateRequestParasVOS(
                    GsonUtil.fromJsonString(
                            dasApiCreate.getApiRequestParas(),
                            new TypeToken<List<DasApiCreateRequestParasVO>>() {
                            }.getType()));
        }
        if (null != dasApiCreate.getApiResponseParas()
                && dasApiCreate.getApiResponseParas().length() > 0) {
            dasApiCreateVO.setApiCreateResponseParasVOS(
                    GsonUtil.fromJsonString(
                            dasApiCreate.getApiResponseParas(),
                            new TypeToken<List<DasApiCreateResponseParasVO>>() {
                            }.getType()));
        }
        if (null != dasApiCreate.getApiOrderParas()
                && dasApiCreate.getApiOrderParas().length() > 0) {
            dasApiCreateVO.setApiCreateOrderParasVOS(
                    GsonUtil.fromJsonString(
                            dasApiCreate.getApiResponseParas(),
                            new TypeToken<List<DasApiCreateOrderParasVO>>() {
                            }.getType()));
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
                            new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
                            }.getType()));
        }
        return dasApiBasicInfoVO;
    }

    @Transactional
    @Override
    public void addDasApiCreate(DasApiCreateVO dasApiCreateVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateVO.getDasApiBasicInfoVO();
        if (dasApiBasicInfoVO == null) {
            throw new BizException("当前新增的API所对应的基础信息为空!!!");
        }
        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoService.addDasApiBasicInfo(dasApiBasicInfoVO);

        // 保存新建API信息
        DasApiCreate dasApiCreate =
                DasApiCreateMapper.INSTANCE.useDasApiCreate(dasApiCreateVO);

        // 新建API设置配置参数
        dasApiCreate.setApiRequestParas(
                GsonUtil.toJsonString(dasApiCreateVO.getApiCreateRequestParasVOS()));
        dasApiCreate.setApiResponseParas(
                GsonUtil.toJsonString(dasApiCreateVO.getApiCreateResponseParasVOS()));
        dasApiCreate.setApiOrderParas(
                GsonUtil.toJsonString(dasApiCreateVO.getApiCreateOrderParasVOS()));

        dasApiCreate.setApiId(apiId);
        dasApiCreate.setGmtCreate(new Date());
        dasApiCreate.setGmtModified(new Date());
        dasApiCreate.setDelFlag(0);
        dasApiCreateRepository.save(dasApiCreate);
    }

    @Transactional
    @Override
    public void updateDasApiCreate(DasApiCreateVO dasApiCreateVO) {
        // 更新API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateVO.getDasApiBasicInfoVO();
        dasApiBasicInfoService.updateDasApiBasicInfo(dasApiBasicInfoVO);
        // 更新新建API
        DasApiCreate dasApiCreate =
                DasApiCreateMapper.INSTANCE.useDasApiCreate(dasApiCreateVO);

        // 新建API设置配置参数
        dasApiCreate.setApiRequestParas(
                GsonUtil.toJsonString(dasApiCreateVO.getApiCreateRequestParasVOS()));
        dasApiCreate.setApiResponseParas(
                GsonUtil.toJsonString(dasApiCreateVO.getApiCreateResponseParasVOS()));
        dasApiCreate.setApiOrderParas(
                GsonUtil.toJsonString(dasApiCreateVO.getApiCreateOrderParasVOS()));

        dasApiCreate.setGmtModified(new Date());
        dasApiCreate.setDelFlag(0);
        Predicate predicate = qDasApiCreate.apiId.eq(dasApiCreate.getApiId());
        boolean exists = dasApiCreateRepository.exists(predicate);
        if (exists) {
            dasApiCreateRepository.saveAndFlush(dasApiCreate);
        } else {
            throw new BizException(
                    "当前要新增的注册API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
        }
    }

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

    @Override
    public List<String> getAllDataBase(String dbType) {
        String type = dbType.split("-")[1];
        DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult = metaDataFeign.mtdDetail(MtdApiParams.builder().typeName(type + "_db").build());
        List<MtdApiDb> mtdApiDbs = mtdApiDefaultCommonResult.getData().getEntities();
        return mtdApiDbs.stream().map(MtdApiDb::getDisplayText).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTable(String dbType, String server, String dbName) {
        String type = dbType.split("-")[1];
        DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult = metaDataFeign.mtdDetail(
                MtdApiParams.builder().typeName(type + "_db").server(server).dbName(dbName).build());
        List<MtdTables> mtdTablesList = mtdApiDefaultCommonResult.getData().getTables();
        return mtdTablesList.stream().map(MtdTables::getDisplayText).collect(Collectors.toList());
    }

    @Override
    public List getAllColumn(String dbType, String server, String dbName, String tableName) {
        String type = dbType.split("-")[1];
        DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult = metaDataFeign.mtdDetail(
                MtdApiParams.builder().typeName(type + "_table").server(server).dbName(dbName).tableName(tableName).build());
        return mtdApiDefaultCommonResult.getData().getColumns();
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
        ResultDatasourceInfo resultDatasourceInfo = dataSourceFeign.getResultDataSourceByConnectName(connectName).getData();
        ConnectBasicInfo connectInfo = ConnectInfoConvertUtils.getConnectInfo(resultDatasourceInfo.getDbType(), resultDatasourceInfo.getConnectBasicInfoJson());
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
}
