package com.qk.dm.dataquality.dolphinapi.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调度引擎Dolphin Scheduler 流程定义相关操作
 *
 * @author wjq
 * @date 2021/11/16
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessDefinitionApiController {
    private final ProcessDefinitionApiService processDefinitionApiService;

    @Autowired
    public ProcessDefinitionApiController(ProcessDefinitionApiService processDefinitionApiService) {
        this.processDefinitionApiService = processDefinitionApiService;
    }

    /**
     * 新增规则调度_基础信息
     *
     * @param dqcSchedulerInfoVO
     * @return DefaultCommonResult
     */
    @PostMapping("/save")
    public DefaultCommonResult save(@RequestBody @Validated DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        processDefinitionApiService.save(dqcSchedulerInfoVO);
        return DefaultCommonResult.success();
    }

}
