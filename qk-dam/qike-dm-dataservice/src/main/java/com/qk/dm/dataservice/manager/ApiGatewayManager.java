package com.qk.dm.dataservice.manager;

import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.consunmer.ConsumerFactory;
import com.qk.dam.dataservice.spi.consunmer.ConsumerService;
import com.qk.dam.dataservice.spi.plugin.GatewayPlugin;
import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RouteFactory;
import com.qk.dam.dataservice.spi.route.RouteInfo;
import com.qk.dam.dataservice.spi.route.RoutesService;
import com.qk.dam.dataservice.spi.server.ServerContext;
import com.qk.dam.dataservice.spi.server.ServerFactory;
import com.qk.dam.dataservice.spi.server.ServerService;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamFactory;
import com.qk.dam.dataservice.spi.upstream.UpstreamService;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ApiGatewayManager {
    private final ConcurrentMap<String, GatewayPlugin> plugins = new ConcurrentHashMap<>();
    private RoutesService routesService = null;
    private UpstreamService upstreamService = null;
    private ConsumerService consumerService = null;
    private ServerService serverService = null;

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
            log.info("======??????????????????????????????======");
        }
    }

    public synchronized void initUpstreamService(final String type, UpstreamContext upstreamContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);

        if (gatewayPlugin != null) {
            UpstreamFactory upstreamFactory = gatewayPlugin.upstreamFactory(upstreamContext);
            this.upstreamService = upstreamFactory.getUpstreamService();
            log.info("????????????????????????????????????");
        }
    }

    public void initConsumersAuth(String type, ConsumerContext consumerContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);

        if (gatewayPlugin != null) {
            ConsumerFactory consumerFactory = gatewayPlugin.consumerFactory(consumerContext);
            this.consumerService = consumerFactory.getConsumerService();
            consumerService.initConsumersAuth();
            log.info("======?????????????????????????????????======");
        }
    }

    public synchronized List<RouteData> getRouteInfo(final String type, RouteContext routeContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);

        if (gatewayPlugin != null) {
            RouteFactory routeFactory = gatewayPlugin.routeFactory(routeContext);
            this.routesService = routeFactory.getRoutesService();
            List<RouteData> routeIdList = routesService.getRouteInfo();
            log.info("======??????????????????????????????======");
            return routeIdList;
        }
        return null;
    }

    public RoutesService getRoutesService() {
        return routesService;
    }

    public UpstreamService getUpstreamService() {
        return upstreamService;
    }

    public List<Map<String,String>> getUpstreamInfo(final String type, UpstreamContext upstreamContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);
        if (gatewayPlugin != null) {
            UpstreamFactory upstreamFactory = gatewayPlugin.upstreamFactory(upstreamContext);
            this.upstreamService = upstreamFactory.getUpstreamService();
            List<Map<String,String>> upstreamInfo = upstreamService.getUpstreamInfo();
            log.info("======?????????????????????Upstream?????????======");
            return upstreamInfo;
        }
        return null;
    }

    public List<Map<String,String>> getServiceInfo(final String type, ServerContext serverContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);
        if (gatewayPlugin != null) {
            ServerFactory serverFactory = gatewayPlugin.serverFactory(serverContext);
            this.serverService = serverFactory.getServerService();
            List<Map<String,String>> serviceInfo = serverService.getServerInfo();
            log.info("======?????????????????????Service?????????======");
            return serviceInfo;
        }
        return null;
    }

    public List apiSixUpstreamInfoIds(String type, UpstreamContext upstreamContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);
        if (gatewayPlugin != null) {
            UpstreamFactory upstreamFactory = gatewayPlugin.upstreamFactory(upstreamContext);
            this.upstreamService = upstreamFactory.getUpstreamService();
            List upstreamInfoIds = upstreamService.apiSixUpstreamInfoIds();
            log.info("======?????????????????????Upstream?????????======");
            return upstreamInfoIds;
        }
        return null;
    }

    public List apiSixServiceInfoIds(String type, ServerContext serverContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);
        if (gatewayPlugin != null) {
            ServerFactory serverFactory = gatewayPlugin.serverFactory(serverContext);
            this.serverService = serverFactory.getServerService();
            List serviceInfoIds = serverService.apiSixServiceInfoIds();
            log.info("======?????????????????????Service?????????======");
            return serviceInfoIds;
        }
        return null;
    }

    public synchronized void clearRouteService(String type, RouteContext routeContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);

        if (gatewayPlugin != null) {
            RouteFactory routeFactory = gatewayPlugin.routeFactory(routeContext);
            this.routesService = routeFactory.getRoutesService();
            routesService.clearRoute();
            log.info("======?????????????????????======");
        }
    }

    public void deleteRouteByRouteId(String type, RouteContext routeContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);

        if (gatewayPlugin != null) {
            RouteFactory routeFactory = gatewayPlugin.routeFactory(routeContext);
            this.routesService = routeFactory.getRoutesService();
            routesService.deleteRouteByRouteId();
            log.info("======?????????????????????======");
        }
    }

    public RouteInfo getRouteInfoById(String type, RouteContext routeContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);

        if (gatewayPlugin != null) {
            RouteFactory routeFactory = gatewayPlugin.routeFactory(routeContext);
            this.routesService = routeFactory.getRoutesService();
            RouteInfo routeData = routesService.getRouteInfoById();
            log.info("======??????????????????????????????======");
            return routeData;
        }
        return null;
    }

    public void updateRoutePlugins(String type, RouteInfo routeInfo, RouteContext routeContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);
        routeContext.setRouteInfo(routeInfo);

        if (gatewayPlugin != null) {
            RouteFactory routeFactory = gatewayPlugin.routeFactory(routeContext);
            this.routesService = routeFactory.getRoutesService();
            routesService.updateRoutePlugins();
            log.info("======?????????????????????????????????======");
        }
    }
}
