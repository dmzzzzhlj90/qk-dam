package com.qk.dm.dataservice.biz;

import com.qk.dam.openapi.ComponentField;
import com.qk.dam.openapi.OpenapiBuilder;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.constant.RequestParamPositionEnum;
import com.qk.dm.dataservice.utils.StringFormatUtils;
import com.qk.dm.dataservice.vo.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 新建API配置方式 OPENAPI GENERATE
 *
 * @author wjq
 * @date 2022/4/21
 * @since 1.0.0
 */
@Component
public class CreateConfigApiOpenApiBuilder {

    public void openApiBuilder(List<DasApiCreateConfigDefinitionVO> createConfigDefinitions,
                               Map<String, List<DasApiBasicInfoVO>> createApiMap,
                               OpenapiBuilder openapiBuilder) {
        for (DasApiCreateConfigDefinitionVO createConfigDefinitionVO : createConfigDefinitions) {
            //新建API 请求参数信息
            List<DasApiCreateRequestParasVO> createRequestParasVOList = createConfigDefinitionVO.getApiCreateRequestParasVOS();

            // API基础信息
            Optional<DasApiBasicInfoVO> optionalApiBasicInfo = createApiMap.get(createConfigDefinitionVO.getApiId()).stream().findFirst();
            if (optionalApiBasicInfo.isEmpty()) {
                continue;
            }
            DasApiBasicInfoVO apiBasicInfoVO = optionalApiBasicInfo.get();

            // paths
            paths(openapiBuilder, apiBasicInfoVO.getApiPath(), apiBasicInfoVO.getRequestType(), apiBasicInfoVO.getDescription());

            // 有参数请求,需要设置components
            if (!ObjectUtils.isEmpty(createRequestParasVOList)) {
                reqComponents(apiBasicInfoVO, openapiBuilder, createConfigDefinitionVO);
            }

            // 返回值,需要设置components
            responseComponents(apiBasicInfoVO, openapiBuilder, createConfigDefinitionVO);
        }
    }

