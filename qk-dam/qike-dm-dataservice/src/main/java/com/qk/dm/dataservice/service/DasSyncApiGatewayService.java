package com.qk.dm.dataservice.service;

import org.springframework.stereotype.Service;

/**
 * 同步数据服务API至服务网关
 *
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Service
public interface DasSyncApiGatewayService {

    void syncApiSixRoutesAll();

    void syncApiSixRoutesRegister();

    void syncApiSixRoutesCreate();

}
