package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasSyncApiGatewayService;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 定时调度_同步数据服务API至服务网关
 *
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/sync/api/gateway")
public class DasSyncApiGatewayController {
    private final DasSyncApiGatewayService dasSyncApiGatewayService;

    @Autowired
    public DasSyncApiGatewayController(DasSyncApiGatewayService dasSyncApiGatewayService) {
        this.dasSyncApiGatewayService = dasSyncApiGatewayService;
    }

    /**
     * 同步数据服务所有的API至API_SIX
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/apiSix/routes/all")
    public DefaultCommonResult syncApiSixRoutesAll() {
        dasSyncApiGatewayService.syncApiSixRoutesAll();
        return DefaultCommonResult.success();
    }

    /**
     * 同步数据服务注册API至API_SIX
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/apiSix/routes/register")
    public DefaultCommonResult syncApiSixRoutesRegister(@RequestParam("upstreamId") String upstreamId,
                                                        @RequestParam("serviceId") String serviceId) {
        dasSyncApiGatewayService.syncApiSixRoutesRegister(upstreamId, serviceId);
        return DefaultCommonResult.success();
    }

    /**
     * 同步数据服务新建API至API_SIX
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/apiSix/routes/create")
    public DefaultCommonResult syncApiSixRoutesCreate() {
        dasSyncApiGatewayService.syncApiSixRoutesCreate();
        return DefaultCommonResult.success();
    }

    /**
     * 创建ApiSix接口认证插件key-auth
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/apiSix/consumers/keyAuth")
    public DefaultCommonResult apiSixConsumersKeyAuth(@RequestBody ApiSixConsumerInfo apiSixConsumerInfo) {
        dasSyncApiGatewayService.apiSixConsumersKeyAuth(apiSixConsumerInfo);
        return DefaultCommonResult.success();
    }

    /**
     * 获取ApiSix上游信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/upstream/info")
    public DefaultCommonResult<List> apiSixUpstreamInfo() {
       return DefaultCommonResult.success(ResultCodeEnum.OK,dasSyncApiGatewayService.apiSixUpstreamInfo());
    }

    /**
     * 获取ApiSix服务信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/service/info")
    public DefaultCommonResult<List> apiSixServiceInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasSyncApiGatewayService.apiSixServiceInfo());
    }

}
