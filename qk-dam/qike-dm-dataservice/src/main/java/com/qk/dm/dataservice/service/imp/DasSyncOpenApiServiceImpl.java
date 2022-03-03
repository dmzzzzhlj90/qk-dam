package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.openapi.OpenapiBuilder;
import com.qk.dm.dataservice.config.ApiSixConnectInfo;
import com.qk.dm.dataservice.constant.ApiTypeEnum;
import com.qk.dm.dataservice.constant.RequestParamPositionEnum;
import com.qk.dm.dataservice.constant.SyncStatusEnum;
import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.service.DasApiRegisterService;
import com.qk.dm.dataservice.service.DasSyncOpenApiService;
import com.qk.dm.dataservice.vo.*;
import org.openapi4j.parser.OpenApi3Parser;
import org.openapi4j.parser.model.v3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 导入OpenApi(同步至数据服务操作)
 *
 * @author wjq
 * @date 2021/8/30 17:49
 * @since 1.0.0
 */
@RefreshScope
@Service
public class DasSyncOpenApiServiceImpl implements DasSyncOpenApiService {
    private static final Log LOG = LogFactory.get("导入OpenApi(同步至数据服务操作)");
    private final DasApiDirService dasApiDirService;
    private final DasApiRegisterService dasApiRegisterService;

    private final ApiSixConnectInfo apiSixConnectInfo;

    @Autowired
    public DasSyncOpenApiServiceImpl(
            DasApiDirService dasApiDirService,
            DasApiRegisterService dasApiRegisterService,
            ApiSixConnectInfo apiSixConnectInfo) {
        this.dasApiDirService = dasApiDirService;
        this.dasApiRegisterService = dasApiRegisterService;
        this.apiSixConnectInfo = apiSixConnectInfo;
    }

