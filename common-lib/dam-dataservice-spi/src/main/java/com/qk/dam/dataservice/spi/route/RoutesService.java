package com.qk.dam.dataservice.spi.route;

import com.qk.dam.dataservice.spi.pojo.RouteData;

import java.util.List;

public interface RoutesService {
  String UNSUPPORTED_MESSAGE = "未支持的路由信息配置";

  default void initRouteInfo() {
    throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
  }

  List<RouteData> getRouteInfo();

  RouteInfo getRouteInfoById();

  void clearRoute();

  void deleteRouteByRouteId();


  void updateRoutePlugins();

}
