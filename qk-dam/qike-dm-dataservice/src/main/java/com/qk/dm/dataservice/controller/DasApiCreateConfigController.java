package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiCreateConfigService;
import com.qk.dm.dataservice.vo.DasApiCreateConfigVO;
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
     * 新增API配置方式_详情展示
     *
     * @param apiId
     * @return DefaultCommonResult<PageResultVO < DasApiCreateVO>>
     */
    @GetMapping(value = "/{apiId}")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<DasApiCreateConfigVO> detail(@PathVariable("apiId") String apiId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.detail(apiId));
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
    @GetMapping("/request/paras/header/info")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getRequestParaHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getRequestParaHeaderInfo());
    }

    /**
     * 新建API__返回参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/response/paras/header/infos")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getResponseParaHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getResponseParaHeaderInfo());
    }

    /**
     * 新建API__排序参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/order/paras/header/info")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getOrderParaHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getOrderParaHeaderInfo());
    }

    /**
     * 新建API__参数操作比较符号
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/paras/compare/symbol")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<List<String>> getParasCompareSymbol() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getParasCompareSymbol());
    }

    /**
     * 新建API__排序方式
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/paras/sort/style")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> getParasSortStyle() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getParasSortStyle());
    }

    /**
     * 新建API__配置方式__参数设置__表头信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/paras/header/info")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getParamHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateConfigService.getParamHeaderInfo());
    }

}
