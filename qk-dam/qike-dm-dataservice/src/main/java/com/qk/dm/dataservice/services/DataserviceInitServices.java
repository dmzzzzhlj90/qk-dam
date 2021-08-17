package com.qk.dm.dataservice.services;

import com.qk.dm.dataservice.manager.PluginManager;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DataserviceInitServices implements HealthIndicator {
  private final PluginManager pluginManager;

  public DataserviceInitServices(PluginManager pluginManager) {
    this.pluginManager = pluginManager;
  }

  @Override
  public Health getHealth(boolean includeDetails) {
    return HealthIndicator.super.getHealth(includeDetails);
  }

  @Override
  public Health health() {
    return null;
  }

  @EventListener
  public void start(final ContextRefreshedEvent event) {
    try {
      pluginManager.loadPlugins();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @EventListener
  public void stop(final ContextClosedEvent event) {
    // 停止 关闭连接
  }
}
