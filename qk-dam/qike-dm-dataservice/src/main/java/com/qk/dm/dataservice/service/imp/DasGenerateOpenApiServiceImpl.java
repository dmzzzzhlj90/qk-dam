package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.openapi.ComponentField;
import com.qk.dam.openapi.OpenapiBuilder;
import com.qk.dm.dataservice.constant.ApiTypeEnum;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.constant.RequestParamPositionEnum;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiRegisterService;
import com.qk.dm.dataservice.service.DasGenerateOpenApiService;
import com.qk.dm.dataservice.utils.StringFormatUtils;
import com.qk.dm.dataservice.vo.*;
import org.openapi4j.parser.model.SerializationFlag;
import org.openapi4j.parser.model.v3.OpenApi3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 根据数据服务API生成OpenApiJson
 *
 * @author wjq
 * @date 2021/8/30 17:49
 * @since 1.0.0
 */
@Service
public class DasGenerateOpenApiServiceImpl implements DasGenerateOpenApiService {
    private static final Log LOG = LogFactory.get("数据服务_根据数据服务API生成OpenApiJson操作");

    public static final String OPEN_API_REQUEST_BODY_REF_SUFFIX = "Payload";
    public static final String OPEN_API_PARAMETER_TYPE_QUERY = "query";
    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiRegisterService dasApiRegisterService;

