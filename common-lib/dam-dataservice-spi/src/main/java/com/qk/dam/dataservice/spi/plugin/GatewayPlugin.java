package com.qk.dam.dataservice.spi.plugin;

import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.consunmer.ConsumerFactory;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RouteFactory;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamFactory;

public interface GatewayPlugin {
  String getType();

  RouteFactory routeFactory(RouteContext routeContext);

  UpstreamFactory upstreamFactory(UpstreamContext routeContext);

  ConsumerFactory consumerFactory(ConsumerContext consumerContext);
}
