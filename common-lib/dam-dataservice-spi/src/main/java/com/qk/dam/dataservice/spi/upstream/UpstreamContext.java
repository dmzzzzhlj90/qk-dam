package com.qk.dam.dataservice.spi.upstream;

import java.util.Map;
import lombok.Builder;

@Builder
public class UpstreamContext {
  private UpstreamInfo upstreamInfo;
  private Map<String, String> params;
}
