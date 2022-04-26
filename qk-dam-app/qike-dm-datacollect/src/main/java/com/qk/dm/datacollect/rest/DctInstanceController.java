package com.qk.dm.datacollect.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.service.DctInstanceService;
import com.qk.dm.datacollect.util.DateUtil;
import com.qk.dm.datacollect.util.ExportTextUtil;
import com.qk.dm.datacollect.vo.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author shenpj
 * @date 2022/4/25 11:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dolphin")
public class DctInstanceController {
    private final DctInstanceService dctInstanceService;

    public DctInstanceController(DctInstanceService dctInstanceService) {
        this.dctInstanceService = dctInstanceService;
    }


    /**
     * 实例列表
     *
     * @param dctSchedulerInstanceParamsDTO
     * @return DefaultCommonResult<List < DqcProcessInstanceVO>>
     */
    @PostMapping("/instance/page/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DctProcessInstanceVO>> search(@RequestBody DctSchedulerInstanceParamsDTO dctSchedulerInstanceParamsDTO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dctInstanceService.search(dctSchedulerInstanceParamsDTO));
    }

    /**
     * 实例详情
     *
     * @param instanceId
     * @return
     */
    @GetMapping("/instance/{instanceId}")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<DctProcessInstanceVO> detail(@PathVariable Integer instanceId) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dctInstanceService.detail(instanceId));
    }

    /**
     * 实例操作
     *
     * @param instanceExecute
     * @return
     */
    @PutMapping("/instance/execute")
    public DefaultCommonResult execute(@RequestBody @Validated DctInstanceExecuteVO instanceExecute) {
        dctInstanceService.execute(instanceExecute);
        return DefaultCommonResult.success();
    }

    /**
     * 任务实例列表
     *
     * @param dctTaskInstanceParamsVO
     * @return DefaultCommonResult<PageResultVO < DctTaskInstanceVO>>
     */
    @PostMapping("/task/list")
    public DefaultCommonResult<PageResultVO<DctTaskInstanceVO>> searchTask(@RequestBody DctTaskInstanceParamsVO dctTaskInstanceParamsVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dctInstanceService.searchTask(dctTaskInstanceParamsVO));
    }

    /**
     * 下载任务实例日志
     *
     * @param response
     * @param taskInstanceId
     */
    @PostMapping("/task/log/download")
    @ResponseBody
    public void taskLogDownload(HttpServletResponse response, Integer taskInstanceId) {
        Object log = dctInstanceService.taskLogDownload(taskInstanceId);
        ExportTextUtil.writeToTxt(response, log.toString(), "任务实例日志_" + taskInstanceId + "_" + DateUtil.toStr(new Date(), "yyyyMMddHHmm"));
    }
}
