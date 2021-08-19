package com.qk.dm.dataservice.manager;

import com.qk.dam.dataservice.spi.plugin.GatewayPlugin;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RouteFactory;
import com.qk.dam.dataservice.spi.route.RoutesService;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamFactory;
import com.qk.dam.dataservice.spi.upstream.UpstreamService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiGatewayManager {
  private final ConcurrentMap<String, GatewayPlugin> plugins = new ConcurrentHashMap<>();
  private RoutesService routesService = null;
  private UpstreamService upstreamService = null;

  public void addPlugin(final GatewayPlugin gatewayPlugin) {
    plugins.put(gatewayPlugin.getType(), gatewayPlugin);
  }

  public GatewayPlugin getGatewayPlugin(final String type) {
    return plugins.get(type);
  }

  public synchronized void initRouteService(final String type, RouteContext routeContext) {
    GatewayPlugin gatewayPlugin = plugins.get(type);

    if (gatewayPlugin != null) {
      RouteFactory routeFactory = gatewayPlugin.routeFactory(routeContext);
      this.routesService = routeFactory.getRoutesService();
      routesService.initRouteInfo();
      log.info("路由服务初始化完成！");
    }
  }

  public synchronized void initUpstreamService(final String type, UpstreamContext upstreamContext) {
    GatewayPlugin gatewayPlugin = plugins.get(type);

    if (gatewayPlugin != null) {

      UpstreamFactory upstreamFactory = gatewayPlugin.upstreamFactory(upstreamContext);
      this.upstreamService = upstreamFactory.getUpstreamService();
      log.info("负载信息服务初始化完成！");
    }
  }

  public RoutesService getRoutesService() {
    return routesService;
  }

  public UpstreamService getUpstreamService() {
    return upstreamService;
  }
}
