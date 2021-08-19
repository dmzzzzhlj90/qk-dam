package com.qk.dam.dataservice.spi.upstream;

public interface UpstreamFactory {
  String UNSUPPORTED_MESSAGE = "未支持的负载均衡数据配置";

  default UpstreamService getUpstreamService() {
    throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
  }
}
