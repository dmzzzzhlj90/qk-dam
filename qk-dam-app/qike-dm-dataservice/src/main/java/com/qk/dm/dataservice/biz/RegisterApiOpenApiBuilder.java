package com.qk.dm.dataservice.biz;

import com.qk.dam.openapi.ComponentField;
import com.qk.dam.openapi.OpenapiBuilder;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.constant.RequestParamPositionEnum;
import com.qk.dm.dataservice.utils.StringFormatUtils;
import com.qk.dm.dataservice.vo.DasApiBasicInfoRequestParasVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiRegisterBackendParaVO;
import com.qk.dm.dataservice.vo.DasApiRegisterDefinitionVO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 注册API OPENAPI GENERATE
 *
 * @author wjq
 * @date 2022/4/21
 * @since 1.0.0
 */
@Component
public class RegisterApiOpenApiBuilder {

    public void openApiBuilder(List<DasApiRegisterDefinitionVO> registerDefinitions,
                               Map<String, List<DasApiBasicInfoVO>> registerApiMap,
                               OpenapiBuilder openapiBuilder) {
        for (DasApiRegisterDefinitionVO dasApiRegisterVO : registerDefinitions) {
            //注册API定义信息
            List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOList = dasApiRegisterVO.getApiRegisterBackendParaVOS();

            // API基础信息
            Optional<DasApiBasicInfoVO> optionalApiBasicInfo = registerApiMap.get(dasApiRegisterVO.getApiId()).stream().findFirst();
            if (optionalApiBasicInfo.isEmpty()) {
                continue;
            }
            DasApiBasicInfoVO apiBasicInfoVO = optionalApiBasicInfo.get();

            // pathsBuilder
            paths(openapiBuilder, dasApiRegisterVO.getBackendPath(), dasApiRegisterVO.getRequestType(), dasApiRegisterVO.getDescription());

            if (!ObjectUtils.isEmpty(apiRegisterBackendParaVOList)) {
                // 有参数请求,需要设置components和paths
                components(apiBasicInfoVO, openapiBuilder, dasApiRegisterVO, apiRegisterBackendParaVOList);
            }
            // 设置返回值信息 response todo response  ref
            openapiBuilder.response(dasApiRegisterVO.getBackendPath(), dasApiRegisterVO.getRequestType().toLowerCase(), "200", "ok");
        }
    }

    /**
     * 有参数请求,需要设置components
     *
     * @param apiBasicInfoVO
     * @param openapiBuilder
     * @param dasApiRegisterVO
     * @param apiRegisterBackendParaVOList
     */
    private void components(DasApiBasicInfoVO apiBasicInfoVO,
                            OpenapiBuilder openapiBuilder,
                            DasApiRegisterDefinitionVO dasApiRegisterVO,
                            List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOList) {

        // 获取基础信定义参数信息
        Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap = getBasicRequestParas(apiBasicInfoVO);
        // requestComponents TODO 如何设置返回值,同样需要设置responseComponents响应参数
        String requestCompKey = requestComponents(openapiBuilder, dasApiRegisterVO, apiRegisterBackendParaVOList, basicInfoRequestParasMap);

        // 参数位置
        String backendParaPosition = apiRegisterBackendParaVOList.get(0).getBackendParaPosition();
        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY.getTypeName().equalsIgnoreCase(backendParaPosition)) {
            // 请求参数位置 form,Request body
            openapiBuilder.requestBody(
                    dasApiRegisterVO.getBackendPath(),
                    dasApiRegisterVO.getRequestType().toLowerCase(),
                    true,
                    OpenapiBuilder.MEDIA_CONTENT_FORM,
                    requestCompKey);
        } else if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_PATH.getTypeName().equalsIgnoreCase(backendParaPosition)) {
            // 请求参数位置 path,Parameters
            parameterRegisterBuilder(
                    openapiBuilder,
                    dasApiRegisterVO.getBackendPath(),
                    dasApiRegisterVO.getRequestType(),
                    apiRegisterBackendParaVOList,
                    basicInfoRequestParasMap);
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
     * @param openapiBuilder
     * @param dasApiRegisterDefinitionVO
     * @param apiRegisterBackendParaVOList
     * @param basicInfoRequestParasMap
     * @return
     */
    private String requestComponents(OpenapiBuilder openapiBuilder,
                                     DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO,
                                     List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOList,
                                     Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap) {
        List<ComponentField> componentFields = new ArrayList<>();
        // 构建components
        for (DasApiRegisterBackendParaVO backendParaVO : apiRegisterBackendParaVOList) {
            List<DasApiBasicInfoRequestParasVO> basicRequestParas = basicInfoRequestParasMap.get(backendParaVO.getParaName());
            Optional<DasApiBasicInfoRequestParasVO> optionalBasicRequestParasVO = basicRequestParas.stream().findFirst();
            if (optionalBasicRequestParasVO.isPresent()) {
                DasApiBasicInfoRequestParasVO basicInfoRequestParas = optionalBasicRequestParasVO.get();
                ComponentField componentField =
                        getComponentField(
                                basicInfoRequestParas.getParaType(),
                                backendParaVO.getBackendParaName(),
                                basicInfoRequestParas.getParaCHNName(),
                                basicInfoRequestParas.getDefaultValue(),
                                basicInfoRequestParas.getNecessary());
                componentFields.add(componentField);
            }
        }
        String backendPath = dasApiRegisterDefinitionVO.getBackendPath();
        String requestCompKey = StringFormatUtils.
                camelName(backendPath.split("/")[backendPath.split("/").length - 1])
                + OpenapiBuilder.OPEN_API_REQUEST_BODY_REF_SUFFIX;
        openapiBuilder.components(requestCompKey, componentFields);
        return requestCompKey;
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
     * @param apiRegisterBackendParaVOList
     * @param basicInfoRequestParasMap
     */
    private void parameterRegisterBuilder(OpenapiBuilder openapiBuilder,
                                          String backendPath,
                                          String httpMethod,
                                          List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOList,
                                          Map<String, List<DasApiBasicInfoRequestParasVO>> basicInfoRequestParasMap) {
        for (DasApiRegisterBackendParaVO backendParaVO : apiRegisterBackendParaVOList) {
            Optional<DasApiBasicInfoRequestParasVO> optionalBasicRequestParasVO = basicInfoRequestParasMap.get(backendParaVO.getParaName()).stream().findFirst();
            if (optionalBasicRequestParasVO.isPresent()) {
                DasApiBasicInfoRequestParasVO requestParasVO = optionalBasicRequestParasVO.get();
                openapiBuilder.parameter(
                        backendPath,
                        httpMethod.toLowerCase(),
                        OpenapiBuilder.OPEN_API_PARAMETER_TYPE_QUERY,
                        backendParaVO.getBackendParaName(),
                        requestParasVO.getNecessary(),
                        requestParasVO.getParaType().toLowerCase());
            }
        }
    }
}
