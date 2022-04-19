package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.service.DasApiLimitManageService;
import com.qk.dm.dataservice.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据服务_服务流控管理
 *
 * @author wjq
 * @date 2022/03/16
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/limit")
public class DasApiLimitManageController {
    private final DasApiLimitManageService dasApiLimitManageService;

    @Autowired
    public DasApiLimitManageController(DasApiLimitManageService dasApiLimitManageService) {
        this.dasApiLimitManageService = dasApiLimitManageService;
    }

    /**
     * 查询服务流控列表信息
     *
     * @param dasApiLimitManageParamsVO
     * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
     */
    @PostMapping(value = "/list")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DasApiLimitInfoVO>> searchList(@RequestBody DasApiLimitManageParamsVO dasApiLimitManageParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiLimitManageService.searchList(dasApiLimitManageParamsVO));
    }

    /**
     * 新增服务流控信息
     *
     * @param dasApiLimitInfoVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DasApiLimitInfoVO dasApiLimitInfoVO) {
        dasApiLimitManageService.insert(dasApiLimitInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑服务流控信息
     *
     * @param dasApiLimitInfoVO
     * @return DefaultCommonResult
     */
    @PutMapping("/update")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DasApiLimitInfoVO dasApiLimitInfoVO) {
        dasApiLimitManageService.update(dasApiLimitInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 批量删除服务流控信息
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/bulk/{ids}")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
        dasApiLimitManageService.deleteBulk(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 绑定服务流控信息
     *
     * @param apiLimitBindParamsVO
     * @return DefaultCommonResult
     */
    @PostMapping("/bind")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.UPDATE)
    public DefaultCommonResult bind(@RequestBody @Validated DasApiLimitBindParamsVO apiLimitBindParamsVO) {
        dasApiLimitManageService.bind(apiLimitBindParamsVO);
        return DefaultCommonResult.success();
    }

    /**
     * API组路由组列表
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/group/routes")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.UPDATE)
    public DefaultCommonResult<List<DasApiGroupRouteVO>> routes() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiLimitManageService.routes());
    }

    /**
     * 时间单位
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/timeUnit")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.UPDATE)
    public DefaultCommonResult<Map<String, String>> timeUnit() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiLimitManageService.timeUnit());
    }

    /**
     * 根据limitId查询绑定API组路由信息
     *
     * @param limitId 流控ID
     * @return
     */
    @GetMapping("/bind/info")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.UPDATE)
    public DefaultCommonResult<List<DasApiLimitBindInfoVO>> searchBindInfo(@RequestParam("limitId") Long limitId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiLimitManageService.searchBindInfo(limitId));
    }

}
