package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dm.dataservice.constant.RequestParamPositionEnum;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreate;
import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.manager.ApiGatewayManager;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasSyncApiGatewayService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoRequestParasVO;
import com.qk.dm.dataservice.vo.DasApiCreateRequestParasVO;
import com.qk.dm.dataservice.vo.DasApiRegisterBackendParaVO;
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
    private static final Log LOG = LogFactory.get("文件下载程序");

    private static final String GATEWAY_TYPE_API_SIX = "ApiSix";
    // API_SIX请求参数前缀_请求参数
    private static final String API_SIX_REQUEST_PARAMETER_ARG_PREFIX = "arg_";
    // API_SIX请求参数前缀_HTTP 请求头
    private static final String API_SIX_REQUEST_PARAMETER_HTTP_PREFIX = "http_";
    // API_SIX请求参数前缀_Cookie
    private static final String API_SIX_REQUEST_PARAMETER_COOKIE_PREFIX = "cookie_";
    // 注册API参数操作符号默认设置为:"=="
    private static final String API_REGISTER_PARAM_SYMBOL = "==";

    @Value("${api_six.admin.route.url}")
    private String API_SIX_ADMIN_ROUTE_URL;

    @Value("${api_six.head.key.value}")
    private String API_SIX_HEAD_KEY_VALUE;

    @Value("${api_six.api.status}")
    private String API_SIX_API_STATUS;

    @Value("${api_six.api.version}")
    private String API_SIX_API_VERSION;

    @Value("${api_six.upstream.pass_host}")
    private String API_SIX_UPSTREAM_PASS_HOST;

    @Value("${api_six.upstream.type}")
    private String API_SIX_UPSTREAM_TYPE;

    @Value("${api_six.upstream.weight}")
    private String API_SIX_UPSTREAM_WEIGHT;

    @Value("${create.api.upstream.host_port}")
    private String CREATE_API_UPSTREAM_HOST_PORT;

    @Value("${create.api.upstream.protocol_type}")
    private String CREATE_API_UPSTREAM_PROTOCOL_TYPE;

    private final ApiGatewayManager apiGatewayManager;
    private final DasApiBasicInfoRepository dasApiBasicInfoRepository;
    private final DasApiRegisterRepository dasApiRegisterRepository;
    private final DasApiCreateRepository dasApiCreateRepository;

    @Autowired
    public DasSyncApiGatewayServiceImpl(
            ApiGatewayManager apiGatewayManager,
            DasApiBasicInfoRepository dasApiBasicInfoRepository,
            DasApiRegisterRepository dasApiRegisterRepository, DasApiCreateRepository dasApiCreateRepository) {
        this.apiGatewayManager = apiGatewayManager;
        this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
        this.dasApiRegisterRepository = dasApiRegisterRepository;
        this.dasApiCreateRepository = dasApiCreateRepository;
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
    public void syncApiSixRoutesAll() {
        LOG.info("====================开始同步数据服务API至网关ApiSix!====================");
        Map<String, List<DasApiBasicInfo>> apiBasicInfoMap = getDasApiBasicInfoAll();
        singleSyncRegisterApi(apiBasicInfoMap);
        LOG.info("==========注册API同步完成!==========");
        singleSyncCreateApi(apiBasicInfoMap);
        LOG.info("==========新建API同步完成!==========");
        LOG.info("====================同步已经完成!====================");
    }

    private void singleSyncRegisterApi(Map<String, List<DasApiBasicInfo>> apiBasicInfoMap) {
        try {
            List<DasApiRegister> apiRegisterList = dasApiRegisterRepository.findAll();
            for (DasApiRegister dasApiRegister : apiRegisterList) {
                DasApiBasicInfo dasApiBasicInfo = apiBasicInfoMap.get(dasApiRegister.getApiId()).get(0);
                ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
                apiSixRouteInfo.setName(dasApiBasicInfo.getApiName());
                apiSixRouteInfo.setStatus(Integer.parseInt(API_SIX_API_STATUS));
                apiSixRouteInfo.setDesc(dasApiBasicInfo.getDescription());
                setRouteUrl(dasApiRegister.getBackendPath(), apiSixRouteInfo);
                setRouteMethod(dasApiRegister.getRequestType(), apiSixRouteInfo);
                setRouteRegisterParams(dasApiBasicInfo, dasApiRegister, apiSixRouteInfo);
                //            setRouteRegexUrls(apiSixRouteInfo);
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
            List<DasApiCreate> apiDatasourceConfigList = dasApiCreateRepository.findAll();
            for (DasApiCreate dasApiDatasourceConfig : apiDatasourceConfigList) {
                DasApiBasicInfo dasApiBasicInfo = apiBasicInfoMap.get(dasApiDatasourceConfig.getApiId()).get(0);
                // 同步API
                ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
                apiSixRouteInfo.setName(dasApiBasicInfo.getApiName());
                apiSixRouteInfo.setStatus(Integer.parseInt(API_SIX_API_STATUS));
                apiSixRouteInfo.setDesc(dasApiBasicInfo.getDescription());
                setRouteUrl(dasApiBasicInfo.getApiPath(), apiSixRouteInfo);
                setRouteMethod(dasApiBasicInfo.getRequestType(), apiSixRouteInfo);
                setRouteCreateParams(dasApiBasicInfo, dasApiDatasourceConfig, apiSixRouteInfo);
                //            setRouteRegexUrls(apiSixRouteInfo);
                setRouteUpstream(CREATE_API_UPSTREAM_HOST_PORT, CREATE_API_UPSTREAM_PROTOCOL_TYPE, apiSixRouteInfo);
                setRouteLabels(apiSixRouteInfo);
                initApiSixGatewayRoute(apiSixRouteInfo, dasApiDatasourceConfig.getApiId());
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
                        .weight(Integer.parseInt(API_SIX_UPSTREAM_WEIGHT))
                        .build();
        nodes.add(node);

        // TODO 后期同步流控策略设置
        Timeout timeout = Timeout.builder().connect(30).read(30).send(30).build();
        Upstream upstream =
                Upstream.builder()
                        .nodes(nodes)
                        .timeout(timeout)
                        .pass_host(API_SIX_UPSTREAM_PASS_HOST)
                        .scheme(protocolType.toLowerCase())
                        .type(API_SIX_UPSTREAM_TYPE)
                        .build();

        apiSixRouteInfo.setUpstream(upstream);
    }

    private void setRouteLabels(ApiSixRouteInfo apiSixRouteInfo) {
        Map<String, String> labels = new HashMap<>();
        labels.put(ApiSixConstant.API_SIX_API_VERSION_KEY, API_SIX_API_VERSION);
        apiSixRouteInfo.setLabels(labels);
    }

    private void initApiSixGatewayRoute(ApiSixRouteInfo apiSixRouteInfo, String apiId) {
        RouteContext routeContext = new RouteContext();
        routeContext.setRouteInfo(apiSixRouteInfo);
        Map<String, String> systemParam = new HashMap<>();
        systemParam.put(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY, API_SIX_ADMIN_ROUTE_URL);
        systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, API_SIX_HEAD_KEY_VALUE);
        systemParam.put(ApiSixConstant.API_SIX_ROUTE_ID, apiId);
        routeContext.setParams(systemParam);
        apiGatewayManager.initRouteService(GATEWAY_TYPE_API_SIX, routeContext);
    }


    private void setRouteRegexUrls(ApiSixRouteInfo apiSixRouteInfo) {
        Map<String, Map<String, List<String>>> pluginsMap = new HashMap<>();
        Map<String, List<String>> regexUrisMap = new HashMap<>();
        List<String> regexUris = new ArrayList<>();
        regexUris.add("^/das/(.*)");
        regexUris.add("/$1");
        regexUrisMap.put("regex_uri", regexUris);
        pluginsMap.put("proxy-rewrite", regexUrisMap);
        apiSixRouteInfo.setPlugins(pluginsMap);
    }

    private void setRouteRegisterParams(DasApiBasicInfo dasApiBasicInfo, DasApiRegister dasApiRegister, ApiSixRouteInfo apiSixRouteInfo) {
        if (dasApiBasicInfo.getDefInputParam().length() > 0 && dasApiRegister.getBackendRequestParas().length() > 0) {
            List<List<String>> varsKey = new ArrayList<>();
            List<String> varsValue = new ArrayList<>();
            // 基础信息入参定义(默认值获取)
            Map<String, List<DasApiBasicInfoRequestParasVO>> delParamMap = getBasicDefParamByKeyList(dasApiBasicInfo);
            // 后端指定入参
            String backendRequestParas = dasApiRegister.getBackendRequestParas();
            List<DasApiRegisterBackendParaVO> registerRPlist =
                    GsonUtil.fromJsonString(
                            backendRequestParas, new TypeToken<List<DasApiRegisterBackendParaVO>>() {
                            }.getType());
            // 指定参数+默认值
            for (DasApiRegisterBackendParaVO backendParaVO : registerRPlist) {
                DasApiBasicInfoRequestParasVO dasApiBasicInfoRequestParasVO = delParamMap.get(backendParaVO.getParaName()).get(0);
                String paraPosition = dasApiBasicInfoRequestParasVO.getParaPosition();
                setParamPosition(varsKey, varsValue, dasApiBasicInfoRequestParasVO, paraPosition, backendParaVO.getBackendParaName());
            }
            apiSixRouteInfo.setVars(varsKey);
        }
    }

    private void setRouteCreateParams(DasApiBasicInfo dasApiBasicInfo, DasApiCreate dasApiCreate, ApiSixRouteInfo apiSixRouteInfo) {
        if (dasApiBasicInfo.getDefInputParam().length() > 0 && dasApiCreate.getApiRequestParas().length() > 0) {
            List<List<String>> varsKey = new ArrayList<>();
            List<String> varsValue = new ArrayList<>();
            // 基础信息入参定义(默认值获取)
            Map<String, List<DasApiBasicInfoRequestParasVO>> delParamMap = getBasicDefParamByKeyList(dasApiBasicInfo);
            // 后端指定入参
            String configApiRequestParas = dasApiCreate.getApiRequestParas();
            List<DasApiCreateRequestParasVO> createRPlist =
                    GsonUtil.fromJsonString(
                            configApiRequestParas, new TypeToken<List<DasApiCreateRequestParasVO>>() {
                            }.getType());
            // 指定参数+默认值
            for (DasApiCreateRequestParasVO createRequestParasVO : createRPlist) {
                DasApiBasicInfoRequestParasVO dasApiBasicInfoRequestParasVO = delParamMap.get(createRequestParasVO.getParaName()).get(0);
                String paraPosition = dasApiBasicInfoRequestParasVO.getParaPosition();
                setParamPosition(varsKey, varsValue, dasApiBasicInfoRequestParasVO, paraPosition, createRequestParasVO.getMappingName());
            }
            apiSixRouteInfo.setVars(varsKey);
        }
    }

    private Map<String, List<DasApiBasicInfoRequestParasVO>> getBasicDefParamByKeyList(DasApiBasicInfo dasApiBasicInfo) {
        // 基础信息入参定义(默认值获取)
        String defInputParam = dasApiBasicInfo.getDefInputParam();
        List<DasApiBasicInfoRequestParasVO> basicRPList =
                GsonUtil.fromJsonString(defInputParam, new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
                }.getType());
        return basicRPList.stream()
                .collect(Collectors.groupingBy(DasApiBasicInfoRequestParasVO::getParaName));
    }

    private void setParamPosition(List<List<String>> varsKey, List<String> varsValue,
                                  DasApiBasicInfoRequestParasVO dasApiBasicInfoRequestParasVO,
                                  String paraPosition, String mappingName) {
        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_QUERY
                .getTypeName()
                .equals(paraPosition)) {
            varsValue.add(API_SIX_REQUEST_PARAMETER_ARG_PREFIX + mappingName);
        }
        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_HEADER
                .getTypeName()
                .equals(paraPosition)) {
            varsValue.add(API_SIX_REQUEST_PARAMETER_HTTP_PREFIX + mappingName);
        }
        if (RequestParamPositionEnum.REQUEST_PARAMETER_POSITION_COOKIE
                .getTypeName()
                .equals(paraPosition)) {
            varsValue.add(
                    API_SIX_REQUEST_PARAMETER_COOKIE_PREFIX + mappingName);
        }
        varsValue.add(API_REGISTER_PARAM_SYMBOL);
        varsValue.add(dasApiBasicInfoRequestParasVO.getDefaultValue());
        varsKey.add(varsValue);
    }
}
