package com.qk.dam.dataservice.spi.route;

import java.util.List;

public interface RoutesService {
  String UNSUPPORTED_MESSAGE = "未支持的路由信息配置";

  default void initRouteInfo() {
    throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
  }

  List getRouteInfo();

  void clearRoute();

  void deleteRouteByRouteId();
}
