package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.service.DqcSchedulerInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  数据质量_调度_实例信息
 * @author shenpengjie
 */
@Slf4j
@RestController
@RequestMapping("/scheduler/instance")
public class DqcSchedulerInstanceController {

    private final DqcSchedulerInstanceService dqcSchedulerInstanceService;

    public DqcSchedulerInstanceController(DqcSchedulerInstanceService dqcSchedulerInstanceService) {
        this.dqcSchedulerInstanceService = dqcSchedulerInstanceService;
    }

    @GetMapping("/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult search(DqcSchedulerInstanceParamsDTO dqcSchedulerInstanceParamsDTO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInstanceService.search(dqcSchedulerInstanceParamsDTO));
    }





}
