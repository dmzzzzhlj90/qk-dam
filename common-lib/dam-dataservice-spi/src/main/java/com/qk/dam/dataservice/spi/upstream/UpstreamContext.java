package com.qk.dam.dataservice.spi.upstream;

import lombok.Builder;

import java.util.Map;

@Builder
public class UpstreamContext {
    private UpstreamInfo upstreamInfo;
    private Map<String, String> params;
}
