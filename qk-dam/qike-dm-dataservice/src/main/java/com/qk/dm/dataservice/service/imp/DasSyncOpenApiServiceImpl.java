package com.qk.dm.dataservice.service.imp;

import com.qk.dam.openapi.OpenapiBuilder;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.constant.RequestParamPositionEnum;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.service.DasApiRegisterService;
import com.qk.dm.dataservice.service.DasSyncOpenApiService;
import com.qk.dm.dataservice.vo.*;
import org.openapi4j.parser.OpenApi3Parser;
import org.openapi4j.parser.model.v3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * OPEN-API同步操作
 *
 * @author wjq
 * @date 2021/8/30 17:49
 * @since 1.0.0
 */
@Service
public class DasSyncOpenApiServiceImpl implements DasSyncOpenApiService {
    private final DasApiDirService dasApiDirService;
    private final DasApiRegisterService dasApiRegisterService;

    @Value("${open_api.url}")
    private String OPEN_API_URL;

    @Value("${open_api.host}")
    private String OPEN_API_HOST;

    @Value("${open_api.protocol_type}")
    private String OPEN_API_PROTOCOL_TYPE;

    @Autowired
    public DasSyncOpenApiServiceImpl(DasApiDirService dasApiDirService, DasApiRegisterService dasApiRegisterService) {
        this.dasApiDirService = dasApiDirService;
        this.dasApiRegisterService = dasApiRegisterService;
    }

