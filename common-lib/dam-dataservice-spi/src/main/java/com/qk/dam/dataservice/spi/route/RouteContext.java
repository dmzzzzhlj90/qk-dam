package com.qk.dam.dataservice.spi.route;

import lombok.Builder;

import java.util.Map;

@Builder
public class RouteContext {
    private RouteInfo routeInfo;
    private Map<String, String> params;
}
