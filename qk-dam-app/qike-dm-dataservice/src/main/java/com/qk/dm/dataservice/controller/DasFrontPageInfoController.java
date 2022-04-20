package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasFrontPageInfoService;
import com.qk.dm.dataservice.vo.DasFrontPageTrendInfoDataVO;
import com.qk.dm.dataservice.vo.DasReleaseTrendParamsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 数据服务_首页信息
 *
 * @author wjq
 * @date 2022/03/11
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/front/page")
public class DasFrontPageInfoController {
    private final DasFrontPageInfoService dasFrontPageInfoService;

    @Autowired
    public DasFrontPageInfoController(DasFrontPageInfoService dasFrontPageInfoService) {
        this.dasFrontPageInfoService = dasFrontPageInfoService;
    }

    /**
     * 日期频次
     *
     * @return DefaultCommonResult<Map < String, String>>
     */
    @GetMapping(value = "/date/frequency")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.GET)
    public DefaultCommonResult<Map<String, String>> getDateFrequency() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasFrontPageInfoService.getDateFrequency());
    }

    /**
     * 发布趋势
     *
     * @param dasReleaseTrendParamsVO
     * @return DefaultCommonResult<PageResultVO < DasApiBasicInfoVO>>
     */
    @PostMapping(value = "/release/trend")
//  @Auth(bizType = BizResource.DAS_API_BASIC_INFO, actionType = RestActionType.LIST)
    public DefaultCommonResult<DasFrontPageTrendInfoDataVO> releaseTrend(@RequestBody DasReleaseTrendParamsVO dasReleaseTrendParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasFrontPageInfoService.releaseTrend(dasReleaseTrendParamsVO));
    }


}
