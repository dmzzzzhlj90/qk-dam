package com.qk.plugin.dataservice.apisix;

import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamFactory;

public class ApiSixUpstreamFactory implements UpstreamFactory {
    private UpstreamContext upstreamContext;

    public ApiSixUpstreamFactory() {
    }

    public ApiSixUpstreamFactory(UpstreamContext upstreamContext) {
        this.upstreamContext = upstreamContext;
    }

    @Override
    public ApiSixUpstreamService getUpstreamService() {
        return new ApiSixUpstreamService(upstreamContext);
    }
}
