package com.qk.dm.dataquality.dolphinapi.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataquality.dolphinapi.constant.SchedulerConstant;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessDefinitionDTO;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
     * @param dqcSchedulerBasicInfoVO
     * @return DefaultCommonResult
     */
    @PostMapping("/save")
    public DefaultCommonResult save(@RequestBody @Validated DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        processDefinitionApiService.save(dqcSchedulerBasicInfoVO, new HashMap<>(16), SchedulerConstant.ZERO_VALUE);
        return DefaultCommonResult.success();
    }

    /**
     * 新增规则调度_基础信息
     *
     * @param projectCode,searchVal,jobId
     * @return DefaultCommonResult
     */
    @GetMapping("/definition/info")
    public DefaultCommonResult<ProcessDefinitionDTO> queryProcessDefinitionInfo(@RequestParam("projectCode") Long projectCode,
                                                                                @RequestParam("searchVal") String searchVal,
                                                                                @RequestParam("jobId") String jobId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                processDefinitionApiService.queryProcessDefinitionInfo(projectCode, searchVal, jobId));
    }

    /**
     * 删除规则调度_基础信息
     *
     * @return DefaultCommonResult
     */
    @DeleteMapping("/definition/info")
    public DefaultCommonResult deleteProcessDefinitionInfo(@RequestParam("processDefinitionCode") Long processDefinitionCode,
                                                           @RequestParam("projectCode") Long projectCode) {
        processDefinitionApiService.delete(processDefinitionCode, projectCode);
        return DefaultCommonResult.success();
    }

    @PutMapping("/release")
    public DefaultCommonResult release(Long processDefinitionId, String releaseState) {
        processDefinitionApiService.release(processDefinitionId, releaseState);
        return DefaultCommonResult.success();
    }

    @PutMapping("/startInstance")
    public DefaultCommonResult startInstance(Long processDefinitionCode) {
        processDefinitionApiService.startInstance(processDefinitionCode);
        return DefaultCommonResult.success();
    }

    @PutMapping("/startCheck")
    public DefaultCommonResult startCheck(Long processDefinitionCode) {
        processDefinitionApiService.startCheck(processDefinitionCode);
        return DefaultCommonResult.success();
    }

//    @DeleteMapping("")
//    public DefaultCommonResult deleteOne(Integer processDefinitionId) {
//        processDefinitionApiService.deleteOne(processDefinitionId);
//        return DefaultCommonResult.success();
//    }
//
//    @GetMapping("/verifyName")
//    public DefaultCommonResult verifyName() {
//        processDefinitionApiService.verifyName("test");
//        return DefaultCommonResult.success();
//    }

}
