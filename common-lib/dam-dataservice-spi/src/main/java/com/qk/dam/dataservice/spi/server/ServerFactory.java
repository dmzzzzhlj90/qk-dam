package com.qk.dam.dataservice.spi.server;

public interface ServerFactory {
  String UNSUPPORTED_MESSAGE = "未支持的服务数据配置";

  default ServerService getServerService() {
    throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
  }
}