    @Override
    public void syncRegister() {
        List<DasApiRegisterVO> dasApiRegisterVOList = new ArrayList<>();
        try {
            //获取同步API信息
            URL url = new URL(OPEN_API_URL);
            OpenApi3 openApi3 = new OpenApi3Parser().parse(url, false);
            //目录设置
            DasApiDirVO dasApiDirVO = getApiDirVO(openApi3);
            //构建同步对象
            getDasApiRegister(dasApiRegisterVOList, dasApiDirVO, openApi3);
            //执行注册API落库
            syncRegisterApi(dasApiRegisterVOList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DasApiDirVO getApiDirVO(OpenApi3 openApi3) {
        DasApiDirVO dasApiDirVO;
        String title = openApi3.getInfo().getTitle();
        List<DasApiDirVO> dasApiDirVOList = dasApiDirService.getDasApiDirByDirName(title);
        if (null != dasApiDirVOList && dasApiDirVOList.size() > 0) {
            dasApiDirVO = dasApiDirVOList.get(0);
        } else {
            dasApiDirVO = DasApiDirVO.builder().apiDirLevel("/" + title).apiDirName(title)
                    .parentId(DasConstant.TREE_DIR_TOP_PARENT_ID).description("自动同步注册Api，根据Title自动创建目录!").build();
            dasApiDirService.addDasApiDir(dasApiDirVO);
        }
        return dasApiDirVO;
    }

    private void getDasApiRegister(List<DasApiRegisterVO> dasApiRegisterVOList, DasApiDirVO dasApiDirVO, OpenApi3 openApi3) {
        Map<String, Schema> schemaMap = openApi3.getComponents().getSchemas();
        Map<String, Path> apiMap = openApi3.getPaths();
        for (String pathKey : apiMap.keySet()) {
            DasApiBasicInfoVO.DasApiBasicInfoVOBuilder apiBasicInfoVOBuilder = DasApiBasicInfoVO.builder();
            DasApiRegisterVO.DasApiRegisterVOBuilder apiRegisterVOBuilder = DasApiRegisterVO.builder();
            //设置目录信息
            apiBasicInfoVOBuilder.dasDirId(dasApiDirVO.getApiDirId()).apiDirLevel(dasApiDirVO.getApiDirLevel());
            //构建注册Api对象
            DasApiRegisterVO dasApiRegisterVO = buildRegisterVO(schemaMap, apiMap, pathKey, apiBasicInfoVOBuilder, apiRegisterVOBuilder);
            dasApiRegisterVOList.add(dasApiRegisterVO);
        }
    }

    private void syncRegisterApi(List<DasApiRegisterVO> dasApiRegisterVOList) {
        dasApiRegisterService.bulkAddDasApiRegister(dasApiRegisterVOList);
    }

    private DasApiRegisterVO buildRegisterVO(Map<String, Schema> schemaMap, Map<String, Path> apiMap, String pathKey,
                                             DasApiBasicInfoVO.DasApiBasicInfoVOBuilder apiBasicInfoVOBuilder,
                                             DasApiRegisterVO.DasApiRegisterVOBuilder apiRegisterVOBuilder) {
        DasApiRegisterVO dasApiRegisterVO = null;
        //请求URL
        Path path = apiMap.get(pathKey);
        for (Map.Entry<String, Operation> operationEntry : path.getOperations().entrySet()) {
            //请求方式
            String requestType = operationEntry.getKey();
            String apiName = operationEntry.getValue().getSummary();
            RequestBody requestBody = operationEntry.getValue().getRequestBody();
            apiBasicInfoVOBuilder.apiName(apiName).apiPath(pathKey).apiType(DasConstant.REGISTER_API_CODE)
                    .protocolType(OPEN_API_PROTOCOL_TYPE).requestType(requestType).description("自动同步注册Api," + apiName);
            apiRegisterVOBuilder.backendHost(OPEN_API_HOST).backendPath(pathKey).backendTimeout("30").protocolType(OPEN_API_PROTOCOL_TYPE)
                    .requestType(requestType).description("自动同步注册Api," + apiName);
            //同步入参信息
            existRequestBody(schemaMap, apiBasicInfoVOBuilder, apiRegisterVOBuilder, requestBody);
            //构建API基础信息
            DasApiBasicInfoVO dasApiBasicInfoVO = apiBasicInfoVOBuilder.build();
            apiRegisterVOBuilder.dasApiBasicInfoVO(dasApiBasicInfoVO);
            //构建注册API
            dasApiRegisterVO = apiRegisterVOBuilder.build();
        }
        return dasApiRegisterVO;
    }

    private void existRequestBody(Map<String, Schema> schemaMap,
                                  DasApiBasicInfoVO.DasApiBasicInfoVOBuilder apiBasicInfoVOBuilder,
                                  DasApiRegisterVO.DasApiRegisterVOBuilder apiRegisterVOBuilder,
                                  RequestBody requestBody) {
        if (!ObjectUtils.isEmpty(requestBody)) {
            String paraPosition = new ArrayList<>(requestBody.getContentMediaTypes().keySet()).get(0);
            for (Map.Entry<String, MediaType> mediaTypeEntry : requestBody.getContentMediaTypes().entrySet()) {
                List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList = new ArrayList<>();
                String ref = mediaTypeEntry.getValue().getSchema().getRef();
                Map<String, Schema> properties;
                List<String> requiredFields;
                if (!ObjectUtils.isEmpty(ref)) {
//                String canonicalRef = mediaTypeEntry.getValue().getSchema().getCanonicalRef();
                    Schema schema = schemaMap.get(ref.split("/")[ref.split("/").length - 1]);
                    properties = schema.getProperties();
                    requiredFields = schema.getRequiredFields();
                } else {
                    properties = mediaTypeEntry.getValue().getSchema().getProperties();
                    requiredFields = mediaTypeEntry.getValue().getSchema().getRequiredFields();
                }
                //构建API基础信息入参定义对象
                buildBasicInfoRequestParasVO(paraPosition, basicInfoRequestParasVOList, properties, requiredFields);
                apiBasicInfoVOBuilder.dasApiBasicInfoRequestParasVO(basicInfoRequestParasVOList);
                //构建注册API后端参数对象
                List<DasApiRegisterBackendParaVO> dasApiRegisterBackendParaVOList = getDasApiRegisterBackendParaVO(basicInfoRequestParasVOList);
                apiRegisterVOBuilder.dasApiRegisterBackendParaVO(dasApiRegisterBackendParaVOList);
            }
        }
    }

    private void buildBasicInfoRequestParasVO(String paraPosition,
                                              List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList,
                                              Map<String, Schema> properties,
                                              List<String> requiredFields) {
        for (Map.Entry<String, Schema> schemaEntry : properties.entrySet()) {
            String key = schemaEntry.getKey();
            Schema schemaEntryValue = schemaEntry.getValue();
            //入参定义对象
            DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasVOBuilder
                    = DasApiBasicInfoRequestParasVO.builder();
            //构建入参定义对象
            basicInfoRequestParasVOBuilder.paraName(key)
                    .paraCHNName(schemaEntryValue.getDescription())
                    .paraType(schemaEntryValue.getType().toUpperCase())
                    .defaultValue((String) schemaEntryValue.getDefault())
                    .description(schemaEntryValue.getDescription());
            //设置必填参数
            setRequiredFields(requiredFields, key, basicInfoRequestParasVOBuilder);
            //设置参数位置
            setParaPosition(paraPosition, basicInfoRequestParasVOBuilder);
            basicInfoRequestParasVOList.add(basicInfoRequestParasVOBuilder.build());
        }
    }

    private void setParaPosition(String paraPosition, DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasVOBuilder) {
        //TODO 根据具体参数位置进行设定
        if (OpenapiBuilder.MEDIA_CONTENT_FORM.equalsIgnoreCase(paraPosition)) {
            //Request body
            basicInfoRequestParasVOBuilder.paraPosition(RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY.getTypeName());
        } else {
            //Parameters
            basicInfoRequestParasVOBuilder.paraPosition(RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_PATH.getTypeName());
        }
    }

    private void setRequiredFields(List<String> requiredFields, String key, DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasVOBuilder) {
        if (requiredFields != null && requiredFields.size() > 0) {
            if (requiredFields.contains(key)) {
                basicInfoRequestParasVOBuilder.necessary(true).supportNull(true);
            } else {
                basicInfoRequestParasVOBuilder.necessary(false).supportNull(false);
            }
        } else {
            basicInfoRequestParasVOBuilder.necessary(false).supportNull(false);
        }

    }

    private List<DasApiRegisterBackendParaVO> getDasApiRegisterBackendParaVO(List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList) {
        List<DasApiRegisterBackendParaVO> backendParaVOList = new ArrayList<>();
        for (DasApiBasicInfoRequestParasVO requestParasVO : basicInfoRequestParasVOList) {
            DasApiRegisterBackendParaVO backendParaVO = DasApiRegisterBackendParaVO.builder()
                    .paraName(requestParasVO.getParaName()).paraPosition(requestParasVO.getParaPosition())
                    .paraType(requestParasVO.getParaType())
                    .backendParaName(requestParasVO.getParaName())
                    .backendParaPosition(requestParasVO.getParaPosition())
                    .description(requestParasVO.getDescription())
                    .build();
            backendParaVOList.add(backendParaVO);
        }
        return backendParaVOList;
    }

}