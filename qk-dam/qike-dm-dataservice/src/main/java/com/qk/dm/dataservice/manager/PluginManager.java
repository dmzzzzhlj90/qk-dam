package com.qk.dm.dataservice.manager;

import com.google.common.collect.ImmutableList;
import com.qk.dam.dataservice.spi.plugin.GatewayPlugin;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class PluginManager {
    private final ApiGatewayManager apiGatewayManager;
    private final AtomicBoolean pluginsLoaded = new AtomicBoolean();
    private final AtomicBoolean pluginsLoading = new AtomicBoolean();

    public PluginManager(ApiGatewayManager apiGatewayManager) {
        this.apiGatewayManager = apiGatewayManager;
    }

    public void loadPlugins() {
        if (!this.pluginsLoading.compareAndSet(false, true)) {
            return;
        }
        final ServiceLoader<GatewayPlugin> serviceLoader =
                ServiceLoader.load(GatewayPlugin.class);
        final List<GatewayPlugin> gatewayPlugins = ImmutableList.copyOf(serviceLoader);

        if (gatewayPlugins.isEmpty()) {
            log.warn("未发现有引入的网关插件 {}", GatewayPlugin.class.getName());
        }

        for (GatewayPlugin gatewayPlugin : gatewayPlugins) {
            log.info("安装插件 {}", gatewayPlugin.getClass().getName());
            this.installPlugin(gatewayPlugin);
            log.info("-- 完成插件加载 {} --", gatewayPlugin.getClass().getName());
        }

        this.pluginsLoaded.set(true);
    }

    private void installPlugin(final GatewayPlugin gatewayPlugin) {
        apiGatewayManager.addPlugin(gatewayPlugin);
    }
}
