package com.qk.dm.dataquality.dolphinapi.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
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
        processDefinitionApiService.save(dqcSchedulerBasicInfoVO, new HashMap<>());
        return DefaultCommonResult.success();
    }

    /**
     * 新增规则调度_基础信息
     *
     * @param projectName,searchVal,jobId
     * @return DefaultCommonResult
     */
    @GetMapping("/definition/info")
    public DefaultCommonResult<ProcessDefinitionDTO> queryProcessDefinitionInfo(@RequestParam("projectName") String projectName,
                                                                                @RequestParam("searchVal") String searchVal,
                                                                                @RequestParam("jobId") String jobId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                processDefinitionApiService.queryProcessDefinitionInfo(projectName, searchVal, jobId));
    }

//    @PutMapping("/release")
//    public DefaultCommonResult release(Integer processDefinitionId, Integer releaseState) {
//        processDefinitionApiService.release(processDefinitionId, releaseState);
//        return DefaultCommonResult.success();
//    }
//
//    @PutMapping("/startInstance")
//    public DefaultCommonResult startInstance(Integer processDefinitionId) {
//        processDefinitionApiService.startInstance(processDefinitionId);
//        return DefaultCommonResult.success();
//    }
//
//    @PutMapping("/startCheck")
//    public DefaultCommonResult startCheck(Integer processDefinitionId) {
//        processDefinitionApiService.startCheck(processDefinitionId);
//        return DefaultCommonResult.success();
//    }
//
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
