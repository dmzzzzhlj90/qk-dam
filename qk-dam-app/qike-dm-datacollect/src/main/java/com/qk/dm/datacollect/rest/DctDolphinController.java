package com.qk.dm.datacollect.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import com.qk.dm.datacollect.vo.DctSchedulerInfoVO;
import com.qk.dm.datacollect.vo.DctSchedulerReleaseVO;
import com.qk.dm.datacollect.vo.DctSchedulerInfoParamsVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author shenpj
 * @date 2022/4/21 16:54
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dolphin/process")
public class DctDolphinController {
    private final DctDolphinService dctDolphinService;

    public DctDolphinController(DctDolphinService dctDolphinService) {
        this.dctDolphinService = dctDolphinService;
    }

    /**
     * 新增流程定义
     * @param dctSchedulerBasicInfoVO
     * @return
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        dctDolphinService.insert(dctSchedulerBasicInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改流程定义
     * @param dctSchedulerBasicInfoVO
     * @return
     */
    @PutMapping("")
    public DefaultCommonResult update(@RequestBody DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        dctDolphinService.update(dctSchedulerBasicInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除流程定义
     * @param code
     * @return
     */
    @DeleteMapping("/{code}")
    public DefaultCommonResult delete(@PathVariable Long code) {
        dctDolphinService.delete(code);
        return DefaultCommonResult.success();
    }

    /**
     * 流程定义上下线
     * @param dctSchedulerReleaseVO
     * @return
     */
    @PutMapping("/release")
    public DefaultCommonResult release(@RequestBody @Valid DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        dctDolphinService.release(dctSchedulerReleaseVO);
        return DefaultCommonResult.success();
    }

    /**
     * 运行流程定义
     * @param code
     * @return
     */
    @PostMapping("/runing/{code}")
    public DefaultCommonResult runing(@PathVariable Long code) {
        dctDolphinService.runing(code);
        return DefaultCommonResult.success();
    }

    /**
     * 流程定义列表
     * @param schedulerInfoParamsVO
     * @return
     */
    @PostMapping("/page/list")
    public DefaultCommonResult<PageResultVO<DctSchedulerInfoVO>> searchPageList(@RequestBody DctSchedulerInfoParamsVO schedulerInfoParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dctDolphinService.searchPageList(schedulerInfoParamsVO));
    }

    /**
     * 流程定义详情
     * @param code
     * @return
     */
    @GetMapping("/{code}")
    public DefaultCommonResult<DctSchedulerBasicInfoVO> detail(@PathVariable Long code) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dctDolphinService.detail(code));
    }

}
