package com.qk.plugin.dataservice.apisix;

import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.consunmer.ConsumerFactory;
import com.qk.dam.dataservice.spi.plugin.GatewayPlugin;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RouteFactory;
import com.qk.dam.dataservice.spi.server.ServerContext;
import com.qk.dam.dataservice.spi.server.ServerFactory;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamFactory;

public class ApiSixGatewayPlugin implements GatewayPlugin {
  private static final String GATEWAY_TYPE = "ApiSix";

  @Override
  public String getType() {
    return GATEWAY_TYPE;
  }

  @Override
  public RouteFactory routeFactory(RouteContext routeContext) {
    return new ApiSixRouteFactory(routeContext);
  }

  @Override
  public UpstreamFactory upstreamFactory(UpstreamContext routeContext) {
    return new ApiSixUpstreamFactory(routeContext);
  }

  @Override
  public ConsumerFactory consumerFactory(ConsumerContext consumerContext) {
    return new ApiSixConsumerFactory(consumerContext);
  }

  @Override
  public ServerFactory serverFactory(ServerContext serverContext) {
    return new ApiSixServerFactory(serverContext);
  }
}
