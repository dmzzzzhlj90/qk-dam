package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.service.DqcSchedulerResultDataService;
import com.qk.dm.dataquality.vo.DqcSchedulerResultParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调度结果集
 *
 * @author wjq
 * @date 2021/12/10
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/scheduler/result/")
public class DqcSchedulerResultDataController {
    private final DqcSchedulerResultDataService dqcSchedulerResultDataService;

    @Autowired
    public DqcSchedulerResultDataController(DqcSchedulerResultDataService dqcSchedulerResultDataService) {
        this.dqcSchedulerResultDataService = dqcSchedulerResultDataService;
    }


    /**
     * 根据规则模板获取执行Sql
     *
     * @param schedulerResultDataParamsVO
     * @return DefaultCommonResult<PageResultVO < DqcSchedulerRulesVO>>
     */
    @PostMapping("/page/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcSchedulerResultVO>> searchPageList(@RequestBody DqcSchedulerResultParamsVO schedulerResultDataParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerResultDataService.getResultDataList(schedulerResultDataParamsVO));
    }

}