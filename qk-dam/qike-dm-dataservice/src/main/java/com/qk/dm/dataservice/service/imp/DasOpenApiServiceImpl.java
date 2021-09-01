package com.qk.dm.dataservice.service.imp;

import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.constant.RequestParamPositionEnum;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.service.DasApiRegisterService;
import com.qk.dm.dataservice.service.DasOpenApiService;
import com.qk.dm.dataservice.test.ApiDocTest;
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
public class DasOpenApiServiceImpl implements DasOpenApiService {
    private final DasApiDirService dasApiDirService;
    private final DasApiRegisterService dasApiRegisterService;

    @Value("${open_api.url}")
    private String OPEN_API_URL;

    @Value("${open_api.host}")
    private String OPEN_API_HOST;

    @Value("${open_api.protocol_type}")
    private String OPEN_API_PROTOCOL_TYPE;

    @Autowired
    public DasOpenApiServiceImpl(DasApiDirService dasApiDirService, DasApiRegisterService dasApiRegisterService) {
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
            //构建注册ApiVO对象
            DasApiBasicInfoVO.DasApiBasicInfoVOBuilder apiBasicInfoVOBuilder = DasApiBasicInfoVO.builder();
            DasApiRegisterVO.DasApiRegisterVOBuilder apiRegisterVOBuilder = DasApiRegisterVO.builder();
            apiBasicInfoVOBuilder.dasDirId(dasApiDirVO.getApiDirId()).apiDirLevel(dasApiDirVO.getApiDirLevel());
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
            String paraPosition = operationEntry.getValue().getDescription();
            RequestBody requestBody = operationEntry.getValue().getRequestBody();
            apiBasicInfoVOBuilder.apiName(apiName).apiPath(pathKey).apiType(DasConstant.REGISTER_API_CODE)
                    .protocolType(OPEN_API_PROTOCOL_TYPE).requestType(requestType).description("自动同步注册Api," + apiName);
            apiRegisterVOBuilder.backendHost(OPEN_API_HOST).backendPath(pathKey).backendTimeout("30").protocolType(OPEN_API_PROTOCOL_TYPE)
                    .requestType(requestType).description("自动同步注册Api," + apiName);
            //Api有入参,需要获取入参信息
            existRequestBody(schemaMap, apiBasicInfoVOBuilder, apiRegisterVOBuilder, paraPosition, requestBody);
            DasApiBasicInfoVO dasApiBasicInfoVO = apiBasicInfoVOBuilder.build();
            apiRegisterVOBuilder.dasApiBasicInfoVO(dasApiBasicInfoVO);
            dasApiRegisterVO = apiRegisterVOBuilder.build();
        }
        return dasApiRegisterVO;
    }

    private void existRequestBody(Map<String, Schema> schemaMap,
                                  DasApiBasicInfoVO.DasApiBasicInfoVOBuilder apiBasicInfoVOBuilder,
                                  DasApiRegisterVO.DasApiRegisterVOBuilder apiRegisterVOBuilder,
                                  String paraPosition, RequestBody requestBody) {
        if (!ObjectUtils.isEmpty(requestBody)) {
            for (Map.Entry<String, MediaType> mediaTypeEntry : requestBody.getContentMediaTypes().entrySet()) {
                List<DasApiBasicInfoRequestParasVO> apiBasicInfoRequestParasVOList = new ArrayList<>();
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
                //构建API基础信息
                buildDasApiBasicInfoVO(paraPosition, apiBasicInfoRequestParasVOList, properties, requiredFields);
                apiBasicInfoVOBuilder.dasApiBasicInfoRequestParasVO(apiBasicInfoRequestParasVOList);
                List<DasApiRegisterBackendParaVO> dasApiRegisterBackendParaVOList = getDasApiRegisterBackendParaVO(apiBasicInfoRequestParasVOList);
                apiRegisterVOBuilder.dasApiRegisterBackendParaVO(dasApiRegisterBackendParaVOList);

            }
        }
    }

    private void buildDasApiBasicInfoVO(String paraPosition, List<DasApiBasicInfoRequestParasVO> apiBasicInfoRequestParasVOList, Map<String, Schema> properties, List<String> requiredFields) {
        for (Map.Entry<String, Schema> schemaEntry : properties.entrySet()) {
            Schema schemaEntryValue = schemaEntry.getValue();
            DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasVOBuilder
                    = DasApiBasicInfoRequestParasVO.builder();
            basicInfoRequestParasVOBuilder.paraName(schemaEntryValue.getTitle())
                    .paraType(schemaEntryValue.getType().toUpperCase()).description(schemaEntryValue.getDescription());
            setRequiredFields(requiredFields, schemaEntryValue, basicInfoRequestParasVOBuilder);
            setParaPosition(paraPosition, basicInfoRequestParasVOBuilder);
            apiBasicInfoRequestParasVOList.add(basicInfoRequestParasVOBuilder.build());
        }
    }

    private void setParaPosition(String paraPosition, DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasVOBuilder) {
        //TODO 根据具体参数位置进行设定
        if ("form".equalsIgnoreCase(paraPosition)) {
            basicInfoRequestParasVOBuilder.paraPosition(RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY.getTypeName());
        } else {
            basicInfoRequestParasVOBuilder.paraPosition(RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY.getTypeName());
        }
    }

    private void setRequiredFields(List<String> requiredFields, Schema schemaEntryValue, DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasVOBuilder) {
        if (requiredFields != null && requiredFields.size() > 0) {
            if (requiredFields.contains(schemaEntryValue.getTitle())) {
                basicInfoRequestParasVOBuilder.necessary(true).supportNull(true);
            } else {
                basicInfoRequestParasVOBuilder.necessary(false).supportNull(false);
            }
        } else {
            basicInfoRequestParasVOBuilder.necessary(false).supportNull(false);
        }

    }

    private List<DasApiRegisterBackendParaVO> getDasApiRegisterBackendParaVO(List<DasApiBasicInfoRequestParasVO> dasApiBasicInfoRequestParasVO) {
        List<DasApiRegisterBackendParaVO> backendParaVOList = new ArrayList<>();
        for (DasApiBasicInfoRequestParasVO requestParasVO : dasApiBasicInfoRequestParasVO) {
            DasApiRegisterBackendParaVO backendParaVO = DasApiRegisterBackendParaVO.builder()
                    .paraName(requestParasVO.getParaName()).paraPosition(requestParasVO.getParaPosition())
                    .paraType(requestParasVO.getParaType())
                    .backendParaName(requestParasVO.getParaName()).backendParaPosition(requestParasVO.getParaPosition()).build();
            backendParaVOList.add(backendParaVO);
        }
        return backendParaVOList;
    }

    @Override
    public void sendApiToTorNaRest() {
        ApiDocTest.testBuilderControllersApi();
    }

}
