package com.qk.dm.datacollect.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import com.qk.dm.datacollect.vo.DctSchedulerInfoVO;
import com.qk.dm.datacollect.vo.DctSchedulerReleaseVO;
import com.qk.dm.datacollect.vo.DqcSchedulerInfoParamsVO;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        dctDolphinService.insert(dctSchedulerBasicInfoVO);
        return DefaultCommonResult.success();
    }

    @PutMapping("")
    public DefaultCommonResult update(@RequestBody DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        dctDolphinService.update(dctSchedulerBasicInfoVO);
        return DefaultCommonResult.success();
    }

    @DeleteMapping("/{code}")
    public DefaultCommonResult delete(@PathVariable Long code) {
        dctDolphinService.delete(code);
        return DefaultCommonResult.success();
    }

    @PutMapping("/release")
    public DefaultCommonResult release(@RequestBody DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        dctDolphinService.release(dctSchedulerReleaseVO);
        return DefaultCommonResult.success();
    }

    @PostMapping("/runing/{code}")
    public DefaultCommonResult runing(@PathVariable Long code) {
        dctDolphinService.runing(code);
        return DefaultCommonResult.success();
    }

    @GetMapping("/page/list")
    public DefaultCommonResult<PageResultVO<DctSchedulerInfoVO>> searchPageList(@RequestBody DqcSchedulerInfoParamsVO schedulerInfoParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dctDolphinService.searchPageList(schedulerInfoParamsVO));
    }

    @GetMapping("/{code}")
    public DefaultCommonResult<DctSchedulerBasicInfoVO> detail(@PathVariable Long code) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,dctDolphinService.detail(code));
    }

}
