package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasReleaseApiParamsVO;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * API发布_同步数据服务API至服务网关
 *
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Service
public interface DasReleaseApiService {


    void syncApiSixRoutes(DasReleaseApiParamsVO dasReleaseApiParamsVO);

    void createApiSixConsumerKeyAuthPlugin(ApiSixConsumerInfo apiSixConsumerInfo);

    List<Map<String, String>> searchApiSixUpstreamInfo();

    List<Map<String, String>> searchApiSixServiceInfo();

    void clearRouteInfo();

}