    @Autowired
    public DasGenerateOpenApiServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService, DasApiRegisterService dasApiRegisterService) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiRegisterService = dasApiRegisterService;
    }

    @Override
    public String generateOpenApiRegister() {
        // TODO 后期考虑版本,目录进行不同API的测试接口生成
        LOG.info("开始执行生成OpenApiJson操作!");
        String openApi3Json = null;
        try {
            // 获取所有注册API信息
            List<DasApiRegisterVO> dasApiRegisterList = dasApiRegisterService.findAll();
            LOG.info("获取到注册API个数为: 【{}】", dasApiRegisterList.size());
            if (!ObjectUtils.isEmpty(dasApiRegisterList)) {
                // 构建OpenApi3
                OpenapiBuilder openapiBuilder = getOpenApiBuilder(ApiTypeEnum.REGISTER_API.getCode(), "数据服务-注册Api-TEST", "3.0.3");
                openApiRegisterBuilder(dasApiRegisterList, openapiBuilder);
                // 构建完成,获取OpenApi3
                OpenApi3 openApi3 = openapiBuilder.getOpenApi3();
                LOG.info("成功获取OpenApi3!");
                EnumSet<SerializationFlag> enumSet = EnumSet.of(SerializationFlag.OUT_AS_JSON);
                openApi3Json = openApi3.toString(enumSet);
                LOG.info("成功生成OpenApiJson!");
            }
            LOG.info("数据服务注册API信息为空!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("生成OpenApi失败!!!");
        }
        return openApi3Json;
    }

    private void openApiRegisterBuilder(List<DasApiRegisterVO> dasApiRegisterList, OpenapiBuilder openapiBuilder) {
        for (DasApiRegisterVO dasApiRegisterVO : dasApiRegisterList) {
            //注册API定义信息
            DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO = dasApiRegisterVO.getApiRegisterDefinitionVO();
            List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOList = dasApiRegisterDefinitionVO.getApiRegisterBackendParaVOS();
            // registerComponentsBuilder 请求参数
            if (!ObjectUtils.isEmpty(apiRegisterBackendParaVOList)) {
                // 获取基础信定义参数信息
                Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap = getBasicRequestParas(dasApiRegisterVO);
                // registerRequestComponentsBuilder TODO registerResponseComponentsBuilder响应参数
                String requestCompKey =
                        registerRequestComponentsBuilder(
                                openapiBuilder,
                                dasApiRegisterDefinitionVO,
                                apiRegisterBackendParaVOList,
                                basicInfoRequestParasMap);
                // pathsBuilder
                pathsBuilder(openapiBuilder,
                        dasApiRegisterDefinitionVO.getBackendPath(),
                        dasApiRegisterDefinitionVO.getRequestType(),
                        dasApiRegisterDefinitionVO.getDescription());
                // 参数位置
                if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY
                        .getTypeName()
                        .equalsIgnoreCase(apiRegisterBackendParaVOList.get(0).getBackendParaPosition())) {
                    // form,Request body
                    openapiBuilder.requestBody(
                            dasApiRegisterDefinitionVO.getBackendPath(),
                            dasApiRegisterDefinitionVO.getRequestType().toLowerCase(),
                            true,
                            OpenapiBuilder.MEDIA_CONTENT_FORM,
                            requestCompKey);
                } else if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_PATH
                        .getTypeName()
                        .equalsIgnoreCase(apiRegisterBackendParaVOList.get(0).getBackendParaPosition())) {
                    // path,Parameters
                    parameterRegisterBuilder(
                            openapiBuilder,
                            dasApiRegisterDefinitionVO.getBackendPath(),
                            dasApiRegisterDefinitionVO.getRequestType(),
                            apiRegisterBackendParaVOList,
                            basicInfoRequestParasMap);
                }
            } else {
                // pathsBuilder
                pathsBuilder(
                        openapiBuilder,
                        dasApiRegisterDefinitionVO.getBackendPath(),
                        dasApiRegisterDefinitionVO.getRequestType(),
                        dasApiRegisterDefinitionVO.getDescription());
            }
            // response todo response  ref
            openapiBuilder.response(
                    dasApiRegisterDefinitionVO.getBackendPath(),
                    dasApiRegisterDefinitionVO.getRequestType().toLowerCase(),
                    "200",
                    "ok");
        }
    }

    private OpenapiBuilder getOpenApiBuilder(String title, String description, String version) {
        return OpenapiBuilder.builder().build().info(title, description, version);
    }

    private void pathsBuilder(
            OpenapiBuilder openapiBuilder, String pathName, String httpMethod, String summary) {
        openapiBuilder.path(pathName, httpMethod.toLowerCase(), summary);
    }

    private String registerRequestComponentsBuilder(OpenapiBuilder openapiBuilder,
                                                    DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO,
                                                    List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOList,
                                                    Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap) {
        List<ComponentField> componentFields = new ArrayList<>();
        // 构建components
        for (DasApiRegisterBackendParaVO backendParaVO : apiRegisterBackendParaVOList) {
            DasApiBasicInfoRequestParasVO basicInfoRequestParasVO =
                    basicInfoRequestParasMap.get(backendParaVO.getParaName()).get(0);
            ComponentField componentField =
                    getComponentField(
                            basicInfoRequestParasVO.getParaType(),
                            backendParaVO.getBackendParaName(),
                            basicInfoRequestParasVO.getParaCHNName(),
                            basicInfoRequestParasVO.getDefaultValue(),
                            basicInfoRequestParasVO.getNecessary());
            componentFields.add(componentField);
        }
        String backendPath = dasApiRegisterDefinitionVO.getBackendPath();
        String requestCompKey =
                StringFormatUtils.camelName(backendPath.split("/")[backendPath.split("/").length - 1])
                        + OPEN_API_REQUEST_BODY_REF_SUFFIX;
        openapiBuilder.components(requestCompKey, componentFields);
        return requestCompKey;
    }

    private Map<String, List<DasApiBasicInfoRequestParasVO>> getBasicRequestParas(DasApiRegisterVO dasApiRegisterVO) {
        // 获取API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getApiBasicInfoVO();
        // 获取基础信定义参数信息
        List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList = dasApiBasicInfoVO.getApiBasicInfoRequestParasVOS();
        return basicInfoRequestParasVOList.stream()
                .collect(Collectors.groupingBy(DasApiBasicInfoRequestParasVO::getParaName));
    }

    private ComponentField getComponentField(
            String paraType,
            String fieldName,
            String description,
            Object defaultValue,
            boolean required) {
        ComponentField.ComponentFieldBuilder componentFieldBuilder =
                ComponentField.builder()
                        .fieldName(fieldName)
                        .desc(description)
                        .defaultValue(defaultValue)
                        .required(required);
        // 参数类型设置
        componentFieldType(paraType, componentFieldBuilder);
        return componentFieldBuilder.build();
    }

    private void componentFieldType(
            String paraType, ComponentField.ComponentFieldBuilder componentFieldBuilder) {
        if (DasConstant.DAS_API_PARA_COL_TYPE_STRING.equalsIgnoreCase(paraType)) {
            componentFieldBuilder.type(DasConstant.DAS_API_PARA_COL_TYPE_STRING.toLowerCase());
        } else if (DasConstant.DAS_API_PARA_COL_TYPE_INTEGER.equalsIgnoreCase(paraType)) {
            componentFieldBuilder.type(DasConstant.DAS_API_PARA_COL_TYPE_INTEGER.toLowerCase());
        } else {
            // 默认
            componentFieldBuilder.type(DasConstant.DAS_API_PARA_COL_TYPE_STRING.toLowerCase());
        }
    }

    private void parameterRegisterBuilder(
            OpenapiBuilder openapiBuilder,
            String backendPath,
            String httpMethod,
            List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOList,
            Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap) {
        for (DasApiRegisterBackendParaVO backendParaVO : apiRegisterBackendParaVOList) {
            DasApiBasicInfoRequestParasVO requestParasVO =
                    basicInfoRequestParasMap.get(backendParaVO.getParaName()).get(0);
            openapiBuilder.parameter(
                    backendPath,
                    httpMethod.toLowerCase(),
                    OPEN_API_PARAMETER_TYPE_QUERY,
                    backendParaVO.getBackendParaName(),
                    requestParasVO.getNecessary(),
                    requestParasVO.getParaType().toLowerCase());
        }
    }
}
