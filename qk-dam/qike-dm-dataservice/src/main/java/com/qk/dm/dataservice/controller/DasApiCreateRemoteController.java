package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiCreateSummaryService;
import com.qk.dm.dataservice.vo.DebugApiParasVO;
import com.qk.dm.dataservice.vo.DebugApiResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新建API 对外提供数据查询唯一入口
 *
 * @author wjq
 * @date 2022/03/08
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/remote")
public class DasApiCreateRemoteController {

    private final DasApiCreateSummaryService dasApiCreateSummaryService;

    @Autowired
    public DasApiCreateRemoteController(DasApiCreateSummaryService dasApiCreateSummaryService) {
        this.dasApiCreateSummaryService = dasApiCreateSummaryService;
    }

    /**
     * 根据apiId查询对应新建API数据
     *
     * @param apiId
     * @return DefaultCommonResult<PageResultVO < DasApiCreateVO>>
     */
    @PostMapping(value = "/search")
//  @Auth(bizType = BizResource.DAS_API_CREATE_CONFIG, actionType = RestActionType.DETAIL)
    public DefaultCommonResult<DebugApiResultVO> remoteSearchData(@RequestParam("apiId") String apiId,
                                                                  @RequestBody List<DebugApiParasVO> debugApiParasVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateSummaryService.remoteSearchData(apiId,debugApiParasVO));
    }

}
