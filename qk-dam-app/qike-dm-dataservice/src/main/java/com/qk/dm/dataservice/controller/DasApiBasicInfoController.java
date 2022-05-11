package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Map;

/**
 * 数据服务_API基础信息
 *
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/basic/info")
public class DasApiBasicInfoController {
    private final DasApiBasicInfoService dasApiBasicInfoService;

    @Autowired
    public DasApiBasicInfoController(DasApiBasicInfoService dasApiBasicInfoService) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
    }

    /**
     * 统一查询API基础信息入口
     *
     * @param dasApiBasicInfoParamsVO
     * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
     */
    @PostMapping(value = "/list")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DasApiBasicInfoVO>> searchList(@RequestBody DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.searchList(dasApiBasicInfoParamsVO));
    }

    /**
     * 新增API基础信息
     *
     * @param dasApiBasicInfoVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DasApiBasicInfoVO dasApiBasicInfoVO) {
        dasApiBasicInfoService.insert(dasApiBasicInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑API基础信息
     *
     * @param dasApiBasicInfoVO
     * @return DefaultCommonResult
     */
    @PutMapping("/update")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DasApiBasicInfoVO dasApiBasicInfoVO) {
        dasApiBasicInfoService.update(dasApiBasicInfoVO);
        return DefaultCommonResult.success();
    }


    /**
     * 批量删除API,根据API类型关联删除API附属信息
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/bulk/{ids}")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.DELETE)
    public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
        dasApiBasicInfoService.deleteBulk(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 获取API类型
     *
     * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
     */
    @GetMapping(value = "/query/api/type")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> getApiType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getApiType());
    }

    /**
     * 获取取数方式
     *
     * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
     */
    @GetMapping(value = "/create/api/type/info")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> createTypeInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.createTypeInfo());
    }

    /**
     * API基础信息__入参定义表头
     *
     * @return DefaultCommonResult<Map < String, String>>
     */
    @GetMapping(value = "/query/request/paras/header/infos")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getRequestParasHeaderInfos() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getRequestParasHeaderInfos());
    }

    /**
     * API基础信息__参数位置下拉列表
     *
     * @return DefaultCommonResult<Map < String, String>>
     */
    @GetMapping(value = "/query/request/param/positions")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> getRequestParamsPositions() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getRequestParamsPositions());
    }

    /**
     * API基础信息__状态信息
     *
     * @return DefaultCommonResult<Map < String, String>>
     */
    @GetMapping(value = "/status/info")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> getStatusInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getStatusInfo());
    }

    /**
     * 数据类型
     *
     * @return DefaultCommonResult<Map < String, String>>
     */
    @GetMapping(value = "/data/type")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> getDataType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getDataType());
    }

    /**
     * API同步方式
     *
     * @return DefaultCommonResult<Map < String, String>>
     */
    @GetMapping(value = "/sync/type")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> getSyncType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getSyncType());
    }

    /**
     * API调试__请求参数表头信息
     *
     * @return DefaultCommonResult<Map < String, String>>
     */
    @GetMapping(value = "/debug/para/header/infos")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getDebugParamHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiBasicInfoService.getDebugParamHeaderInfo());
    }

}
