package com.qk.dm.dataservice.service.imp;

import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.manager.ApiGatewayManager;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasSyncApiGatewayService;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import com.qk.plugin.dataservice.apisix.route.entity.Labels;
import com.qk.plugin.dataservice.apisix.route.entity.Nodes;
import com.qk.plugin.dataservice.apisix.route.entity.Timeout;
import com.qk.plugin.dataservice.apisix.route.entity.Upstream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Service
public class DasSyncApiGatewayServiceImpl implements DasSyncApiGatewayService {
  private static final String GATEWAY_TYPE_API_SIX = "ApiSix";

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

  private final ApiGatewayManager apiGatewayManager;
  private final DasApiBasicInfoRepository dasApiBasicInfoRepository;
  private final DasApiRegisterRepository dasApiRegisterRepository;

  @Autowired
  public DasSyncApiGatewayServiceImpl(
      ApiGatewayManager apiGatewayManager,
      DasApiBasicInfoRepository dasApiBasicInfoRepository,
      DasApiRegisterRepository dasApiRegisterRepository) {
    this.apiGatewayManager = apiGatewayManager;
    this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
    this.dasApiRegisterRepository = dasApiRegisterRepository;
  }

  @Override
  public void syncApiSixRoutes() {
    // TODO 增量对比同步
    // 获取API
    List<DasApiBasicInfo> apiBasicInfoList = dasApiBasicInfoRepository.findAll();
    Map<String, List<DasApiBasicInfo>> apiBasicInfoMap =
        apiBasicInfoList.stream().collect(Collectors.groupingBy(DasApiBasicInfo::getApiId));
    List<DasApiRegister> apiRegisterList = dasApiRegisterRepository.findAll();

    for (DasApiRegister dasApiRegister : apiRegisterList) {
      DasApiBasicInfo dasApiBasicInfo = apiBasicInfoMap.get(dasApiRegister.getApiId()).get(0);
      // 同步API
      ApiSixRouteInfo apiSixRouteInfo = new ApiSixRouteInfo();
      apiSixRouteInfo.setName(dasApiBasicInfo.getApiName());
      apiSixRouteInfo.setStatus(Integer.parseInt(API_SIX_API_STATUS));
      apiSixRouteInfo.setDesc(dasApiBasicInfo.getDescription());
      setRouteUrl(dasApiRegister, apiSixRouteInfo);
      setRouteMethod(dasApiRegister, apiSixRouteInfo);
      //            setRouteParams(apiSixRouteInfo);
      //            setRouteRegexUrls(apiSixRouteInfo);
      setRouteUpstream(dasApiRegister, apiSixRouteInfo);
      setRouteLabels(apiSixRouteInfo);
      initApiSixGatewayRoute(apiSixRouteInfo);
    }
  }

  private void initApiSixGatewayRoute(ApiSixRouteInfo apiSixRouteInfo) {
    RouteContext routeContext = new RouteContext();
    routeContext.setRouteInfo(apiSixRouteInfo);
    Map<String, String> systemParam = new HashMap<>();
    systemParam.put(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY, API_SIX_ADMIN_ROUTE_URL);
    systemParam.put(ApiSixConstant.API_SIX_HEAD_KEY, API_SIX_HEAD_KEY_VALUE);
    routeContext.setParams(systemParam);

    apiGatewayManager.initRouteService(GATEWAY_TYPE_API_SIX, routeContext);
  }

  private void setRouteLabels(ApiSixRouteInfo apiSixRouteInfo) {
    Labels labels = Labels.builder().API_VERSION(API_SIX_API_VERSION).build();
    apiSixRouteInfo.setLabels(labels);
  }

  private void setRouteUpstream(DasApiRegister dasApiRegister, ApiSixRouteInfo apiSixRouteInfo) {
    List<Nodes> nodes = new ArrayList<>();
    Nodes node =
        Nodes.builder()
            .host(dasApiRegister.getBackendHost().split(":")[0])
            .port(Integer.parseInt(dasApiRegister.getBackendHost().split(":")[1]))
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
            .scheme(dasApiRegister.getProtocolType().toLowerCase())
            .type(API_SIX_UPSTREAM_TYPE)
            .build();

    apiSixRouteInfo.setUpstream(upstream);
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

  private void setRouteParams(ApiSixRouteInfo apiSixRouteInfo) {
    List<List<String>> varsKey = new ArrayList<>();
    List<String> varsValue = new ArrayList<>();
    varsKey.add(varsValue);
    apiSixRouteInfo.setVars(varsKey);
  }

  private void setRouteMethod(DasApiRegister dasApiRegister, ApiSixRouteInfo apiSixRouteInfo) {
    List<String> methods = new ArrayList<>();
    String requestType = dasApiRegister.getRequestType();
    methods.add(requestType);
    apiSixRouteInfo.setMethods(methods);
  }

  private void setRouteUrl(DasApiRegister dasApiRegister, ApiSixRouteInfo apiSixRouteInfo) {
    List<String> uris = new ArrayList<>();
    String backendPath = dasApiRegister.getBackendPath();
    uris.add(backendPath);
    apiSixRouteInfo.setUris(uris);
  }
}
