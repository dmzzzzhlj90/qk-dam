package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.service.DasFlowStrategyService;
import com.qk.dm.dataservice.vo.DasFlowStrategyParamsVO;
import com.qk.dm.dataservice.vo.DasFlowStrategyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 服务流控
 * @author zys
 * @date 2021/8/18 11:07
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/flowstrategy")
public class DataServiceApiDasFlowStrategyController {
    private final DasFlowStrategyService dasFlowStrategyService;
    @Autowired
    public DataServiceApiDasFlowStrategyController(DasFlowStrategyService dasFlowStrategyService) {
        this.dasFlowStrategyService = dasFlowStrategyService;
    }

    /**
     * 新增服务流控信息
     * @param dasFlowStrategyVO
     * @return
     */
    @PostMapping("/add")
    public DefaultCommonResult addDasFlowStrategy(
            @RequestBody @Validated DasFlowStrategyVO dasFlowStrategyVO) {
        dasFlowStrategyService.addDasFlowStrategy(dasFlowStrategyVO);
        return DefaultCommonResult.success();
    }

    /**
     * 统一查询服务流控信息
     * @param dasFlowStrategyParamsVO
     * @return
     */
    @PostMapping(value = "/query")
    public DefaultCommonResult<PageResultVO<DasFlowStrategyVO>> getDasApiDasAiManagement(
            @RequestBody DasFlowStrategyParamsVO dasFlowStrategyParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dasFlowStrategyService.getDasFlowStrategy(dasFlowStrategyParamsVO));
    }

    /**
     * 修改服务流控信息
     * @param dasFlowStrategyVO
     * @return
     */
    @PostMapping("/update")
    public DefaultCommonResult updateDasFlowStrategy(
            @RequestBody @Validated DasFlowStrategyVO dasFlowStrategyVO) {
        dasFlowStrategyService.updateDasFlowStrategy(dasFlowStrategyVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除服务流控信息
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDasFlowStrategy(@PathVariable("id") String id) {
        dasFlowStrategyService.deleteDasFlowStrategy(Long.valueOf(id));
        return DefaultCommonResult.success();
    }

    /**
     * 批量删除服务流控信息
     * @param ids
     * @return
     */
    @DeleteMapping("/bulk/delete/{ids}")
    public DefaultCommonResult bulkDeleteDasFlowStrategy(@PathVariable("ids") String ids) {
        dasFlowStrategyService.bulkDeleteDasFlowStrategy(ids);
        return DefaultCommonResult.success();
    }


}