package com.qk.plugin.dataservice.apisix;

import com.qk.dam.commons.util.RestTemplateUtils;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RoutesService;
import com.qk.plugin.dataservice.apisix.route.ApiSixResultVO;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import com.qk.plugin.dataservice.apisix.route.result.Nodes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.*;

/**
 * @author wjq
 * @date 2021/8/19 10:38
 * @since 1.0.0
 */
public class ApiSixRoutesService implements RoutesService {

  private RouteContext routeContext;

  public ApiSixRoutesService() {}

  public ApiSixRoutesService(RouteContext routeContext) {
    this.routeContext = routeContext;
  }

  @Override
  public void initRouteInfo() {
    deleteRoute();
    ApiSixRouteInfo routeInfo = (ApiSixRouteInfo) routeContext.getRouteInfo();
    HttpEntity httpEntity = setHttpEntity(routeInfo, routeContext.getParams());
    RestTemplateUtils.exchange(
        routeContext.getParams().get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY)
            + routeContext.getParams().get(ApiSixConstant.API_SIX_ROUTE_ID),
        HttpMethod.PUT,
        httpEntity,
        String.class);
  }

  @Override
  public List getRouteInfo() {
    List result = new ArrayList();
    Map<String, String> params = routeContext.getParams();
    HttpEntity httpEntity = setHttpEntity(null, params);
    ResponseEntity<ApiSixResultVO> responseEntity =
        RestTemplateUtils.exchange(
            params.get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY),
            HttpMethod.GET,
            httpEntity,
            ApiSixResultVO.class);
    if (null != responseEntity.getBody().getNode()) {
      List<Nodes> nodes = responseEntity.getBody().getNode().getNodes();
      for (Nodes node : nodes) {
        result.add(node.getValue().getId());
      }
    }
    return result;
  }

  public void deleteRoute() {
    List ids = getRouteInfo();
    if (ids.contains(routeContext.getParams().get(ApiSixConstant.API_SIX_ROUTE_ID))) {
      HttpEntity httpEntity = setHttpEntity(null, routeContext.getParams());
      RestTemplateUtils.exchange(
          routeContext.getParams().get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY)
              + routeContext.getParams().get(ApiSixConstant.API_SIX_ROUTE_ID),
          HttpMethod.DELETE,
          httpEntity,
          String.class);
    }
  }

  private HttpEntity setHttpEntity(ApiSixRouteInfo routeInfo, Map<String, String> params) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    headers.add(ApiSixConstant.API_SIX_HEAD_KEY, params.get(ApiSixConstant.API_SIX_HEAD_KEY));
    return new HttpEntity<>(routeInfo, headers);
  }
}
