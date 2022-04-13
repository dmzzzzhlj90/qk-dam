package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiCreateConfigService;
import com.qk.dm.dataservice.vo.DasApiCreateConfigVO;
import com.qk.dm.dataservice.vo.DebugApiResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 数据服务_新建API_配置方式
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/create/config")
public class DasApiCreateConfigController {
    private final DasApiCreateConfigService dasApiCreateConfigService;

    @Autowired
    public DasApiCreateConfigController(DasApiCreateConfigService dasApiCreateConfigService) {
        this.dasApiCreateConfigService = dasApiCreateConfigService;
    }

    /**
     * 新增API配置方式
     *
     * @param dasApiCreateConfigVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DasApiCreateConfigVO dasApiCreateConfigVO) {
        dasApiCreateConfigService.insert(dasApiCreateConfigVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑API配置方式
     *
     * @param dasApiCreateConfigVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DasApiCreateConfigVO dasApiCreateConfigVO) {
        dasApiCreateConfigService.update(dasApiCreateConfigVO);
        return DefaultCommonResult.success();
    }

    /**
     * 新建API__请求参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/request/param/header/info")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getRequestParamHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getRequestParamHeaderInfo());
    }

    /**
     * 新建API__返回参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/response/param/header/infos")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getResponseParamHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getResponseParamHeaderInfo());
    }

    /**
     * 新建API__排序参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/order/param/header/info")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getOrderParamHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getOrderParamHeaderInfo());
    }

    /**
     * 新建API__参数操作比较符号
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/param/compare/symbol")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<List<String>> getParamCompareSymbol() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getParamCompareSymbol());
    }

    /**
     * 新建API__排序方式
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/param/sort/style")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> getParamSortStyle() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getParamSortStyle());
    }

    /**
     * 新建API__配置方式__参数设置__表头信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/param/header/info")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getParamHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getParamHeaderInfo());
    }

    /**
     * 新建API__配置方式__调试接口功能
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/debug/model")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult debugModel(@RequestBody DasApiCreateConfigVO dasApiCreateConfigVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.debugModel(dasApiCreateConfigVO));
    }

}
