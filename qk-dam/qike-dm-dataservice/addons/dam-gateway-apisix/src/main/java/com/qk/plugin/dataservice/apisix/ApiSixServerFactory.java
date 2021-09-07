package com.qk.plugin.dataservice.apisix;

import com.qk.dam.dataservice.spi.server.ServerContext;
import com.qk.dam.dataservice.spi.server.ServerFactory;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;

public class ApiSixServerFactory implements ServerFactory {
    private ServerContext serverContext;

    public ApiSixServerFactory() {
    }

    public ApiSixServerFactory(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    @Override
    public ApiSixServerService getServerService() {
        return new ApiSixServerService(serverContext);
    }
}
