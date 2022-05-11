package com.qk.dm.dataservice.service.imp;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.sqlparser.JSqlParserUtil;
import com.qk.dm.dataservice.constant.*;
import com.qk.dm.dataservice.entity.*;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateMybatisSqlScriptMapper;
import com.qk.dm.dataservice.repositories.DasApiCreateMybatisSqlScriptRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasDataQueryInfoService;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 提供数据查询服务配置信息
 *
 * @author zhudaoming
 * @date 20220420
 * @since 1.5
 */
@Service
@RequiredArgsConstructor
public class DasDataQueryInfoServiceImpl implements DasDataQueryInfoService {
    private final static QDasApiCreateMybatisSqlScript qDasApiCreateMybatisSqlScript =
            QDasApiCreateMybatisSqlScript.dasApiCreateMybatisSqlScript;
    
    private final JPAQueryFactory jpaQueryFactory;
    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiCreateMybatisSqlScriptRepository mybatisSqlScriptRepository;

    @Override
    public List<DataQueryInfoVO> dataQueryInfo() {
        BooleanExpression whereCondition =
                qDasApiCreateMybatisSqlScript
                        .accessMethod
                        .eq(CreateTypeEnum.CREATE_API_MYBATIS_SQL_SCRIPT_TYPE.getCode())
                        .and(qDasApiCreateMybatisSqlScript.delFlag.eq(0))
                        .and(QDasApiBasicInfo.dasApiBasicInfo.delFlag.eq(0));
        return dataQueryInfo(whereCondition);
    }

    @Override
    public List<DataQueryInfoVO> dataQueryInfo(Long id) {
        BooleanExpression whereCondition =
                qDasApiCreateMybatisSqlScript
                        .id
                        .eq(id)
                        .and(
                                qDasApiCreateMybatisSqlScript.accessMethod.eq(
                                        CreateTypeEnum.CREATE_API_MYBATIS_SQL_SCRIPT_TYPE.getCode()))
                        .and(qDasApiCreateMybatisSqlScript.delFlag.eq(0))
                        .and(QDasApiBasicInfo.dasApiBasicInfo.delFlag.eq(0));
        return dataQueryInfo(whereCondition);
    }

    @Override
    public List<DataQueryInfoVO> dataQueryInfoLast(Long time) {
        BooleanExpression whereCondition =
                qDasApiCreateMybatisSqlScript
                        .gmtModified
                        .gt(new Date(time))
                        .and(
                                qDasApiCreateMybatisSqlScript.accessMethod.eq(
                                        CreateTypeEnum.CREATE_API_MYBATIS_SQL_SCRIPT_TYPE.getCode()))
                        .and(qDasApiCreateMybatisSqlScript.delFlag.eq(0))
                        .and(QDasApiBasicInfo.dasApiBasicInfo.delFlag.eq(0))
                ;
        return dataQueryInfo(whereCondition);
    }

    private List<DataQueryInfoVO> dataQueryInfo(BooleanExpression whereCondition) {
        return jpaQueryFactory
                .select(qDasApiCreateMybatisSqlScript, QDasApiBasicInfo.dasApiBasicInfo)
                .from(qDasApiCreateMybatisSqlScript)
                .leftJoin(QDasApiBasicInfo.dasApiBasicInfo)
                .on(
                        qDasApiCreateMybatisSqlScript.apiId.eq(
                                QDasApiBasicInfo.dasApiBasicInfo.apiId))
                .where(whereCondition)
                .fetch()
                .stream()
                .map(
                        tuple ->
                                new DataQueryInfoVO(
                                        DasApiCreateMybatisSqlScriptMapper.INSTANCE.useDasApiCreateMybatisSqlScriptDefinitionVO(
                                                tuple.get(qDasApiCreateMybatisSqlScript)),
                                        DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(
                                                tuple.get(QDasApiBasicInfo.dasApiBasicInfo))))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DataQueryInfoVO dataQueryInfoVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dataQueryInfoVO.getDasApiBasicInfo();
        Objects.requireNonNull(dasApiBasicInfoVO, "当前新增的API所对应的基础信息为空!!!");

        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.CREATE_API.getCode());
        dasApiBasicInfoService.insert(dasApiBasicInfoVO);

        // 保存新建API信息
        DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO =
                dataQueryInfoVO.getDasApiCreateMybatisSqlScript();
        DasApiCreateMybatisSqlScript mybatisSqlScript =
                DasApiCreateMybatisSqlScriptMapper.INSTANCE.useDasApiCreateMybatisSqlScript(mybatisSqlScriptDefinitionVO);

        // 新建API设置配置参数
        setParamsJson(mybatisSqlScriptDefinitionVO, mybatisSqlScript);

