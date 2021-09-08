package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiRouteService;
import com.qk.dm.dataservice.vo.DasApiRouteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据服务_API路由Route匹配
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/route")
public class DasApiRouteController {
    private final DasApiRouteService dasApiRouteService;

    @Autowired
    public DasApiRouteController(DasApiRouteService dasApiRouteService) {
        this.dasApiRouteService = dasApiRouteService;
    }


    /**
     * 查询所有API路由匹配信息
     *
     * @return DefaultCommonResult<List < DaasApiTreeVO>>
     */
    @GetMapping("/info/all")
    public DefaultCommonResult<List<DasApiRouteVO>> getDasApiRouteInfoAll() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiRouteService.getDasApiRouteInfoAll());
    }

    /**
     * 新增API路由匹配信息
     *
     * @param dasApiRouteVO
     * @return DefaultCommonResult
     */
    @PostMapping("/add")
    public DefaultCommonResult addDasApiRoute(@RequestBody DasApiRouteVO dasApiRouteVO) {
        dasApiRouteService.addDasApiRoute(dasApiRouteVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑API路由匹配信息
     *
     * @param dasApiRouteVO
     * @return DefaultCommonResult
     */
    @PutMapping("/update")
    public DefaultCommonResult updateDasApiRoute(@RequestBody DasApiRouteVO dasApiRouteVO) {
        dasApiRouteService.updateDasApiRoute(dasApiRouteVO);
        return DefaultCommonResult.success();
    }

    /**
     * 批量删除API路由匹配信息
     *
     * @param ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/bulk/delete")
    public DefaultCommonResult bulkDeleteDasApiRoute(@RequestParam("ids") List<String> ids) {
        dasApiRouteService.bulkDeleteDasApiRoute(ids);
        return DefaultCommonResult.success();
    }

}
