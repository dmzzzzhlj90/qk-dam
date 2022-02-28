package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasReleaseApiService;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API发布_同步数据服务API至服务网关
 *
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/release/api")
public class DasReleaseApiController {
    private final DasReleaseApiService dasReleaseApiService;

    @Autowired
    public DasReleaseApiController(DasReleaseApiService dasReleaseApiService) {
        this.dasReleaseApiService = dasReleaseApiService;
    }

    //    /**
    //     * 同步数据服务所有的API至API_SIX
    //     *
    //     * @return DefaultCommonResult
    //     */
    //    @PostMapping("/apiSix/routes/all")
    //    public DefaultCommonResult syncApiSixRoutesAll() {
    //        dasSyncApiGatewayService.syncApiSixRoutesAll();
    //        return DefaultCommonResult.success();
    //    }

//    /**
//     * 全量同步__数据服务注册API至API_SIX (内部使用)
//     *
//     * @param upstreamId,serviceId
//     * @return DefaultCommonResult
//     */
//    @PostMapping("/apiSix/routes/register/all")
////  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.CREATE)
//    public DefaultCommonResult apiSixRoutesRegisterAll(@RequestParam("upstreamId") String upstreamId,
//                                                       @RequestParam("serviceId") String serviceId) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, dasReleaseApiService.apiSixRoutesRegisterAll(upstreamId, serviceId));
//    }

    /**
     * 同步API至API_SIX
     *
     * @param nearlyApiPath 模糊匹配路径
     * @param apiSyncType   api同步方式
     * @param apiIds      apiId集合
     * @return
     */
    @PostMapping("/sync/apiSix/routes")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.CREATE)
    public DefaultCommonResult syncApiSixRoutes(@RequestParam("nearlyApiPath") String nearlyApiPath,
                                                @RequestParam("apiSyncType") String apiSyncType,
                                                @RequestParam("apiIds") List<String> apiIds) {
      dasReleaseApiService.syncApiSixRoutes(nearlyApiPath, apiSyncType, apiIds);
        return DefaultCommonResult.success();
    }

    //    /**
    //     * 同步数据服务新建API至API_SIX
    //     *
    //     * @return DefaultCommonResult
    //     */
    //    @PostMapping("/apiSix/routes/create")
    //    public DefaultCommonResult syncApiSixRoutesCreate() {
    //        dasSyncApiGatewayService.syncApiSixRoutesCreate();
    //        return DefaultCommonResult.success();
    //    }

    /**
     * 创建ApiSix接口认证插件key-auth
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/apiSix/consumers/keyAuth")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult apiSixConsumersKeyAuth(@RequestBody ApiSixConsumerInfo apiSixConsumerInfo) {
        dasReleaseApiService.apiSixConsumersKeyAuth(apiSixConsumerInfo);
        return DefaultCommonResult.success();
    }

    /**
     * 获取ApiSix上游信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/upstream/info")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<List> apiSixUpstreamInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasReleaseApiService.apiSixUpstreamInfo());
    }

    /**
     * 获取ApiSix服务信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/service/info")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<List> apiSixServiceInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasReleaseApiService.apiSixServiceInfo());
    }

    /**
     * 批量清除ApiSix路由信息(内部使用)
     *
     * @return DefaultCommonResult
     */
    @DeleteMapping("/clear/apiSix/route/info")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DELETE)
    public DefaultCommonResult clearRouteInfo() {
        dasReleaseApiService.clearRouteInfo();
        return DefaultCommonResult.success();
    }
}
