package com.qk.plugin.dataservice.apisix;

import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RouteFactory;


public class ApiSixRouteFactory implements RouteFactory {
  private RouteContext routeContext;

  public ApiSixRouteFactory() {}

  public ApiSixRouteFactory(RouteContext routeContext) {
    this.routeContext = routeContext;
  }

  @Override
  public ApiSixRoutesService getRoutesService() {
    return new ApiSixRoutesService(routeContext);
  }
}

