package com.qk.dm.dataquality.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceExecuteDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.service.DqcSchedulerInstanceService;
import com.qk.dm.dataquality.vo.SchedulerInstanceConstantsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 实例信息分页
     * @param dqcSchedulerInstanceParamsDTO
     * @return
     */
    @PostMapping("/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult search(@RequestBody DqcSchedulerInstanceParamsDTO dqcSchedulerInstanceParamsDTO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInstanceService.search(dqcSchedulerInstanceParamsDTO));
    }

    /**
     * 实例操作
     * @param instanceExecute
     * @return
     */
    @PutMapping("/execute")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
    public DefaultCommonResult execute(@RequestBody @Validated DqcSchedulerInstanceExecuteDTO instanceExecute) {
        dqcSchedulerInstanceService.execute(instanceExecute);
        return DefaultCommonResult.success();
    }

    /**
     * 获取实例信息常量信息
     * @return DefaultCommonResult<RuleTemplateConstantsVO>
     */
    @GetMapping("/constants")
    public DefaultCommonResult<SchedulerInstanceConstantsVO> getInstanceConstants() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInstanceService.getInstanceConstants());
    }



}