    /**
     * 有参数请求,需要设置components
     *
     * @param apiBasicInfoVO
     * @param openapiBuilder
     * @param createConfigDefinitionVO
     */
    private void reqComponents(DasApiBasicInfoVO apiBasicInfoVO,
                               OpenapiBuilder openapiBuilder,
                               DasApiCreateConfigDefinitionVO createConfigDefinitionVO) {

        // 获取基础信定义参数信息
        Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap = getBasicRequestParas(apiBasicInfoVO);
        //新建API 请求参数信息
        List<DasApiCreateRequestParasVO> createRequestParasVOList = createConfigDefinitionVO.getApiCreateRequestParasVOS();

        // requestComponents
        String requestCompKey = reqComponents(apiBasicInfoVO, openapiBuilder, createRequestParasVOList, basicInfoRequestParasMap);

        // 参数位置
        String backendParaPosition = apiBasicInfoVO.getApiBasicInfoRequestParasVOS().get(0).getParaPosition();
        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY.getTypeName().equalsIgnoreCase(backendParaPosition)) {
            // 请求参数位置 form,Request body
            openapiBuilder.requestBody(
                    apiBasicInfoVO.getApiPath(),
                    apiBasicInfoVO.getRequestType().toLowerCase(),
                    true,
                    OpenapiBuilder.MEDIA_CONTENT_FORM,
                    requestCompKey);
        } else if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_PATH.getTypeName().equalsIgnoreCase(backendParaPosition)) {
            // 请求参数位置 path,Parameters
            parameterRegisterBuilder(
                    openapiBuilder,
                    apiBasicInfoVO.getApiPath(),
                    apiBasicInfoVO.getRequestType(),
                    createRequestParasVOList,
                    basicInfoRequestParasMap);
        }
    }

    /**
     * 返回值,需要设置components
     *
     * @param apiBasicInfoVO
     * @param openapiBuilder
     * @param createConfigDefinitionVO
     */
    private void responseComponents(DasApiBasicInfoVO apiBasicInfoVO,
                                    OpenapiBuilder openapiBuilder,
                                    DasApiCreateConfigDefinitionVO createConfigDefinitionVO) {
        List<DasApiCreateResponseParasVO> createResponseParasVOList = createConfigDefinitionVO.getApiCreateResponseParasVOS();
        if (!ObjectUtils.isEmpty(createResponseParasVOList)) {
            // 设置返回值,同样需要设置responseComponents响应参数
            String responseCompKey = respComponents(apiBasicInfoVO, openapiBuilder, createResponseParasVOList);
            // 有参数的response
            openapiBuilder.response(apiBasicInfoVO.getApiPath(), apiBasicInfoVO.getRequestType().toLowerCase(), "200", "ok", responseCompKey);
        } else {
            // 无返回信息的response
            openapiBuilder.response(apiBasicInfoVO.getApiPath(), apiBasicInfoVO.getRequestType().toLowerCase(), "200", "ok");
        }

    }

    /**
     * 设置paths
     *
     * @param openapiBuilder
     * @param pathName
     * @param httpMethod
     * @param summary
     */
    private void paths(OpenapiBuilder openapiBuilder, String pathName, String httpMethod, String summary) {
        openapiBuilder.path(pathName, httpMethod.toLowerCase(), summary);
    }

    /**
     * 请求参数设置
     *
     * @param apiBasicInfoVO
     * @param openapiBuilder
     * @param createRequestParasVOList
     * @param basicInfoRequestParasMap
     * @return
     */
    private String reqComponents(DasApiBasicInfoVO apiBasicInfoVO,
                                 OpenapiBuilder openapiBuilder,
                                 List<DasApiCreateRequestParasVO> createRequestParasVOList,
                                 Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap) {
        List<ComponentField> componentFields = new ArrayList<>();
        // 构建components
        for (DasApiCreateRequestParasVO createRequestParasVO : createRequestParasVOList) {
            // 获取基础信息定义的请求参数信息
            DasApiBasicInfoRequestParasVO basicReqVO = getBasicRepVOByParaName(basicInfoRequestParasMap, createRequestParasVO);
            if (basicReqVO == null) {
                continue;
            }

            ComponentField componentField =
                    getComponentField(
                            basicReqVO.getParaType(),
                            basicReqVO.getParaName(),
                            basicReqVO.getParaCHNName(),
                            basicReqVO.getDefaultValue(),
                            basicReqVO.getNecessary());
            componentFields.add(componentField);
        }
        String backendPath = apiBasicInfoVO.getApiPath();
        String requestCompKey = StringFormatUtils.
                camelName(backendPath.split("/")[backendPath.split("/").length - 1])
                + OpenapiBuilder.OPEN_API_REQUEST_BODY_REF_SUFFIX;
        openapiBuilder.components(requestCompKey, componentFields);
        return requestCompKey;
    }

    /**
     * 响应设置 components
     *
     * @param apiBasicInfoVO
     * @param openapiBuilder
     * @param createResponseParasVOList
     */
    private String respComponents(DasApiBasicInfoVO apiBasicInfoVO,
                                  OpenapiBuilder openapiBuilder,
                                  List<DasApiCreateResponseParasVO> createResponseParasVOList) {
        List<ComponentField> componentFields = new ArrayList<>();
        // 构建components
        for (DasApiCreateResponseParasVO createResponseParasVO : createResponseParasVOList) {
            ComponentField componentField =
                    getComponentField(
                            createResponseParasVO.getParaType(),
                            createResponseParasVO.getParaName(),
                            createResponseParasVO.getParaName(),
                            createResponseParasVO.getDefaultValue(),
                            false);
            componentFields.add(componentField);
        }
        String backendPath = apiBasicInfoVO.getApiPath();
        String responseCompKey = StringFormatUtils.
                camelName(backendPath.split("/")[backendPath.split("/").length - 1])
                + OpenapiBuilder.OPEN_API_RESPONSE_BODY_REF_SUFFIX;
        openapiBuilder.components(responseCompKey, componentFields);
        return responseCompKey;

    }

    /**
     * 获取基础信息定义的请求参数信息
     *
     * @param basicInfoRequestParasMap
     * @param createRequestParasVO
     * @return
     */
    private DasApiBasicInfoRequestParasVO getBasicRepVOByParaName(Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap,
                                                                  DasApiCreateRequestParasVO createRequestParasVO) {
        List<DasApiBasicInfoRequestParasVO> basicRequestParas = basicInfoRequestParasMap.get(createRequestParasVO.getParaName());
        Optional<DasApiBasicInfoRequestParasVO> optionalBasicInfoRequestParas = basicRequestParas.stream().findFirst();
        if (optionalBasicInfoRequestParas.isEmpty()) {
            return null;
        }
        return optionalBasicInfoRequestParas.get();
    }

    /**
     * 获取基础信定义参数信息
     *
     * @param apiBasicInfoVO
     * @return
     */
    private Map<String, List<DasApiBasicInfoRequestParasVO>> getBasicRequestParas(DasApiBasicInfoVO apiBasicInfoVO) {
        // 获取基础信定义参数信息
        List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList = apiBasicInfoVO.getApiBasicInfoRequestParasVOS();
        return basicInfoRequestParasVOList.stream().collect(Collectors.groupingBy(DasApiBasicInfoRequestParasVO::getParaName));
    }

    private ComponentField getComponentField(String paraType,
                                             String fieldName,
                                             String description,
                                             Object defaultValue,
                                             boolean required) {
        ComponentField.ComponentFieldBuilder componentFieldBuilder = ComponentField.builder()
                .fieldName(fieldName)
                .desc(description)
                .defaultValue(defaultValue)
                .required(required);
        // 参数类型设置
        componentFieldType(paraType, componentFieldBuilder);
        return componentFieldBuilder.build();
    }

    private void componentFieldType(String paraType, ComponentField.ComponentFieldBuilder componentFieldBuilder) {
        if (DasConstant.DAS_API_PARA_COL_TYPE_STRING.equalsIgnoreCase(paraType)) {
            componentFieldBuilder.type(DasConstant.DAS_API_PARA_COL_TYPE_STRING.toLowerCase());
        } else if (DasConstant.DAS_API_PARA_COL_TYPE_INTEGER.equalsIgnoreCase(paraType)) {
            componentFieldBuilder.type(DasConstant.DAS_API_PARA_COL_TYPE_INTEGER.toLowerCase());
        } else {
            // 默认
            componentFieldBuilder.type(DasConstant.DAS_API_PARA_COL_TYPE_STRING.toLowerCase());
        }
    }

    /**
     * 请求参数位置 path,Parameters
     *
     * @param openapiBuilder
     * @param backendPath
     * @param httpMethod
     * @param createRequestParasVOList
     * @param basicInfoRequestParasMap
     */
    private void parameterRegisterBuilder(OpenapiBuilder openapiBuilder,
                                          String backendPath,
                                          String httpMethod,
                                          List<DasApiCreateRequestParasVO> createRequestParasVOList,
                                          Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap) {
        for (DasApiCreateRequestParasVO createRequestParasVO : createRequestParasVOList) {
            // 获取基础信息定义的请求参数信息
            DasApiBasicInfoRequestParasVO basicReqVO = getBasicRepVOByParaName(basicInfoRequestParasMap, createRequestParasVO);
            if (basicReqVO == null) {
                continue;
            }
            openapiBuilder.parameter(
                    backendPath,
                    httpMethod.toLowerCase(),
                    OpenapiBuilder.OPEN_API_PARAMETER_TYPE_QUERY,
                    basicReqVO.getParaName(),
                    basicReqVO.getNecessary(),
                    basicReqVO.getParaType().toLowerCase());
        }
    }
}
