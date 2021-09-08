package com.qk.dam.dataservice.spi.consunmer;

public interface ConsumerFactory {
    String UNSUPPORTED_MESSAGE = "未支持的路由信息配置";

    default ConsumerService getConsumerService() {
        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }
}
