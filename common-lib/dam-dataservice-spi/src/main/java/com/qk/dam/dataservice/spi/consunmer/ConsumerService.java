package com.qk.dam.dataservice.spi.consunmer;

public interface ConsumerService {
  String UNSUPPORTED_MESSAGE = "未支持的路由信息配置";

  default void initConsumersAuth() {
    throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
  }
}
