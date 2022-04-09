package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiRegisterService;
import com.qk.dm.dataservice.vo.DasApiRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Map;

/**
 * 数据服务_注册API
 *
 * @author wjq
 * @date 20210817
 * @since 1.0.0
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/api/register")
public class DasApiRegisterController {
    private final DasApiRegisterService dasApiRegisterService;

    @Autowired
    public DasApiRegisterController(DasApiRegisterService dasApiRegisterService) {
        this.dasApiRegisterService = dasApiRegisterService;
    }

    /**
     * 注册API_详情展示
     *
     * @param apiId
     * @return DefaultCommonResult<PageResultVO < DasApiRegisterVO>>
     */
    @GetMapping(value = "/query/by/{apiId}")
//  @Auth(bizType = BizResource.DAS_API_REGISTER, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<DasApiRegisterVO> detail(@PathVariable("apiId") String apiId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiRegisterService.detail(apiId));
    }

    /**
     * 新增注册API
     *
     * @param dasApiRegisterVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
//  @Auth(bizType = BizResource.DAS_API_REGISTER, actionType = RestActionType.CREATE)
    public DefaultCommonResult insert(@RequestBody @Validated DasApiRegisterVO dasApiRegisterVO) {
        dasApiRegisterService.insert(dasApiRegisterVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑注册API
     *
     * @param dasApiRegisterVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
//  @Auth(bizType = BizResource.DAS_API_REGISTER, actionType = RestActionType.UPDATE)
    public DefaultCommonResult update(@RequestBody @Validated DasApiRegisterVO dasApiRegisterVO) {
        dasApiRegisterService.update(dasApiRegisterVO);
        return DefaultCommonResult.success();
    }

    /**
     * 注册API__后端服务参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/backend/paras/header/infos")
//  @Auth(bizType = BizResource.DAS_API_REGISTER, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getRegisterBackendParaHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiRegisterService.getRegisterBackendParaHeaderInfo());
    }

    /**
     * 注册API__常量参数表头
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/constant/paras/header/infos")
//  @Auth(bizType = BizResource.DAS_API_REGISTER, actionType = RestActionType.GET)
    public DefaultCommonResult<LinkedList<Map<String, Object>>> getRegisterConstantParaHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiRegisterService.getRegisterConstantParaHeaderInfo());
    }
}
