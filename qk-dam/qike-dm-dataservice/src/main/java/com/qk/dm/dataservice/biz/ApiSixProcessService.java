package com.qk.dm.dataservice.biz;

import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RouteInfo;
import com.qk.dam.dataservice.spi.server.ServerContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dm.dataservice.config.ApiSixConnectInfo;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.manager.ApiGatewayManager;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * API_SIX数据信息(上游,服务,消费者,路由等)
 *
 * @author wjq
 * @date 2022/3/1
 * @since 1.0.0
 */
@Component
public class ApiSixProcessService {

    private final ApiGatewayManager apiGatewayManager;
    private final ApiSixConnectInfo apiSixConnectInfo;

    @Autowired
    public ApiSixProcessService(ApiGatewayManager apiGatewayManager,
                                ApiSixConnectInfo apiSixConnectInfo) {
        this.apiGatewayManager = apiGatewayManager;
        this.apiSixConnectInfo = apiSixConnectInfo;
    }

    /**
     * 初始化创建对应API路由信息
     *
     * @param apiSixRouteInfo
     * @param apiId
     * @param routeContext
     */
    public void initApiSixGatewayRoute(ApiSixRouteInfo apiSixRouteInfo, String apiId, RouteContext routeContext) {
        routeContext.setRouteInfo(apiSixRouteInfo);
        Map<String, String> contextParams = routeContext.getParams();
        contextParams.put(ApiSixConstant.API_SIX_ROUTE_ID, apiId);
        routeContext.setParams(contextParams);
        apiGatewayManager.initRouteService(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
    }

    /**
     * 创建ApiSix接口认证插件key-auth
     *
     * @param apiSixConsumerInfo
     */
    public void createApiSixConsumerKeyAuthPlugin(ApiSixConsumerInfo apiSixConsumerInfo) {
        ConsumerContext consumerContext = new ConsumerContext();
        consumerContext.setConsumerInfo(apiSixConsumerInfo);
        Map<String, String> systemParam = Maps.newHashMap();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_CONSUMER_URL_KEY, apiSixConnectInfo.getAdminConsumerUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        consumerContext.setParams(systemParam);
        apiGatewayManager.initConsumersAuth(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, consumerContext);
    }

    /**
     * 查询API_SIX上游信息
     *
     * @return
     */
    public List apiSixUpstreamInfoIds() {
        UpstreamContext upstreamContext = new UpstreamContext();
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_UPSTREAM_URL_KEY, apiSixConnectInfo.getAdminUpstreamUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        upstreamContext.setParams(systemParam);
        List upstreamInfoIds = apiGatewayManager.apiSixUpstreamInfoIds(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, upstreamContext);
        return upstreamInfoIds;
    }

    /**
     * 查询API_SIX服务信息
     *
     * @return
     */
    public List apiSixServiceInfoIds() {
        ServerContext serverContext = new ServerContext();
        Map<String, String> systemParam = new HashMap<>(16);
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_SERVER_URL_KEY, apiSixConnectInfo.getAdminServerUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        serverContext.setParams(systemParam);
        List serviceInfoIds = apiGatewayManager.apiSixServiceInfoIds(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, serverContext);
        return serviceInfoIds;
    }


    /**
     * 设置Route上下文参数
     *
     * @return RouteContext
     */
    public RouteContext buildRouteContext() {
        RouteContext routeContext = new RouteContext();
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY, apiSixConnectInfo.getAdminRouteUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        routeContext.setParams(systemParam);
        return routeContext;
    }

    /**
     * 获取路由ID集合
     *
     * @param routeContext
     * @return
     */
    public List<RouteData> getRouteIdList(RouteContext routeContext) {
        return apiGatewayManager.getRouteInfo(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
    }

    /**
     * 构建注册API路由对象信息
     *
     * @param dasApiBasicInfo
     * @param dasApiRegister
     * @return
     */
    public ApiSixRouteInfo buildRegisterApiSixRouteInfo(DasApiBasicInfo dasApiBasicInfo,
                                                        DasApiRegister dasApiRegister) {
        ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
        setRouteName(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
        apiSixRouteInfo.setStatus(Integer.parseInt(apiSixConnectInfo.getApiStatus()));
        apiSixRouteInfo.setDesc(dasApiBasicInfo.getApiName());
        //TODO
        apiSixRouteInfo.setUri(dasApiRegister.getBackendPath());
//        setRouteUrls(dasApiRegister.getBackendPath(), apiSixRouteInfo);
        setRouteMethod(dasApiRegister.getRequestType(), apiSixRouteInfo);
        //                setRouteRegisterParams(dasApiBasicInfo, dasApiRegister, apiSixRouteInfo);
        //                setRoutePlugins(apiSixRouteInfo);
        //                setRouteUpstream(dasApiRegister.getBackendHost(),
        // dasApiRegister.getProtocolType(), apiSixRouteInfo);
        setRouteUpstreamId(apiSixRouteInfo, apiSixConnectInfo.getUpstreamId());
        setRouteServiceId(apiSixRouteInfo, apiSixConnectInfo.getServiceId());
        setRouteLabels(apiSixRouteInfo);
        return apiSixRouteInfo;
    }


    /**
     * 构建新建API路由对象信息
     *
     * @param dasApiBasicInfo
     * @return
     */
    public ApiSixRouteInfo buildCreateApiSixRouteInfo(DasApiBasicInfo dasApiBasicInfo) {
        ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
        setRouteName(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
        apiSixRouteInfo.setStatus(Integer.parseInt(apiSixConnectInfo.getApiStatus()));
        apiSixRouteInfo.setDesc(dasApiBasicInfo.getApiName());
        //TODO
        apiSixRouteInfo.setUri(dasApiBasicInfo.getApiPath());
//        setRouteUrls(dasApiRegister.getBackendPath(), apiSixRouteInfo);
        setRouteMethod(dasApiBasicInfo.getRequestType(), apiSixRouteInfo);
        //                setRouteRegisterParams(dasApiBasicInfo, dasApiRegister, apiSixRouteInfo);
        //                setRoutePlugins(apiSixRouteInfo);
        //                setRouteUpstream(dasApiRegister.getBackendHost(),
        // dasApiRegister.getProtocolType(), apiSixRouteInfo);
        setRouteUpstreamId(apiSixRouteInfo, apiSixConnectInfo.getUpstreamId());
        setRouteServiceId(apiSixRouteInfo, apiSixConnectInfo.getServiceId());
        setRouteLabels(apiSixRouteInfo);
        return apiSixRouteInfo;
    }

    public void setRouteName(String apiPath, ApiSixRouteInfo apiSixRouteInfo) {
        // TODO 后期设置多租户,增加分类命名RouteName,或者根据数据服务维护基础信息Api名称+Url进行Name生成;
        String name = apiPath.replace("/", "_");
        String routeName = name.replaceFirst("_", "");
        apiSixRouteInfo.setName(routeName);
    }

    public void setRouteUrls(String backendPath, ApiSixRouteInfo apiSixRouteInfo) {
        List<String> uris = new ArrayList<>();
        uris.add(backendPath);
        apiSixRouteInfo.setUris(uris);
    }

    public void setRouteMethod(String requestType, ApiSixRouteInfo apiSixRouteInfo) {
        List<String> methods = new ArrayList<>();
        methods.add(requestType.toUpperCase());
        apiSixRouteInfo.setMethods(methods);
    }

    public void setRouteLabels(ApiSixRouteInfo apiSixRouteInfo) {
        Map<String, String> labels = new HashMap<>();
        labels.put(ApiSixConstant.API_SIX_API_VERSION_KEY, apiSixConnectInfo.getApiVersion());
        apiSixRouteInfo.setLabels(labels);
    }

    private void setRouteUpstreamId(ApiSixRouteInfo apiSixRouteInfo, String upstreamId) {
        apiSixRouteInfo.setUpstream_id(upstreamId);
    }

    private void setRouteServiceId(ApiSixRouteInfo apiSixRouteInfo, String serviceId) {
        apiSixRouteInfo.setService_id(serviceId);
    }

    /**
     * 获取ApiSix上游信息
     *
     * @return
     */
    public List searchApiSixUpstreamInfo() {
        UpstreamContext upstreamContext = new UpstreamContext();
        Map<String, String> systemParam = Maps.newHashMap();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_UPSTREAM_URL_KEY, apiSixConnectInfo.getAdminUpstreamUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        upstreamContext.setParams(systemParam);
        List upstreamInfoList = apiGatewayManager.getUpstreamInfo(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, upstreamContext);
        return upstreamInfoList;
    }

    /**
     * 获取ApiSix服务信息
     *
     * @return
     */
    public List searchApiSixServiceInfo() {
        ServerContext serverContext = new ServerContext();
        Map<String, String> systemParam = Maps.newHashMap();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_SERVER_URL_KEY, apiSixConnectInfo.getAdminServerUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        serverContext.setParams(systemParam);
        List serviceInfoList = apiGatewayManager.getServiceInfo(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, serverContext);
        return serviceInfoList;
    }

    /**
     * 校验API_SIX上游和服务信息
     */
    public void checkUpstreamAndService() {
        if (ObjectUtils.isEmpty(apiSixConnectInfo.getUpstreamId())) {
            throw new BizException("ApiSix上游参数: upstreamId为空!!!");
        }
        if (ObjectUtils.isEmpty(apiSixConnectInfo.getServiceId())) {
            throw new BizException("ApiSix服务参数: serviceId为空!!!");
        }
        //查询API_SIX上游信息
        List upstreamInfoIds = apiSixUpstreamInfoIds();
        if (ObjectUtils.isEmpty(upstreamInfoIds)) {
            throw new BizException("未配置ApiSix上游信息!!!");
        }

        if (!upstreamInfoIds.contains(apiSixConnectInfo.getUpstreamId())) {
            throw new BizException("ApiSix中配置的上游信息中,不包含参数: " + apiSixConnectInfo.getUpstreamId() + "!!!");
        }

        //查询API_SIX服务信息
        List serviceInfoIds = apiSixServiceInfoIds();
        if (ObjectUtils.isEmpty(serviceInfoIds)) {
            throw new BizException("未配置ApiSix服务信息!!!");
        }

        if (!serviceInfoIds.contains(apiSixConnectInfo.getServiceId())) {
            throw new BizException("ApiSix中配置的服务信息中,不包含参数: " + apiSixConnectInfo.getServiceId() + "!!!");
        }
    }

    /**
     * 根据ApiID清除对应的Route
     *
     * @param routeContext
     * @param apiId
     * @param routeInfoList
     */
    public void deleteRouteByRouteId(RouteContext routeContext, String apiId, List<RouteData> routeInfoList) {
        List<String> routeIdList = routeInfoList.stream().map(RouteData::getId).collect(Collectors.toList());
        if (routeIdList.contains(apiId)) {
            Map<String, String> systemParam = routeContext.getParams();
            systemParam.put(ApiSixConstant.API_SIX_ROUTE_ID, apiId);
            routeContext.setParams(systemParam);
            apiGatewayManager.deleteRouteByRouteId(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
        }
    }


    /**
     * 清除全部路由信息
     */
    public void clearRouteInfo() {
        apiGatewayManager.clearRouteService(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, buildRouteContext());
    }

    /**
     * 根据路由ID查询路由信息
     *
     * @param routeId
     * @return
     */
    public RouteInfo getRouteInfoById(String routeId) {
        RouteContext routeContext = buildRouteContext();
        Map<String, String> systemParam = routeContext.getParams();
        systemParam.put(ApiSixConstant.API_SIX_ROUTE_ID, routeId);
        routeContext.setParams(systemParam);
        return apiGatewayManager.getRouteInfoById(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
    }

    /**
     * 更新路由查询信息
     *
     * @param routeInfo
     */
    public void updateRoutePlugins(RouteInfo routeInfo) {
        RouteContext routeContext = buildRouteContext();
        apiGatewayManager.updateRoutePlugins(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeInfo, routeContext);
    }

    //
    // ===============================同步新建API======================================================
    //        private void singleSyncCreateApi(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap) {
    //        try {
    //            //TODO 等数据服务页面调试开发
    //            RouteContext routeContext = buildRouteContext();
    //            List<DasApiCreateConfig> apiCreateList = dasApiCreateConfigRepository.findAll();
    //            for (DasApiCreateConfig dasApiCreate : apiCreateList) {
    //                DasApiBasicInfo dasApiBasicInfo =
    // apiBasicInfoMap.get(dasApiCreate.getApiId()).get(0);
    //                // 同步API
    //                ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
    //                setRouteName(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
    //                apiSixRouteInfo.setStatus(Integer.parseInt(apiSixConnectInfo.getApiStatus()));
    //                apiSixRouteInfo.setDesc(dasApiBasicInfo.getDescription());
    //                setRouteUrl(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
    //                setRouteMethod(dasApiBasicInfo.getRequestType(), apiSixRouteInfo);
    ////                setRouteCreateParams(dasApiBasicInfo, dasApiCreate, apiSixRouteInfo);
    ////                setRoutePlugins(apiSixRouteInfo);
    ////                setRouteUpstream(CREATE_API_UPSTREAM_HOST_PORT,
    // CREATE_API_UPSTREAM_PROTOCOL_TYPE, apiSixRouteInfo);
    //                setRouteLabels(apiSixRouteInfo);
    //                initApiSixGatewayRoute(apiSixRouteInfo, dasApiCreate.getApiId(), routeContext);
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            throw new BizException(e.getMessage());
    //        }
    //        LOG.info("==========新建API同步完成!==========");
    //    }

    //
    // ===============================ApiSix上游,服务信息======================================================
    //    private void setRouteUpstream(String requestHostPort, String protocolType, ApiSixRouteInfo
    // apiSixRouteInfo) {
    //        List<Nodes> nodes = new ArrayList<>();
    //        Nodes node =
    //                Nodes.builder()
    //                        .host(requestHostPort.split(":")[0])
    //                        .port(Integer.parseInt(requestHostPort.split(":")[1]))
    //                        .weight(Integer.parseInt(apiSixConnectInfo.getUpstreamWeight()))
    //                        .build();
    //        nodes.add(node);
    //
    //        // TODO 后期同步流控策略设置
    //        Timeout timeout = Timeout.builder()
    //                .connect(apiSixConnectInfo.getUpstreamConnectTimeOut())
    //                .read(apiSixConnectInfo.getUpstreamConnectTimeOut())
    //                .send(apiSixConnectInfo.getUpstreamConnectTimeOut()).build();
    //        Upstream upstream =
    //                Upstream.builder()
    //                        .nodes(nodes)
    //                        .timeout(timeout)
    //                        .pass_host(apiSixConnectInfo.getUpstreamPassHost())
    //                        .scheme(protocolType.toLowerCase())
    //                        .type(apiSixConnectInfo.getUpstreamType())
    //                        .build();
    //
    //        apiSixRouteInfo.setUpstream(upstream);
    //    }
    //
    //    private void setRoutePlugins(ApiSixRouteInfo apiSixRouteInfo) {
    //        Map<String, Map<String, Object>> pluginsMap = new HashMap<>();
    ////        setRouteRegexUrl(pluginsMap);
    //        Map<String, Object> keyAuthMap = new HashMap<>();
    //        keyAuthMap.put(ApiSixConstant.API_SIX_KEY_AUTH_POSITION_HEADER_KEY,
    // ApiSixConstant.API_SIX_KEY_AUTH_POSITION_HEADER_VAL);
    //        pluginsMap.put(ApiSixConstant.API_SIX_CONSUMER_PLUGINS_KEY_AUTH, keyAuthMap);
    //        apiSixRouteInfo.setPlugins(pluginsMap);
    //    }
    //
    //    private void setRouteRegexUrl(Map<String, Map<String, Object>> pluginsMap) {
    //        Map<String,Object> regexUrisMap = new HashMap<>();
    //        List<String> regexUris = new ArrayList<>();
    //        regexUris.add("^/das/(.*)");
    //        regexUris.add("/$1");
    //        regexUrisMap.put("regex_uri", regexUris);
    //        pluginsMap.put("proxy-rewrite", regexUrisMap);
    //    }

    //
    // ===============================ApiSix参数,Route版本的信息======================================================
    //    private void setRouteRegisterParams(DasApiBasicInfo dasApiBasicInfo, DasApiRegister
    // dasApiRegister, ApiSixRouteInfo apiSixRouteInfo) {
    //        String defInputParam = dasApiBasicInfo.getDefInputParam();
    //        String backendRequestParas = dasApiRegister.getBackendRequestParas();
    //        if (!ObjectUtils.isEmpty(defInputParam) && !ObjectUtils.isEmpty(backendRequestParas)) {
    //            List<List<String>> varsKey = new ArrayList<>();
    //            List<String> varsValue = new ArrayList<>();
    //            // 基础信息入参定义(默认值获取)
    //            Map<String, List<DasApiBasicInfoRequestParasVO>> delParamMap =
    // getBasicDefParamByKeyList(dasApiBasicInfo);
    //            // 后端指定入参
    //            List<DasApiRegisterBackendParaVO> registerRPlist =
    //                    GsonUtil.fromJsonString(
    //                            backendRequestParas, new
    // TypeToken<List<DasApiRegisterBackendParaVO>>() {
    //                            }.getType());
    //            // 指定参数+默认值
    //            for (DasApiRegisterBackendParaVO backendParaVO : registerRPlist) {
    //                DasApiBasicInfoRequestParasVO dasApiBasicInfoRequestParasVO =
    // delParamMap.get(backendParaVO.getParaName()).get(0);
    //                String paraPosition = dasApiBasicInfoRequestParasVO.getParaPosition();
    //                setParamPosition(varsKey, varsValue, dasApiBasicInfoRequestParasVO,
    // paraPosition, backendParaVO.getBackendParaName());
    //            }
    //            apiSixRouteInfo.setVars(varsKey);
    //        }
    //    }
    //
    //    private void setRouteCreateParams(DasApiBasicInfo dasApiBasicInfo, DasApiCreateConfig
    // dasApiCreateConfig, ApiSixRouteInfo apiSixRouteInfo) {
    //        String defInputParam = dasApiBasicInfo.getDefInputParam();
    //        String createApiRequestParas = dasApiCreateConfig.getApiRequestParas();
    //        if (!ObjectUtils.isEmpty(defInputParam) && !ObjectUtils.isEmpty(createApiRequestParas))
    // {
    //            List<List<String>> varsKey = new ArrayList<>();
    //            List<String> varsValue = new ArrayList<>();
    //            // 基础信息入参定义(默认值获取)
    //            Map<String, List<DasApiBasicInfoRequestParasVO>> delParamMap =
    // getBasicDefParamByKeyList(dasApiBasicInfo);
    //            // 后端指定入参
    //            List<DasApiCreateRequestParasVO> createRPlist =
    //                    GsonUtil.fromJsonString(
    //                            createApiRequestParas, new
    // TypeToken<List<DasApiCreateRequestParasVO>>() {
    //                            }.getType());
    //            // 指定参数+默认值
    //            for (DasApiCreateRequestParasVO createRequestParasVO : createRPlist) {
    //                DasApiBasicInfoRequestParasVO dasApiBasicInfoRequestParasVO =
    // delParamMap.get(createRequestParasVO.getParaName()).get(0);
    //                String paraPosition = dasApiBasicInfoRequestParasVO.getParaPosition();
    //                setParamPosition(varsKey, varsValue, dasApiBasicInfoRequestParasVO,
    // paraPosition, createRequestParasVO.getMappingName());
    //            }
    //            apiSixRouteInfo.setVars(varsKey);
    //        }
    //    }
    //
    //    private Map<String, List<DasApiBasicInfoRequestParasVO>>
    // getBasicDefParamByKeyList(DasApiBasicInfo dasApiBasicInfo) {
    //        // 基础信息入参定义(默认值获取)
    //        String defInputParam = dasApiBasicInfo.getDefInputParam();
    //        List<DasApiBasicInfoRequestParasVO> basicRPList =
    //                GsonUtil.fromJsonString(defInputParam, new
    // TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
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
    //            varsValue.add(ApiSixConnectInfo.API_SIX_REQUEST_PARAMETER_HTTP_PREFIX +
    // mappingName);
    //        }
    //        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_COOKIE
    //                .getTypeName()
    //                .equals(paraPosition)) {
    //            varsValue.add(ApiSixConnectInfo.API_SIX_REQUEST_PARAMETER_COOKIE_PREFIX +
    // mappingName);
    //        }
    //        varsValue.add(ApiSixConnectInfo.API_REGISTER_PARAM_SYMBOL);
    //        varsValue.add(dasApiBasicInfoRequestParasVO.getDefaultValue());
    //        varsKey.add(varsValue);
    //    }
}
