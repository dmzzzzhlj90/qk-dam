package com.qk.dam.dataservice.spi.route;

public interface RouteFactory {
  String UNSUPPORTED_MESSAGE = "未支持的路由信息配置";

  default RoutesService getRoutesService() {
    throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
  }
}
