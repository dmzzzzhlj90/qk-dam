package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.service.DasApiAppManageInfoService;
import com.qk.dm.dataservice.vo.BulkDeleteParamVO;
import com.qk.dm.dataservice.vo.DasApiAppManageInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiAppManageInfoVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据服务_API应用管理
 *
 * @author wjq
 * @date 2022/03/18
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/app/manage/info")
public class DasApiAppManageInfoController {
    private final DasApiAppManageInfoService dasApiAppManageInfoService;

    @Autowired
    public DasApiAppManageInfoController(DasApiAppManageInfoService dasApiAppManageInfoService) {
        this.dasApiAppManageInfoService = dasApiAppManageInfoService;
    }

    /**
     * 查询API应用管理列表信息
     *
     * @param dasApiAppManageParamsVO
     * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
     */
    @PostMapping(value = "/list")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DasApiAppManageInfoVO>> searchList(@RequestBody DasApiAppManageInfoParamsVO dasApiAppManageParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiAppManageInfoService.searchList(dasApiAppManageParamsVO));
    }

    /**
     * 新增API应用管理信息
     *
     * @param dasApiAppManageInfoVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DasApiAppManageInfoVO dasApiAppManageInfoVO) {
        dasApiAppManageInfoService.insert(dasApiAppManageInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑API应用管理信息
     *
     * @param dasApiAppManageInfoVO
     * @return DefaultCommonResult
     */
    @PutMapping("/update")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DasApiAppManageInfoVO dasApiAppManageInfoVO) {
        dasApiAppManageInfoService.update(dasApiAppManageInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 批量删除API应用管理信息
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/bulk/{ids}")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
        dasApiAppManageInfoService.deleteBulk(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 获取ApiSix路由信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/route/info")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<List<RouteData>> searchApiSixRouteInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiAppManageInfoService.searchApiSixRouteInfo());
    }

    /**
     * 获取ApiSix上游信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/upstream/info")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<List<Map<String, String>>> searchApiSixUpstreamInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiAppManageInfoService.searchApiSixUpstreamInfo());
    }

    /**
     * 获取ApiSix服务信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/apiSix/service/info")
//  @Auth(bizType = BizResource.DAS_SYNC_API_GATEWAY, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<List<Map<String, String>>> searchApiSixServiceInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiAppManageInfoService.searchApiSixServiceInfo());
    }

    /**
     * 关联API
     *
     * @param apiGroupRoutePath
     * @return
     */
    @GetMapping(value = "/relation")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.LIST)
    public DefaultCommonResult<List<DasApiBasicInfoVO>> relationApiList(@RequestParam("apiGroupRoutePath") String apiGroupRoutePath) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiAppManageInfoService.relationApiList(apiGroupRoutePath));
    }

}
