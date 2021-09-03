package com.qk.plugin.dataservice.apisix;

import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.consunmer.ConsumerFactory;

public class ApiSixConsumerFactory implements ConsumerFactory {
    private ConsumerContext consumerContext;

    public ApiSixConsumerFactory() {
    }

    public ApiSixConsumerFactory(ConsumerContext consumerContext) {
        this.consumerContext = consumerContext;
    }

    @Override
    public ApiSixConsumerService getConsumerService() {
        return new ApiSixConsumerService(consumerContext);
    }
}
