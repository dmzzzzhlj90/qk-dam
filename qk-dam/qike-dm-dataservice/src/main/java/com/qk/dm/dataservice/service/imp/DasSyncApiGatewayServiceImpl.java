package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.server.ServerContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dm.dataservice.config.ApiSixConnectInfo;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.constant.SyncStatusEnum;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiRegister;
import com.qk.dm.dataservice.manager.ApiGatewayManager;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasSyncApiGatewayService;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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

//    @Override
//    public void syncApiSixRoutesAll() {
//        LOG.info("====================开始同步数据服务API至网关ApiSix!====================");
//        Map<String, List<DasApiBasicInfo>> apiBasicInfoMap = getDasApiBasicInfoAll();
//        singleSyncRegisterApi(apiBasicInfoMap, "", "");
//        singleSyncCreateApi(apiBasicInfoMap);
//        LOG.info("====================数据服务API同步已经完成!====================");
//    }

    @Override
    public int apiSixRoutesRegisterAll(String upstreamId, String serviceId) {
        LOG.info("====================开始全量同步数据服务注册API至网关ApiSix!====================");
        checkUpstreamAndService(upstreamId, serviceId);
        Map<String, List<DasApiBasicInfo>> apiBasicInfoMap = getDasApiBasicInfoAll();
        int status =singleSyncRegisterApi(apiBasicInfoMap, upstreamId, serviceId);
        LOG.info("====================数据服务注册API全量同步已经完成!====================");
        return status;
    }

    @Override
    public int apiSixRoutesRegisterByPath(String upstreamId, String serviceId, String apiPath) {
        LOG.info("====================开始增量同步数据服务注册API至网关ApiSix!====================");
        checkUpstreamAndService(upstreamId, serviceId);
        Map<String, List<DasApiBasicInfo>> apiBasicInfoMap = getDasApiBasicInfoByRegisterType();
        int status = singleSyncRegisterApiByPath(apiBasicInfoMap, upstreamId, serviceId, apiPath);
        LOG.info("====================数据服务API增量同步已经完成!====================");
        return status;
    }