        mybatisSqlScript.setApiId(apiId);
        mybatisSqlScript.setGmtCreate(new Date());
        mybatisSqlScript.setGmtModified(new Date());
        mybatisSqlScript.setDelFlag(0);
        mybatisSqlScriptRepository.save(mybatisSqlScript);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DataQueryInfoVO dataQueryInfoVO) {
        // 更新API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dataQueryInfoVO.getDasApiBasicInfo();
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.CREATE_API.getCode());
        dasApiBasicInfoService.update(dasApiBasicInfoVO);
        // 更新新建API
        DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO =
                dataQueryInfoVO.getDasApiCreateMybatisSqlScript();
        DasApiCreateMybatisSqlScript mybatisSqlScript =
                DasApiCreateMybatisSqlScriptMapper.INSTANCE.useDasApiCreateMybatisSqlScript(mybatisSqlScriptDefinitionVO);

        // 新建API设置配置参数
        setParamsJson(mybatisSqlScriptDefinitionVO, mybatisSqlScript);

        mybatisSqlScript.setGmtModified(new Date());
        mybatisSqlScript.setDelFlag(0);
        Predicate predicate = qDasApiCreateMybatisSqlScript.apiId.eq(mybatisSqlScript.getApiId());
        boolean exists = mybatisSqlScriptRepository.exists(predicate);
        if (exists) {
            mybatisSqlScriptRepository.saveAndFlush(mybatisSqlScript);
        } else {
            throw new BizException("当前要新增的API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
        }

    }

    @Override
    public Object generateResponseParam(String sqlPara) {
        sqlPara = JSqlParserUtil.PATTERN_PATH_VAR.matcher(sqlPara).replaceAll("1");
        sqlPara = JSqlParserUtil.PATTERN_PATH_VAR_TAG.matcher(sqlPara).replaceAll("1");

        System.out.println("sqlPara===>"+sqlPara);
        return JSqlParserUtil.selectBody(sqlPara);
    }

    @Override
    public Object detail(DasApiBasicInfo dasApiBasicInfo, DasApiCreateMybatisSqlScript createMybatisSqlScript) {
        DataQueryInfoVO dataQueryInfoVO = DataQueryInfoVO.builder().build();

        // API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(dasApiBasicInfo);
        dasApiBasicInfoService.setDelInputParamVO(dasApiBasicInfo, dasApiBasicInfoVO);
        dataQueryInfoVO.setDasApiBasicInfo(dasApiBasicInfoVO);

        // 新建API脚本方式,配置信息
        DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO =
                DasApiCreateMybatisSqlScriptMapper.INSTANCE.useDasApiCreateMybatisSqlScriptDefinitionVO(createMybatisSqlScript);

        // 新建API配置信息,设置请求/响应/排序参数VO转换对象
        setParamsVO(createMybatisSqlScript, mybatisSqlScriptDefinitionVO);
        dataQueryInfoVO.setDasApiCreateMybatisSqlScript(mybatisSqlScriptDefinitionVO);

        return dataQueryInfoVO;
    }

    @Override
    public LinkedList<Map<String, Object>> paramHeaderInfo() {
        return CreateSqlRequestParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public LinkedList<Map<String, Object>> responseParamHeaderInfo() {
        return CreateResponseParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public LinkedList<Map<String, Object>> orderParamHeaderInfo() {
        return CreateOrderParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public Map<Boolean, String> pageFlags() {
        return MybatisSqlPageFlagEnum.getAllValue();
    }

    @Override
    public Map<Integer, String> cacheLevels() {
        return MybatisSqlCacheLevelEnum.getAllValue();
    }

    private void setParamsVO(DasApiCreateMybatisSqlScript createMybatisSqlScript,
                             DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO) {
        // 请求参数
        if (Objects.nonNull(createMybatisSqlScript.getApiRequestParas())) {
            mybatisSqlScriptDefinitionVO.setApiCreateSqlRequestParasVOS(
                    GsonUtil.fromJsonString(createMybatisSqlScript.getApiRequestParas(), new TypeToken<List<DasApiCreateRequestParasVO>>() {
                    }.getType()));
        }

        // 响应参数
        if (Objects.nonNull(createMybatisSqlScript.getApiResponseParas())) {
            mybatisSqlScriptDefinitionVO.setApiCreateResponseParasVOS(
                    GsonUtil.fromJsonString(createMybatisSqlScript.getApiResponseParas(), new TypeToken<List<DasApiCreateResponseParasVO>>() {
                    }.getType()));
        }

        // 排序参数
//        if (Objects.nonNull(createMybatisSqlScript.getApiOrderParas())) {
//            mybatisSqlScriptDefinitionVO.setApiCreateOrderParasVOS(
//                    GsonUtil.fromJsonString(createMybatisSqlScript.getApiOrderParas(), new TypeToken<List<DasApiCreateOrderParasVO>>() {
//                    }.getType()));
//        }
    }

    private void setParamsJson(DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO,
                               DasApiCreateMybatisSqlScript mybatisSqlScript) {
        // 请求参数
        if (Objects.nonNull(mybatisSqlScriptDefinitionVO.getApiCreateSqlRequestParasVOS())) {
            mybatisSqlScript.setApiRequestParas(
                    GsonUtil.toJsonString(mybatisSqlScriptDefinitionVO.getApiCreateSqlRequestParasVOS()));
        }

        // 响应参数
        if (Objects.nonNull(mybatisSqlScriptDefinitionVO.getApiCreateResponseParasVOS())) {
            mybatisSqlScript.setApiResponseParas(
                    GsonUtil.toJsonString(mybatisSqlScriptDefinitionVO.getApiCreateResponseParasVOS()));
        }

        // 排序参数
//        if (Objects.nonNull(mybatisSqlScriptDefinitionVO.getApiCreateOrderParasVOS())) {
//            mybatisSqlScript.setApiOrderParas(
//                    GsonUtil.toJsonString(mybatisSqlScriptDefinitionVO.getApiCreateOrderParasVOS()));
//        }
    }

}
