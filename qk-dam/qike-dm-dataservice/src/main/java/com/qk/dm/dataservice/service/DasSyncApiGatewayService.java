package com.qk.dm.dataservice.service;

import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同步数据服务API至服务网关
 *
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Service
public interface DasSyncApiGatewayService {

//    void syncApiSixRoutesAll();

    int apiSixRoutesRegisterAll(String upstreamId, String serviceId);

    int apiSixRoutesRegisterByPath(String upstreamId, String serviceId, String apiPath);

//    void syncApiSixRoutesCreate();

    void apiSixConsumersKeyAuth(ApiSixConsumerInfo apiSixConsumerInfo);

    List apiSixUpstreamInfo();

    List apiSixServiceInfo();

    void clearRouteInfo();

}
