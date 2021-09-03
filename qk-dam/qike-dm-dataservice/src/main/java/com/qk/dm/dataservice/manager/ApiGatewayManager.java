package com.qk.dm.dataservice.manager;

import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.consunmer.ConsumerFactory;
import com.qk.dam.dataservice.spi.consunmer.ConsumerService;
import com.qk.dam.dataservice.spi.plugin.GatewayPlugin;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RouteFactory;
import com.qk.dam.dataservice.spi.route.RoutesService;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamFactory;
import com.qk.dam.dataservice.spi.upstream.UpstreamService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ApiGatewayManager {
    private final ConcurrentMap<String, GatewayPlugin> plugins = new ConcurrentHashMap<>();
    private RoutesService routesService = null;
    private UpstreamService upstreamService = null;
    private ConsumerService consumerService = null;

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

    public void initConsumersAuth(String type, ConsumerContext consumerContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);

        if (gatewayPlugin != null) {
            ConsumerFactory consumerFactory = gatewayPlugin.consumerFactory(consumerContext);
            this.consumerService = consumerFactory.getConsumerService();
            consumerService.initConsumersAuth();
            log.info("消费者服务初始化完成！");
        }
    }

    public synchronized void getRouteInfo(final String type, RouteContext routeContext) {
        GatewayPlugin gatewayPlugin = plugins.get(type);

        if (gatewayPlugin != null) {
            RouteFactory routeFactory = gatewayPlugin.routeFactory(routeContext);
            this.routesService = routeFactory.getRoutesService();
            routesService.getRouteInfo();
            log.info("路由服务初始化完成！");
        }
    }

    public RoutesService getRoutesService() {
        return routesService;
    }

    public UpstreamService getUpstreamService() {
        return upstreamService;
    }


}