//    @Override
//    public void syncApiSixRoutesCreate() {
//        Map<String, List<DasApiBasicInfo>> apiBasicInfoMap = getDasApiBasicInfoAll();
//        singleSyncCreateApi(apiBasicInfoMap);
//    }

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

    @Override
    public List apiSixUpstreamInfo() {
        UpstreamContext upstreamContext = new UpstreamContext();
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_UPSTREAM_URL_KEY, apiSixConnectInfo.getAdminUpstreamUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        upstreamContext.setParams(systemParam);
        List upstreamInfoList = apiGatewayManager.getUpstreamInfo(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, upstreamContext);
        return upstreamInfoList;
    }

    public List apiSixUpstreamInfoIds() {
        UpstreamContext upstreamContext = new UpstreamContext();
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_UPSTREAM_URL_KEY, apiSixConnectInfo.getAdminUpstreamUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        upstreamContext.setParams(systemParam);
        List upstreamInfoIds = apiGatewayManager.apiSixUpstreamInfoIds(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, upstreamContext);
        return upstreamInfoIds;
    }

    @Override
    public List apiSixServiceInfo() {
        ServerContext serverContext = new ServerContext();
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_SERVER_URL_KEY, apiSixConnectInfo.getAdminServerUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        serverContext.setParams(systemParam);
        List serviceInfoList = apiGatewayManager.getServiceInfo(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, serverContext);
        return serviceInfoList;
    }

    public List apiSixServiceInfoIds() {
        ServerContext serverContext = new ServerContext();
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_SERVER_URL_KEY, apiSixConnectInfo.getAdminServerUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        serverContext.setParams(systemParam);
        List serviceInfoIds = apiGatewayManager.apiSixServiceInfoIds(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, serverContext);
        return serviceInfoIds;
    }

    private int singleSyncRegisterApi(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap,
                                       String upstreamId,
                                       String serviceId) {
        AtomicInteger successfulNum = new AtomicInteger(0);
        AtomicInteger failNum = new AtomicInteger(0);
        try {
            RouteContext routeContext = buildRouteContext();
            //清除路由信息
            apiGatewayManager.clearRouteService(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
            Iterable<DasApiRegister> dasApiRegisterIterable =
                    dasApiRegisterRepository.findAll(QDasApiRegister.dasApiRegister.status.ne(SyncStatusEnum.REQUEST_PARAMETER_POSITION_PATH.getCode()));
            for (DasApiRegister dasApiRegister : dasApiRegisterIterable) {
                boolean flag = setRouteInfo(apiBasicInfoMap, upstreamId, serviceId, routeContext, dasApiRegister);
                updateApiRegisterStatus(dasApiRegister, flag, successfulNum, failNum);
            }
            LOG.info("成功同步注册Api个数:【{}】", successfulNum);
            LOG.info("失败同步注册Api个数:【{}】", failNum);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(e.getMessage());
        }
        return (failNum.get()) > 0 ? 0 : 1;
    }

    private boolean setRouteInfo(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap, String upstreamId, String serviceId, RouteContext routeContext, DasApiRegister dasApiRegister) {
        boolean flag = false;
        try {
            ApiSixRouteInfo apiSixRouteInfo = getApiSixRouteInfo(apiBasicInfoMap, upstreamId, serviceId, dasApiRegister);
            initApiSixGatewayRoute(apiSixRouteInfo, dasApiRegister.getApiId(), routeContext);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private int singleSyncRegisterApiByPath(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap,
                                            String upstreamId,
                                            String serviceId,
                                            String apiPath) {
        AtomicInteger successfulNum = new AtomicInteger(0);
        AtomicInteger failNum = new AtomicInteger(0);
        try {
            RouteContext routeContext = buildRouteContext();
            List<String> routeIdList = apiGatewayManager.getRouteInfo(apiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
            Iterable<DasApiRegister> dasApiRegisterIterable =
                    dasApiRegisterRepository.findAll(QDasApiRegister.dasApiRegister.backendPath.contains(apiPath)
                            .and(QDasApiRegister.dasApiRegister.status.ne(SyncStatusEnum.REQUEST_PARAMETER_POSITION_PATH.getCode())));

            for (DasApiRegister dasApiRegister : dasApiRegisterIterable) {
                boolean flag = setRouteInfoByPath(apiBasicInfoMap, upstreamId, serviceId, routeContext, routeIdList, dasApiRegister);
                updateApiRegisterStatus(dasApiRegister, flag, successfulNum, failNum);
            }
            LOG.info("成功同步注册Api个数:【{}】", successfulNum);
            LOG.info("失败同步注册Api个数:【{}】", failNum);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(e.getMessage());
        }
        return (failNum.get()) > 0 ? 0 : 1;
    }

    private void updateApiRegisterStatus(DasApiRegister dasApiRegister, boolean flag, AtomicInteger successfulNum, AtomicInteger failNum) {
        if (flag) {
            dasApiRegister.setStatus(SyncStatusEnum.REQUEST_PARAMETER_POSITION_PATH.getCode());
            dasApiRegisterRepository.saveAndFlush(dasApiRegister);
            LOG.info("同步成功,更新注册API同步状态成功,注册API路径为:【{}】", dasApiRegister.getBackendPath());
            successfulNum.getAndIncrement();
        } else {
            dasApiRegister.setStatus(SyncStatusEnum.CREATE_FAIL_SYNC.getCode());
            dasApiRegisterRepository.saveAndFlush(dasApiRegister);
            LOG.info("同步失败,更新注册API同步状态成功,注册API路径为:【{}】", dasApiRegister.getBackendPath());
            failNum.getAndIncrement();
        }
    }

    private boolean setRouteInfoByPath(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap,
                                       String upstreamId,
                                       String serviceId,
                                       RouteContext routeContext,
                                       List<String> routeIdList,
                                       DasApiRegister dasApiRegister) {
        boolean flag = false;
        try {
            //清除匹配到的Route
            deleteRouteByRouteId(routeContext, dasApiRegister, routeIdList);
            LOG.info("成功清除注册Api,Path为:【{}】", dasApiRegister.getBackendPath());
            flag = setRouteInfo(apiBasicInfoMap, upstreamId, serviceId, routeContext, dasApiRegister);
            LOG.info("成功同步注册Api,Path:【{}】", dasApiRegister.getBackendPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private RouteContext buildRouteContext() {
        RouteContext routeContext = new RouteContext();
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY, apiSixConnectInfo.getAdminRouteUrl());
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, apiSixConnectInfo.getHeadKeyValue());
        routeContext.setParams(systemParam);
        return routeContext;
    }

    private void deleteRouteByRouteId(RouteContext routeContext, DasApiRegister dasApiRegister, List<String> routeIdList) {
        if (routeIdList.contains(dasApiRegister.getApiId())) {
            Map<String, String> systemParam = routeContext.getParams();
            systemParam.put(ApiSixConstant.API_SIX_ROUTE_ID, dasApiRegister.getApiId());
            routeContext.setParams(systemParam);
            apiGatewayManager.deleteRouteByRouteId(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
        }
    }

    private ApiSixRouteInfo getApiSixRouteInfo(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap, String upstreamId, String serviceId, DasApiRegister dasApiRegister) {
        DasApiBasicInfo dasApiBasicInfo = apiBasicInfoMap.get(dasApiRegister.getApiId()).get(0);
        ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
        setRouteName(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
        apiSixRouteInfo.setStatus(Integer.parseInt(apiSixConnectInfo.getApiStatus()));
        apiSixRouteInfo.setDesc(dasApiBasicInfo.getApiName());
        setRouteUrl(dasApiRegister.getBackendPath(), apiSixRouteInfo);
        setRouteMethod(dasApiRegister.getRequestType(), apiSixRouteInfo);
        //                setRouteRegisterParams(dasApiBasicInfo, dasApiRegister, apiSixRouteInfo);
        //                setRoutePlugins(apiSixRouteInfo);
        //                setRouteUpstream(dasApiRegister.getBackendHost(), dasApiRegister.getProtocolType(), apiSixRouteInfo);
        setRouteUpstreamId(apiSixRouteInfo, upstreamId);
        setRouteServiceId(apiSixRouteInfo, serviceId);
        setRouteLabels(apiSixRouteInfo);
        return apiSixRouteInfo;
    }

    private Map<String, List<DasApiBasicInfo>> getDasApiBasicInfoAll() {
        // 获取所有API基础信息
        List<DasApiBasicInfo> apiBasicInfoList = dasApiBasicInfoRepository.findAll();
        return apiBasicInfoList.stream().collect(Collectors.groupingBy(DasApiBasicInfo::getApiId));
    }

    private Map<String, List<DasApiBasicInfo>> getDasApiBasicInfoByRegisterType() {
        Map<String, List<DasApiBasicInfo>> dasApiBasicInfoMap = new HashMap<>();
        // 获取所有注册API基础信息
        Iterable<DasApiBasicInfo> apiBasicInfoIterable =
                dasApiBasicInfoRepository.findAll(QDasApiBasicInfo.dasApiBasicInfo.apiType.eq(DasConstant.REGISTER_API_CODE));
        for (DasApiBasicInfo dasApiBasicInfo : apiBasicInfoIterable) {
            List<DasApiBasicInfo> dasApiBasicInfoList = new ArrayList<>();
            dasApiBasicInfoList.add(dasApiBasicInfo);
            dasApiBasicInfoMap.put(dasApiBasicInfo.getApiId(), dasApiBasicInfoList);
        }
        return dasApiBasicInfoMap;
    }

    private void checkUpstreamAndService(String upstreamId, String serviceId) {
        if (ObjectUtils.isEmpty(upstreamId)) {
            throw new BizException("ApiSix上游参数: upstreamId为空!!!");
        }
        if (ObjectUtils.isEmpty(serviceId)) {
            throw new BizException("ApiSix服务参数: serviceId为空!!!");
        }
        List upstreamInfoIds = apiSixUpstreamInfoIds();
        if (ObjectUtils.isEmpty(upstreamInfoIds)) {
            throw new BizException("未配置ApiSix上游信息!!!");
        }

        if (!upstreamInfoIds.contains(upstreamId)) {
            throw new BizException("ApiSix中配置的上游信息中,不包含参数: " + upstreamId + "!!!");
        }

        List serviceInfoIds = apiSixServiceInfoIds();
        if (ObjectUtils.isEmpty(serviceInfoIds)) {
            throw new BizException("未配置ApiSix服务信息!!!");
        }

        if (!serviceInfoIds.contains(serviceId)) {
            throw new BizException("ApiSix中配置的服务信息中,不包含参数: " + serviceId + "!!!");
        }
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


    private void setRouteLabels(ApiSixRouteInfo apiSixRouteInfo) {
        Map<String, String> labels = new HashMap<>();
        labels.put(ApiSixConstant.API_SIX_API_VERSION_KEY, apiSixConnectInfo.getApiVersion());
        apiSixRouteInfo.setLabels(labels);
    }

    private void initApiSixGatewayRoute(ApiSixRouteInfo apiSixRouteInfo, String apiId, RouteContext routeContext) {
        routeContext.setRouteInfo(apiSixRouteInfo);
        Map<String, String> contextParams = routeContext.getParams();
        contextParams.put(ApiSixConstant.API_SIX_ROUTE_ID, apiId);
        routeContext.setParams(contextParams);
        apiGatewayManager.initRouteService(ApiSixConnectInfo.GATEWAY_TYPE_API_SIX, routeContext);
    }


    private void setRouteUpstreamId(ApiSixRouteInfo apiSixRouteInfo, String upstreamId) {
        apiSixRouteInfo.setUpstream_id(upstreamId);
    }

    private void setRouteServiceId(ApiSixRouteInfo apiSixRouteInfo, String serviceId) {
        apiSixRouteInfo.setService_id(serviceId);
    }

//        ===============================同步新建API======================================================
//        private void singleSyncCreateApi(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap) {
//        try {
//            //TODO 等数据服务页面调试开发
//            RouteContext routeContext = buildRouteContext();
//            List<DasApiCreateConfig> apiCreateList = dasApiCreateConfigRepository.findAll();
//            for (DasApiCreateConfig dasApiCreate : apiCreateList) {
//                DasApiBasicInfo dasApiBasicInfo = apiBasicInfoMap.get(dasApiCreate.getApiId()).get(0);
//                // 同步API
//                ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
//                setRouteName(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
//                apiSixRouteInfo.setStatus(Integer.parseInt(apiSixConnectInfo.getApiStatus()));
//                apiSixRouteInfo.setDesc(dasApiBasicInfo.getDescription());
//                setRouteUrl(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
//                setRouteMethod(dasApiBasicInfo.getRequestType(), apiSixRouteInfo);
////                setRouteCreateParams(dasApiBasicInfo, dasApiCreate, apiSixRouteInfo);
////                setRoutePlugins(apiSixRouteInfo);
////                setRouteUpstream(CREATE_API_UPSTREAM_HOST_PORT, CREATE_API_UPSTREAM_PROTOCOL_TYPE, apiSixRouteInfo);
//                setRouteLabels(apiSixRouteInfo);
//                initApiSixGatewayRoute(apiSixRouteInfo, dasApiCreate.getApiId(), routeContext);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BizException(e.getMessage());
//        }
//        LOG.info("==========新建API同步完成!==========");
//    }

//        ===============================ApiSix上游,服务信息======================================================
//    private void setRouteUpstream(String requestHostPort, String protocolType, ApiSixRouteInfo apiSixRouteInfo) {
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
//        keyAuthMap.put(ApiSixConstant.API_SIX_KEY_AUTH_POSITION_HEADER_KEY, ApiSixConstant.API_SIX_KEY_AUTH_POSITION_HEADER_VAL);
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