    @Override
    public int syncRegisterApi(DasOpenApiParamsVO dasOpenApiParamsVO) {
        LOG.info("========================同步OenApi开始!========================");
        try {
            for (String openApiPath : dasOpenApiParamsVO.getOpenApiPaths()) {
                syncRegisterByOpenApiPath(openApiPath, dasOpenApiParamsVO);
            }
            LOG.info("=====================同步OenApi已完成!======================");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 根据OpenApi路径同步数据
     *
     * @param openApiPath
     * @param dasOpenApiParamsVO
     * @throws Exception
     */
    private void syncRegisterByOpenApiPath(String openApiPath, DasOpenApiParamsVO dasOpenApiParamsVO) throws Exception {
        List<DasApiRegisterVO> dasApiRegisterVOList = new ArrayList<>();
        LOG.info("开始同步OpenApi,同步地址为: 【{}】", openApiPath);
        // 获取同步API信息
        OpenApi3 openApi3 = new OpenApi3Parser()
                .parse(new URL(openApiPath), false);
        LOG.info("Path:【{}】,成功解析OenApi3对象!", openApiPath);
        // 构建同步对象
        buildDasApiRegister(dasApiRegisterVOList,
                searchApiDirInfoByDirId(dasOpenApiParamsVO.getDirId()),
                openApi3);
        LOG.info("成功构建注册API对象,待同步对象的个数为: 【{}】 ", dasApiRegisterVOList.size());
        // 执行注册API落库
        syncRegisterApi(dasApiRegisterVOList);
        LOG.info("路径为:【{}】,数据落库已完成!!!", openApiPath);
    }

    /**
     * 构建注册API对象信息
     *
     * @param dasApiRegisterVOList
     * @param dasApiDir
     * @param openApi3
     */
    private void buildDasApiRegister(List<DasApiRegisterVO> dasApiRegisterVOList, DasApiDir dasApiDir, OpenApi3 openApi3) {
        //openApi3接口Path对象信息
        Map<String, Path> apiMap = openApi3.getPaths();
        for (String pathKey : apiMap.keySet()) {
            // API基础信息对象构建器
            DasApiBasicInfoVO.DasApiBasicInfoVOBuilder apiBasicInfoBuilder = DasApiBasicInfoVO.builder();
            // 设置目录信息
            apiBasicInfoBuilder.dirId(dasApiDir.getDirId()).dirName(dasApiDir.getDirName());
            // 注册Api对象
            dasApiRegisterVOList.add(
                    setRegisterParams(apiMap, pathKey, apiBasicInfoBuilder, openApi3));
        }
    }

    /**
     * 封装DasApiRegisterVO
     *
     * @param apiMap
     * @param pathKey
     * @param apiBasicInfoBuilder
     * @param openApi3
     * @return DasApiRegisterVO
     */
    private DasApiRegisterVO setRegisterParams(Map<String, Path> apiMap,
                                               String pathKey,
                                               DasApiBasicInfoVO.DasApiBasicInfoVOBuilder apiBasicInfoBuilder,
                                               OpenApi3 openApi3) {
        DasApiRegisterVO dasApiRegisterVO = null;
        // 注册API信息对象构建器
        DasApiRegisterVO.DasApiRegisterVOBuilder apiRegisterBuilder = DasApiRegisterVO.builder();
        // 注册API子类定义信息对象构建器
        DasApiRegisterDefinitionVO.DasApiRegisterDefinitionVOBuilder dasApiRegisterDefinitionBuilder = DasApiRegisterDefinitionVO.builder();
        // 获取OpenApi中的Schemas参数对象信息
        Map<String, Schema> schemaMap = openApi3.getComponents().getSchemas();
        // title
        String title = openApi3.getInfo().getTitle();
        // 获取OpenApi中的baseUrl对象信息
        URL baseUrl = openApi3.getContext().getBaseUrl();
        // API列表信息(接口文档地址中的具体接口信息集合)
        Path path = apiMap.get(pathKey);
        for (Map.Entry<String, Operation> operationEntry : path.getOperations().entrySet()) {
            // 请求方式
            String requestType = operationEntry.getKey();
            // API中文名称
            String apiName = operationEntry.getValue().getSummary();
            //RequestBody
            RequestBody requestBody = operationEntry.getValue().getRequestBody();
            //设置API基础信息对象
            apiBasicInfoBuilder
                    .apiName(apiName)
                    .apiPath(pathKey)
                    .apiType(ApiTypeEnum.REGISTER_API.getCode())
                    .protocolType(baseUrl.getProtocol())
                    .requestType(requestType)
                    .status(SyncStatusEnum.CREATE_NO_UPLOAD.getCode())
                    .description(title + " : " + apiName);
            //设置注册API子类定义信息对象
            dasApiRegisterDefinitionBuilder
                    .backendHost(baseUrl.getHost())
                    .backendPath(pathKey)
                    .backendTimeout(String.valueOf(apiSixConnectInfo.getUpstreamConnectTimeOut()))
                    .protocolType(baseUrl.getProtocol())
                    .requestType(requestType)
                    .description(title + " : " + apiName);

            // 同步入参信息
            setRequestBody(schemaMap, apiBasicInfoBuilder, dasApiRegisterDefinitionBuilder, requestBody);
            // 构建API基础信息
            DasApiBasicInfoVO dasApiBasicInfoVO = apiBasicInfoBuilder.build();
            apiRegisterBuilder.dasApiBasicInfoVO(dasApiBasicInfoVO);
            // 构建注册API
            DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO = dasApiRegisterDefinitionBuilder.build();
            apiRegisterBuilder.dasApiRegisterDefinitionVO(dasApiRegisterDefinitionVO);
            dasApiRegisterVO = apiRegisterBuilder.build();
        }
        return dasApiRegisterVO;
    }

    /**
     * 同步入参信息
     *
     * @param schemaMap
     * @param apiBasicInfoVOBuilder
     * @param dasApiRegisterDefinitionVOBuilder
     * @param requestBody
     */
    private void setRequestBody(Map<String, Schema> schemaMap,
                                DasApiBasicInfoVO.DasApiBasicInfoVOBuilder apiBasicInfoVOBuilder,
                                DasApiRegisterDefinitionVO.DasApiRegisterDefinitionVOBuilder dasApiRegisterDefinitionVOBuilder,
                                RequestBody requestBody) {
        if (!ObjectUtils.isEmpty(requestBody)) {
            // 参数位置信息
            String paraPosition = new ArrayList<>(requestBody.getContentMediaTypes().keySet()).get(0);

            // 封装请求,响应等参数VO
            for (Map.Entry<String, MediaType> mediaTypeEntry : requestBody.getContentMediaTypes().entrySet()) {
                List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList = new ArrayList<>();
                // ref
                String ref = mediaTypeEntry.getValue().getSchema().getRef();
                // 属性集合
                Map<String, Schema> properties;
                // 必填字段集合
                List<String> requiredFields;
                if (!ObjectUtils.isEmpty(ref)) {
//                    String canonicalRef = mediaTypeEntry.getValue().getSchema().getCanonicalRef();
                    // ref获取,参数schema,需要处理URL路径
                    Schema schema = schemaMap.get(ref.split("/")[ref.split("/").length - 1]);
                    properties = schema.getProperties();
                    requiredFields = schema.getRequiredFields();
                } else {
                    // 参数schema
                    properties = mediaTypeEntry.getValue().getSchema().getProperties();
                    requiredFields = mediaTypeEntry.getValue().getSchema().getRequiredFields();
                }
                // 构建API基础信息入参定义对象
                buildBasicInfoRequestParasVO(paraPosition, basicInfoRequestParasVOList, properties, requiredFields);
                apiBasicInfoVOBuilder.dasApiBasicInfoRequestParasVO(basicInfoRequestParasVOList);
                // 构建注册API子类定义对象,后端参数对象
                List<DasApiRegisterBackendParaVO> dasApiRegisterBackendParaVOList = buildDasApiRegisterBackendPara(basicInfoRequestParasVOList);
                dasApiRegisterDefinitionVOBuilder.dasApiRegisterBackendParaVO(dasApiRegisterBackendParaVOList);
            }
        }
    }

    /**
     * 构建API基础信息入参定义对象
     *
     * @param paraPosition
     * @param basicInfoRequestParasVOList
     * @param properties
     * @param requiredFields
     */
    private void buildBasicInfoRequestParasVO(String paraPosition,
                                              List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList,
                                              Map<String, Schema> properties,
                                              List<String> requiredFields) {
        for (Map.Entry<String, Schema> schemaEntry : properties.entrySet()) {
            String key = schemaEntry.getKey();
            Schema schemaEntryValue = schemaEntry.getValue();
            // 入参定义对象构建器
            DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasBuilder = DasApiBasicInfoRequestParasVO.builder();
            // 构建入参定义对象
            basicInfoRequestParasBuilder
                    .paraName(key)
                    .paraCHNName(schemaEntryValue.getDescription())
                    .paraType(schemaEntryValue.getType().toUpperCase())
                    .defaultValue(schemaEntryValue.getDefault())
                    .description(schemaEntryValue.getDescription());
            // 设置必填参数
            setRequiredFields(requiredFields, key, basicInfoRequestParasBuilder);
            // 设置参数位置
            setParaPosition(paraPosition, basicInfoRequestParasBuilder);
            basicInfoRequestParasVOList.add(basicInfoRequestParasBuilder.build());
        }
    }

    /**
     * API基础信息 入参定义对象 设置必填参数
     *
     * @param requiredFields
     * @param key
     * @param basicInfoRequestParasBuilder
     */
    private void setRequiredFields(List<String> requiredFields,
                                   String key,
                                   DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasBuilder) {
        if (requiredFields != null && requiredFields.size() > 0) {
            if (requiredFields.contains(key)) {
                basicInfoRequestParasBuilder.necessary(true).supportNull(true);
            } else {
                basicInfoRequestParasBuilder.necessary(false).supportNull(false);
            }
        } else {
            basicInfoRequestParasBuilder.necessary(false).supportNull(false);
        }
    }

    /**
     * 设置参数位置
     *
     * @param paraPosition
     * @param basicInfoRequestParasBuilder
     */
    private void setParaPosition(String paraPosition,
                                 DasApiBasicInfoRequestParasVO.DasApiBasicInfoRequestParasVOBuilder basicInfoRequestParasBuilder) {
        // TODO 根据具体参数位置进行设定
        if (OpenapiBuilder.MEDIA_CONTENT_FORM.equalsIgnoreCase(paraPosition)) {
            // Request body
            basicInfoRequestParasBuilder
                    .paraPosition(RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY.getTypeName());
        } else {
            // Parameters
            basicInfoRequestParasBuilder
                    .paraPosition(RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_PATH.getTypeName());
        }
    }

    /**
     * 构建注册API子类定义对象,后端参数对象
     *
     * @param basicInfoRequestParasVOList
     * @return
     */
    private List<DasApiRegisterBackendParaVO> buildDasApiRegisterBackendPara(List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList) {
        List<DasApiRegisterBackendParaVO> backendParaVOList = new ArrayList<>();
        for (DasApiBasicInfoRequestParasVO requestParasVO : basicInfoRequestParasVOList) {
            DasApiRegisterBackendParaVO backendParaVO =
                    DasApiRegisterBackendParaVO.builder()
                            .paraName(requestParasVO.getParaName())
                            .paraPosition(requestParasVO.getParaPosition())
                            .paraType(requestParasVO.getParaType())
                            .backendParaName(requestParasVO.getParaName())
                            .backendParaPosition(requestParasVO.getParaPosition())
                            .description(requestParasVO.getDescription())
                            .build();
            backendParaVOList.add(backendParaVO);
        }
        return backendParaVOList;
    }

    /**
     * 执行注册API落库
     *
     * @param dasApiRegisterVOList
     */
    private void syncRegisterApi(List<DasApiRegisterVO> dasApiRegisterVOList) {
        dasApiRegisterService.bulkAddDasApiRegister(dasApiRegisterVOList);
    }

    /**
     * 根据目录Id获取对应目录信息
     *
     * @param dirId
     * @return
     */
    private DasApiDir searchApiDirInfoByDirId(String dirId) {
        DasApiDir dasApiDir = dasApiDirService.searchApiDirInfoByDirId(dirId);
        if (dasApiDir == null) {
            throw new BizException("未查询到选择的目录信息!!!");
        }
        return dasApiDir;
    }

//    /**
//     * 根据接口Title自动创建
//     *
//     * @param openApi3
//     * @return
//     */
//    private DasApiDirVO searchApiDirInfo(OpenApi3 openApi3) {
//        DasApiDirVO dasApiDirVO;
//        String title = openApi3.getInfo().getTitle();
//        List<DasApiDirVO> dasApiDirVOList = dasApiDirService.getDasApiDirByDirName(title);
//        if (null != dasApiDirVOList && dasApiDirVOList.size() > 0) {
//            //TODO 目录支持选择
//            dasApiDirVO = dasApiDirVOList.get(0);
//        } else {
//            dasApiDirVO =
//                    DasApiDirVO.builder()
//                            //TODO 目录支持选择
//                            .value("/" + title)
//                            .title(title)
//                            .parentId(DasConstant.TREE_DIR_TOP_PARENT_ID)
//                            .description("自动同步注册Api，根据Title自动创建目录!")
//                            .build();
//            dasApiDirService.insert(dasApiDirVO);
//        }
//        return dasApiDirVO;
//    }

}
