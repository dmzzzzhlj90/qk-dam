package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dm.dataservice.config.ApiSixConnectInfo;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateConfig;
import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.manager.ApiGatewayManager;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasSyncApiGatewayService;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import com.qk.plugin.dataservice.apisix.route.entity.Nodes;
import com.qk.plugin.dataservice.apisix.route.entity.Timeout;
import com.qk.plugin.dataservice.apisix.route.entity.Upstream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 同步数据服务API至服务网关
 *
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Service
public class DasSyncApiGatewayServiceImpl implements DasSyncApiGatewayService {
    private static final Log LOG = LogFactory.get("同步数据服务API至服务网关下载程序");

    @Value("${create.api.upstream.host_port}")
    private String CREATE_API_UPSTREAM_HOST_PORT;

    @Value("${create.api.upstream.protocol_type}")
    private String CREATE_API_UPSTREAM_PROTOCOL_TYPE;

    private final ApiGatewayManager apiGatewayManager;
    private final DasApiBasicInfoRepository dasApiBasicInfoRepository;
    private final DasApiRegisterRepository dasApiRegisterRepository;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;
    private final ApiSixConnectInfo apiSixConnectInfo;

    @Autowired
    public DasSyncApiGatewayServiceImpl(
            ApiGatewayManager apiGatewayManager,
            DasApiBasicInfoRepository dasApiBasicInfoRepository,
            DasApiRegisterRepository dasApiRegisterRepository, DasApiCreateConfigRepository dasApiCreateConfigRepository, ApiSixConnectInfo apiSixConnectInfo) {
        this.apiGatewayManager = apiGatewayManager;
        this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
        this.dasApiRegisterRepository = dasApiRegisterRepository;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
        this.apiSixConnectInfo = apiSixConnectInfo;
    }

    @Override
    public void syncApiSixRoutesAll() {
        LOG.info("====================开始同步数据服务API至网关ApiSix!====================");
        Map<String, List<DasApiBasicInfo>> apiBasicInfoMap = getDasApiBasicInfoAll();
        singleSyncRegisterApi(apiBasicInfoMap);
        LOG.info("==========注册API同步完成!==========");
        singleSyncCreateApi(apiBasicInfoMap);
        LOG.info("==========新建API同步完成!==========");
        LOG.info("====================数据服务API同步已经完成!====================");
    }

    @Override
    public void syncApiSixRoutesRegister() {
        Map<String, List<DasApiBasicInfo>> apiBasicInfoMap = getDasApiBasicInfoAll();
        singleSyncRegisterApi(apiBasicInfoMap);
    }

    @Override
    public void syncApiSixRoutesCreate() {
        Map<String, List<DasApiBasicInfo>> apiBasicInfoMap = getDasApiBasicInfoAll();
        singleSyncCreateApi(apiBasicInfoMap);
    }

    @Override
    public void apiSixConsumersKeyAuth(ApiSixConsumerInfo apiSixConsumerInfo) {
        ConsumerContext consumerContext = new ConsumerContext();
        consumerContext.setConsumerInfo(apiSixConsumerInfo);
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_CONSUMER_URL_KEY, apiSixConnectInfo.getAdminConsumerUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        consumerContext.setParams(systemParam);
        apiGatewayManager.initConsumersAuth(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, consumerContext);
    }

