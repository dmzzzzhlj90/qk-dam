package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiCreateSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据服务_新建API公共接口
 *
 * @author wjq
 * @date 2022/03/08
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/create/summary")
public class DasApiCreateSummaryController {

    private final DasApiCreateSummaryService dasApiCreateSummaryService;

    @Autowired
    public DasApiCreateSummaryController(DasApiCreateSummaryService dasApiCreateSummaryService) {
        this.dasApiCreateSummaryService = dasApiCreateSummaryService;
    }

    /**
     * 新增API__详情展示
     *
     * @param apiId
     * @return DefaultCommonResult<PageResultVO < DasApiCreateVO>>
     */
    @GetMapping(value = "/detail/{apiId}")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<Object> detail(@PathVariable("apiId") String apiId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateSummaryService.detail(apiId));
    }

}
