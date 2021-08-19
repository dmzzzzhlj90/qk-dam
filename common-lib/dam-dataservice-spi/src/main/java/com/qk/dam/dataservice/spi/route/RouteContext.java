package com.qk.dam.dataservice.spi.route;

import java.util.Map;
import lombok.Builder;

@Builder
public class RouteContext {
  private RouteInfo routeInfo;
  private Map<String, String> params;
}