    private void singleSyncRegisterApi(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap) {
        try {
            List<DasApiRegister> apiRegisterList = dasApiRegisterRepository.findAll();
            for (DasApiRegister dasApiRegister : apiRegisterList) {
                DasApiBasicInfo dasApiBasicInfo = apiBasicInfoMap.get(dasApiRegister.getApiId()).get(0);
                ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
                setRouteName(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
                apiSixRouteInfo.setStatus(Integer.parseInt(apiSixConnectInfo.getApiStatus()));
                apiSixRouteInfo.setDesc(dasApiBasicInfo.getDescription());
                setRouteUrl(dasApiRegister.getBackendPath(), apiSixRouteInfo);
                setRouteMethod(dasApiRegister.getRequestType(), apiSixRouteInfo);
//                setRouteRegisterParams(dasApiBasicInfo, dasApiRegister, apiSixRouteInfo);
                setRoutePlugins(apiSixRouteInfo);
                setRouteUpstream(dasApiRegister.getBackendHost(), dasApiRegister.getProtocolType(), apiSixRouteInfo);
                setRouteLabels(apiSixRouteInfo);
                initApiSixGatewayRoute(apiSixRouteInfo, dasApiRegister.getApiId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void singleSyncCreateApi(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap) {
        try {
            List<DasApiCreateConfig> apiCreateList = dasApiCreateConfigRepository.findAll();
            for (DasApiCreateConfig dasApiCreate : apiCreateList) {
                DasApiBasicInfo dasApiBasicInfo = apiBasicInfoMap.get(dasApiCreate.getApiId()).get(0);
                // 同步API
                ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
                setRouteName(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
                apiSixRouteInfo.setStatus(Integer.parseInt(apiSixConnectInfo.getApiStatus()));
                apiSixRouteInfo.setDesc(dasApiBasicInfo.getDescription());
                setRouteUrl(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
                setRouteMethod(dasApiBasicInfo.getRequestType(), apiSixRouteInfo);
//                setRouteCreateParams(dasApiBasicInfo, dasApiCreate, apiSixRouteInfo);
//                setRoutePlugins(apiSixRouteInfo);
                setRouteUpstream(CREATE_API_UPSTREAM_HOST_PORT, CREATE_API_UPSTREAM_PROTOCOL_TYPE, apiSixRouteInfo);
                setRouteLabels(apiSixRouteInfo);
                initApiSixGatewayRoute(apiSixRouteInfo, dasApiCreate.getApiId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, List<DasApiBasicInfo>> getDasApiBasicInfoAll() {
        // 获取所有API基础信息
        List<DasApiBasicInfo> apiBasicInfoList = dasApiBasicInfoRepository.findAll();
        return apiBasicInfoList.stream().collect(Collectors.groupingBy(DasApiBasicInfo::getApiId));
    }


    private void setRouteName(String apiPath, ApiSixRouteInfo apiSixRouteInfo) {
        //TODO 后期设置多租户,增加分类命名RouteName,或者根据数据服务维护基础信息Api名称+Url进行Name生成;
        String name = apiPath.replace("/", "_");
        String routeName = name.replaceFirst("_", "");
        apiSixRouteInfo.setName(routeName);
    }

    private void setRouteUrl(String backendPath, ApiSixRouteInfo apiSixRouteInfo) {
        List<String> uris = new ArrayList<>();
        uris.add(backendPath);
        apiSixRouteInfo.setUris(uris);
    }

    private void setRouteMethod(String requestType, ApiSixRouteInfo apiSixRouteInfo) {
        List<String> methods = new ArrayList<>();
        methods.add(requestType.toUpperCase());
        apiSixRouteInfo.setMethods(methods);
    }

    private void setRouteUpstream(String requestHostPort, String protocolType, ApiSixRouteInfo apiSixRouteInfo) {
        List<Nodes> nodes = new ArrayList<>();
        Nodes node =
                Nodes.builder()
                        .host(requestHostPort.split(":")[0])
                        .port(Integer.parseInt(requestHostPort.split(":")[1]))
                        .weight(Integer.parseInt(apiSixConnectInfo.getUpstreamWeight()))
                        .build();
        nodes.add(node);

        // TODO 后期同步流控策略设置
        Timeout timeout = Timeout.builder()
                .connect(apiSixConnectInfo.getUpstreamConnectTimeOut())
                .read(apiSixConnectInfo.getUpstreamConnectTimeOut())
                .send(apiSixConnectInfo.getUpstreamConnectTimeOut()).build();
        Upstream upstream =
                Upstream.builder()
                        .nodes(nodes)
                        .timeout(timeout)
                        .pass_host(apiSixConnectInfo.getUpstreamPassHost())
                        .scheme(protocolType.toLowerCase())
                        .type(apiSixConnectInfo.getUpstreamType())
                        .build();

        apiSixRouteInfo.setUpstream(upstream);
    }

    private void setRouteLabels(ApiSixRouteInfo apiSixRouteInfo) {
        Map<String, String> labels = new HashMap<>();
        labels.put(ApiSixConstant.API_SIX_API_VERSION_KEY, apiSixConnectInfo.getApiVersion());
        apiSixRouteInfo.setLabels(labels);
    }

    private void initApiSixGatewayRoute(ApiSixRouteInfo apiSixRouteInfo, String apiId) {
        RouteContext routeContext = new RouteContext();
        routeContext.setRouteInfo(apiSixRouteInfo);
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY, apiSixConnectInfo.getAdminRouteUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        systemParam.put(ApiSixConstant.API_SIX_ROUTE_ID, apiId);
        routeContext.setParams(systemParam);
        apiGatewayManager.initRouteService(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
    }


    private void setRoutePlugins(ApiSixRouteInfo apiSixRouteInfo) {
        Map<String, Map<String, Object>> pluginsMap = new HashMap<>();
//        setRouteRegexUrl(pluginsMap);
        Map<String, Object> keyAuthMap = new HashMap<>();
        keyAuthMap.put(ApiSixConstant.API_SIX_KEY_AUTH_POSITION_HEADER_KEY, ApiSixConstant.API_SIX_KEY_AUTH_POSITION_HEADER_VAL);
        pluginsMap.put(ApiSixConstant.API_SIX_CONSUMER_PLUGINS_KEY_AUTH, keyAuthMap);
        apiSixRouteInfo.setPlugins(pluginsMap);
    }

    private void setRouteRegexUrl(Map<String, Map<String, Object>> pluginsMap) {
        Map<String,Object> regexUrisMap = new HashMap<>();
        List<String> regexUris = new ArrayList<>();
        regexUris.add("^/das/(.*)");
        regexUris.add("/$1");
        regexUrisMap.put("regex_uri", regexUris);
        pluginsMap.put("proxy-rewrite", regexUrisMap);
    }

//    ===============================ApiSix参数,Route版本的信息======================================================
//    private void setRouteRegisterParams(DasApiBasicInfo dasApiBasicInfo, DasApiRegister dasApiRegister, ApiSixRouteInfo apiSixRouteInfo) {
//        String defInputParam = dasApiBasicInfo.getDefInputParam();
//        String backendRequestParas = dasApiRegister.getBackendRequestParas();
//        if (!ObjectUtils.isEmpty(defInputParam) && !ObjectUtils.isEmpty(backendRequestParas)) {
//            List<List<String>> varsKey = new ArrayList<>();
//            List<String> varsValue = new ArrayList<>();
//            // 基础信息入参定义(默认值获取)
//            Map<String, List<DasApiBasicInfoRequestParasVO>> delParamMap = getBasicDefParamByKeyList(dasApiBasicInfo);
//            // 后端指定入参
//            List<DasApiRegisterBackendParaVO> registerRPlist =
//                    GsonUtil.fromJsonString(
//                            backendRequestParas, new TypeToken<List<DasApiRegisterBackendParaVO>>() {
//                            }.getType());
//            // 指定参数+默认值
//            for (DasApiRegisterBackendParaVO backendParaVO : registerRPlist) {
//                DasApiBasicInfoRequestParasVO dasApiBasicInfoRequestParasVO = delParamMap.get(backendParaVO.getParaName()).get(0);
//                String paraPosition = dasApiBasicInfoRequestParasVO.getParaPosition();
//                setParamPosition(varsKey, varsValue, dasApiBasicInfoRequestParasVO, paraPosition, backendParaVO.getBackendParaName());
//            }
//            apiSixRouteInfo.setVars(varsKey);
//        }
//    }
//
//    private void setRouteCreateParams(DasApiBasicInfo dasApiBasicInfo, DasApiCreateConfig dasApiCreateConfig, ApiSixRouteInfo apiSixRouteInfo) {
//        String defInputParam = dasApiBasicInfo.getDefInputParam();
//        String createApiRequestParas = dasApiCreateConfig.getApiRequestParas();
//        if (!ObjectUtils.isEmpty(defInputParam) && !ObjectUtils.isEmpty(createApiRequestParas)) {
//            List<List<String>> varsKey = new ArrayList<>();
//            List<String> varsValue = new ArrayList<>();
//            // 基础信息入参定义(默认值获取)
//            Map<String, List<DasApiBasicInfoRequestParasVO>> delParamMap = getBasicDefParamByKeyList(dasApiBasicInfo);
//            // 后端指定入参
//            List<DasApiCreateRequestParasVO> createRPlist =
//                    GsonUtil.fromJsonString(
//                            createApiRequestParas, new TypeToken<List<DasApiCreateRequestParasVO>>() {
//                            }.getType());
//            // 指定参数+默认值
//            for (DasApiCreateRequestParasVO createRequestParasVO : createRPlist) {
//                DasApiBasicInfoRequestParasVO dasApiBasicInfoRequestParasVO = delParamMap.get(createRequestParasVO.getParaName()).get(0);
//                String paraPosition = dasApiBasicInfoRequestParasVO.getParaPosition();
//                setParamPosition(varsKey, varsValue, dasApiBasicInfoRequestParasVO, paraPosition, createRequestParasVO.getMappingName());
//            }
//            apiSixRouteInfo.setVars(varsKey);
//        }
//    }
//
//    private Map<String, List<DasApiBasicInfoRequestParasVO>> getBasicDefParamByKeyList(DasApiBasicInfo dasApiBasicInfo) {
//        // 基础信息入参定义(默认值获取)
//        String defInputParam = dasApiBasicInfo.getDefInputParam();
//        List<DasApiBasicInfoRequestParasVO> basicRPList =
//                GsonUtil.fromJsonString(defInputParam, new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
//                }.getType());
//        return basicRPList.stream()
//                .collect(Collectors.groupingBy(DasApiBasicInfoRequestParasVO::getParaName));
//    }
//
//    private void setParamPosition(List<List<String>> varsKey, List<String> varsValue,
//                                  DasApiBasicInfoRequestParasVO dasApiBasicInfoRequestParasVO,
//                                  String paraPosition, String mappingName) {
//        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY
//                .getTypeName()
//                .equals(paraPosition)) {
//            varsValue.add(ApiSixConnectInfo.API_SIX_REQUEST_PARAMETER_ARG_PREFIX + mappingName);
//        }
//        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_HEADER
//                .getTypeName()
//                .equals(paraPosition)) {
//            varsValue.add(ApiSixConnectInfo.API_SIX_REQUEST_PARAMETER_HTTP_PREFIX + mappingName);
//        }
//        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_COOKIE
//                .getTypeName()
//                .equals(paraPosition)) {
//            varsValue.add(ApiSixConnectInfo.API_SIX_REQUEST_PARAMETER_COOKIE_PREFIX + mappingName);
//        }
//        varsValue.add(ApiSixConnectInfo.API_REGISTER_PARAM_SYMBOL);
//        varsValue.add(dasApiBasicInfoRequestParasVO.getDefaultValue());
//        varsKey.add(varsValue);
//    }
}
