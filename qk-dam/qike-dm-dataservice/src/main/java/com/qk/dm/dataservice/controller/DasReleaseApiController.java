package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasReleaseApiService;
import com.qk.dm.dataservice.vo.DasReleaseApiParamsVO;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 同步API至API_SIX
     *
     * @param dasReleaseApiParamsVO 发布API参数信息VO
     * @return
     */
    @PostMapping("/sync/apiSix/routes")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.CREATE)
    public DefaultCommonResult syncApiSixRoutes(@RequestBody DasReleaseApiParamsVO dasReleaseApiParamsVO) {
        dasReleaseApiService.syncApiSixRoutes(dasReleaseApiParamsVO);
        return DefaultCommonResult.success();
    }

    /**
     * 创建ApiSix接口认证插件key-auth
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/apiSix/consumer/keyAuth/plugin")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult createApiSixConsumerKeyAuthPlugin(@RequestBody ApiSixConsumerInfo apiSixConsumerInfo) {
        dasReleaseApiService.createApiSixConsumerKeyAuthPlugin(apiSixConsumerInfo);
        return DefaultCommonResult.success();
    }

    /**
     * 获取ApiSix上游信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/upstream/info")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<List<Map<String, String>>> searchApiSixUpstreamInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasReleaseApiService.searchApiSixUpstreamInfo());
    }

    /**
     * 获取ApiSix服务信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/service/info")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<List<Map<String,String>>> searchApiSixServiceInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasReleaseApiService.searchApiSixServiceInfo());
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
