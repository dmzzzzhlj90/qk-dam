package com.qk.dm.datacollect.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import org.springframework.web.bind.annotation.*;

/**
 * @author shenpj
 * @date 2022/4/21 16:54
 * @since 1.0.0
 */
@RestController
@RequestMapping("/scheduler/info/")
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

    @DeleteMapping("")
    public DefaultCommonResult delete() {
        dctDolphinService.delete();
        return DefaultCommonResult.success();
    }

}
