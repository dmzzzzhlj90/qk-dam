package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dam.sqlparser.JSqlParserUtil;
import com.qk.dm.dataquery.feign.DataBackendQueryFeign;
import com.qk.dm.dataservice.constant.*;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateMybatisSqlScript;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiCreateMybatisSqlScript;
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
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private final DataBackendQueryFeign dataBackendQueryFeign;
    ;

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
                        .and(QDasApiBasicInfo.dasApiBasicInfo.delFlag.eq(0));
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
                                        getMybatisSqlScriptDefinitionVO(tuple.get(qDasApiCreateMybatisSqlScript)),
                                        getApiBasicInfoVO(tuple.get(QDasApiBasicInfo.dasApiBasicInfo)),
                                        null))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DataQueryInfoVO dataQueryInfoVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dataQueryInfoVO.getApiBasicInfoVO();
        Objects.requireNonNull(dasApiBasicInfoVO, "当前新增的API所对应的基础信息为空!!!");

        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.CREATE_API.getCode());
        dasApiBasicInfoService.insert(dasApiBasicInfoVO);

        // 保存新建API信息
        DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO =
                dataQueryInfoVO.getApiCreateDefinitionVO();
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
        DasApiBasicInfoVO dasApiBasicInfoVO = dataQueryInfoVO.getApiBasicInfoVO();
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.CREATE_API.getCode());
        dasApiBasicInfoService.update(dasApiBasicInfoVO);
        // 更新新建API
        DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO =
                dataQueryInfoVO.getApiCreateDefinitionVO();
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
    public List<DasApiCreateResponseParasVO> generateResponseParam(String sqlPara) {
        List<DasApiCreateResponseParasVO> responseParasVOList = new ArrayList<>();

        sqlPara = JSqlParserUtil.PATTERN_PATH_VAR.matcher(sqlPara).replaceAll("1");
        sqlPara = JSqlParserUtil.PATTERN_PATH_VAR_TAG.matcher(sqlPara).replaceAll("1");

        System.out.println("sqlPara===>" + sqlPara);
        List<SelectItem> selectItems = JSqlParserUtil.selectBody(sqlPara);

        selectItems.forEach(selectItem -> {
            DasApiCreateResponseParasVO.DasApiCreateResponseParasVOBuilder builder = DasApiCreateResponseParasVO.builder();

            Alias alias = ((SelectExpressionItem) selectItem).getAlias();
            String aliasName = alias.getName();

            Column column = null;
            try {
                column = (Column) ((SelectExpressionItem) selectItem).getExpression();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String columnName;
            if (Objects.nonNull(column)) {
                columnName = column.getColumnName();
            } else {
                columnName = aliasName;
            }

            builder
                    .paraName(aliasName)
                    .mappingName(columnName)
                    .paraType(DasConstant.DAS_API_PARA_COL_TYPE_STRING)
                    .description(aliasName);
            responseParasVOList.add(builder.build());
        });

        return responseParasVOList;
    }

    @Override
    public Object detail(DasApiBasicInfo dasApiBasicInfo, DasApiCreateMybatisSqlScript createMybatisSqlScript) {
        DataQueryInfoVO dataQueryInfoVO = DataQueryInfoVO.builder().build();

        // API基础信息
        dataQueryInfoVO.setApiBasicInfoVO(
                getApiBasicInfoVO(dasApiBasicInfo));

        // 新建API脚本方式,配置信息
        dataQueryInfoVO.setApiCreateDefinitionVO(
                getMybatisSqlScriptDefinitionVO(createMybatisSqlScript));

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

    @Override
    public Object debugModel(DataQueryInfoVO dataQueryInfoVO) {
        // 请求参数解析
        Boolean pageFlag = dataQueryInfoVO.getApiCreateDefinitionVO().getPageFlag();
        Map<String, String> uriPathParam = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        Map<String, Object> body = Maps.newHashMap();

        HttpHeaders headers = null;

        DasApiBasicInfoVO apiBasicInfoVO = dataQueryInfoVO.getApiBasicInfoVO();
        List<DasApiBasicInfoRequestParasVO> basicInfoRequestParas = apiBasicInfoVO.getApiBasicInfoRequestParasVOS();
        Map<String, String> requestParaPositionMap = basicInfoRequestParas.stream()
                .collect(Collectors.toMap(
                        DasApiBasicInfoRequestParasVO::getParaName,
                        DasApiBasicInfoRequestParasVO::getParaPosition));

        List<DebugApiParasVO> debugApiParas = dataQueryInfoVO.getDebugApiParasVOS();

        requestParams(uriPathParam, params, body, requestParaPositionMap, debugApiParas);

        HttpDataParamModel httpDataParamModel = HttpDataParamModel.builder()
                .apiId(dataQueryInfoVO.getApiBasicInfoVO().getApiId())
                .uriPathParam(uriPathParam)
                .headers(headers)
                .body(body)
                .params(params)
                .method(
                        getRequestMethod(
                                dataQueryInfoVO.getApiBasicInfoVO().getRequestType()))
                .cacheLevel(dataQueryInfoVO.getApiCreateDefinitionVO().getCacheLevel())
                .pageFlag(pageFlag)
                .resultDataType(dataQueryInfoVO.getApiBasicInfoVO().getResultDataType())
                .pagination(
                        pagination(
                                pageInfo(debugApiParas, Pagination.PAGE_NUM),
                                pageInfo(debugApiParas, Pagination.PAGE_SIZE),
                                pageFlag))
                .build();

        return dataBackendQueryFeign
                .dataBackendQuery(httpDataParamModel);
    }

    private String pageInfo(List<DebugApiParasVO> debugApiParas, String flag) {
        return Objects.requireNonNull(
                debugApiParas.stream()
                        .filter(debugApiParasVO ->
                                debugApiParasVO.getParaName().equalsIgnoreCase(flag))
                        .findFirst()
                        .orElse(null)).getValue();
    }

    private DasApiBasicInfoVO getApiBasicInfoVO(DasApiBasicInfo dasApiBasicInfo) {
        DasApiBasicInfoVO dasApiBasicInfoVO = DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(dasApiBasicInfo);
        dasApiBasicInfoService.setDelInputParamVO(dasApiBasicInfo, dasApiBasicInfoVO);
        return dasApiBasicInfoVO;
    }

    private DasApiCreateMybatisSqlScriptDefinitionVO getMybatisSqlScriptDefinitionVO(DasApiCreateMybatisSqlScript createMybatisSqlScript) {
        DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO =
                DasApiCreateMybatisSqlScriptMapper.INSTANCE.useDasApiCreateMybatisSqlScriptDefinitionVO(createMybatisSqlScript);

        // 新建API配置信息,设置请求/响应/排序参数VO转换对象
        setParamsVO(createMybatisSqlScript, mybatisSqlScriptDefinitionVO);
        return mybatisSqlScriptDefinitionVO;
    }

    private void setParamsVO(DasApiCreateMybatisSqlScript createMybatisSqlScript,
                             DasApiCreateMybatisSqlScriptDefinitionVO mybatisSqlScriptDefinitionVO) {
        // 请求参数
        if (Objects.nonNull(createMybatisSqlScript.getApiRequestParas())) {
            mybatisSqlScriptDefinitionVO.setApiCreateSqlRequestParasVOS(
                    GsonUtil.fromJsonString(createMybatisSqlScript.getApiRequestParas(), new TypeToken<List<DasApiCreateSqlRequestParasVO>>() {
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

    private Map<String, String> getUriPathParam(DataQueryInfoVO dataQueryInfoVO) {
        Map<String, String> uriPathParam = Maps.newHashMap();

        DasApiBasicInfoVO apiBasicInfoVO = dataQueryInfoVO.getApiBasicInfoVO();
        List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOS = apiBasicInfoVO.getApiBasicInfoRequestParasVOS();
        Map<String, String> requestParaMap = basicInfoRequestParasVOS.stream()
                .collect(Collectors.toMap(DasApiBasicInfoRequestParasVO::getParaName, DasApiBasicInfoRequestParasVO::getParaPosition));

        DasApiCreateMybatisSqlScriptDefinitionVO createDefinitionVO = dataQueryInfoVO.getApiCreateDefinitionVO();
        List<DasApiCreateSqlRequestParasVO> requestParasVOS = createDefinitionVO.getApiCreateSqlRequestParasVOS();
        if (Objects.nonNull(requestParasVOS)) {
            requestParasVOS.forEach(requestParasVO -> {
                String paraName = requestParasVO.getParaName();

            });
        }

        return uriPathParam;
    }

    /**
     * 请求方法
     *
     * @param requestType
     * @return
     */
    private RequestMethod getRequestMethod(String requestType) {
        if (RequestMethodTypeEnum.GET.getValue().equalsIgnoreCase(requestType)) {
            return RequestMethod.GET;
        } else if (RequestMethodTypeEnum.POST.getValue().equalsIgnoreCase(requestType)) {
            return RequestMethod.POST;
        } else if (RequestMethodTypeEnum.PUT.getValue().equalsIgnoreCase(requestType)) {
            return RequestMethod.PUT;
        } else if (RequestMethodTypeEnum.DELETE.getValue().equalsIgnoreCase(requestType)) {
            return RequestMethod.DELETE;
        } else {
            return RequestMethod.GET;
        }
    }

    /**
     * 请求参数
     *
     * @param uriPathParam
     * @param params
     * @param body
     * @param requestParaPositionMap
     * @param debugApiParas
     */
    private void requestParams(Map<String, String> uriPathParam,
                               Map<String, Object> params,
                               Map<String, Object> body,
                               Map<String, String> requestParaPositionMap,
                               List<DebugApiParasVO> debugApiParas) {
        debugApiParas.forEach(para -> {
            String paraName = para.getParaName();
            String paraPosition = requestParaPositionMap.get(paraName);
            if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_PATH.getTypeName().equalsIgnoreCase(paraPosition)) {
                // PATH
                uriPathParam.put(paraName, para.getValue());
            } else if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY.getTypeName().equalsIgnoreCase(paraPosition)) {
                // QUERY
                params.put(paraName, para.getValue());
            } else if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_BODY.getTypeName().equalsIgnoreCase(paraPosition)) {
                // BODY
                body.put(paraName, para.getValue());
            } else if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_HEADER.getTypeName().equalsIgnoreCase(paraPosition)) {
                // TODO HEADER
            }
        });
    }

    /**
     * 分页参数设置
     *
     * @param pageNum
     * @param pageSize
     * @param pageFlag
     * @return
     */
    private Pagination pagination(String pageNum, String pageSize, Boolean pageFlag) {
        Pagination pagination = new Pagination();
        pagination.setSortField("");
        if (pageFlag) {
            if (Objects.nonNull(pageNum)) {
                pagination.setPage(Integer.parseInt(pageNum));
            } else {
                pagination.setPage(Pagination.PAGE_DEFAULT_NUM);
            }
            if (Objects.nonNull(pageSize)) {
                pagination.setSize(Integer.parseInt(pageSize));
            } else {
                pagination.setSize(Pagination.PAGE_DEFAULT_SIZE);
            }
        }
        return pagination;
    }
}
