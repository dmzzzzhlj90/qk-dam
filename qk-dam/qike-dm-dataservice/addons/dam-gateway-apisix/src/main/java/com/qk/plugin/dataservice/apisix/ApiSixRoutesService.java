package com.qk.plugin.dataservice.apisix;

import com.qk.dam.commons.util.RestTemplateUtils;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RoutesService;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
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
    Map<String, String> params = routeContext.getParams();

    ApiSixRouteInfo routeInfo = (ApiSixRouteInfo) routeContext.getRouteInfo();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    headers.add(ApiSixConstant.API_SIX_HEAD_KEY, params.get(ApiSixConstant.API_SIX_HEAD_KEY));
    HttpEntity httpEntity = new HttpEntity<>(routeInfo, headers);
    RestTemplateUtils.exchange(
        params.get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY)
            + params.get(ApiSixConstant.API_SIX_ROUTE_ID),
        HttpMethod.PUT,
        httpEntity,
        String.class);
  }
}
