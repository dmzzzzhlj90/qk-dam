package com.qk.dm.dataquality.rest;

import cn.hutool.core.date.DateUtil;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceExecuteDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerTaskInstanceLogDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerTaskInstanceParamsDTO;
import com.qk.dm.dataquality.service.DqcSchedulerInstanceService;
import com.qk.dm.dataquality.utils.ExportTextUtil;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.DqcProcessTaskInstanceVO;
import com.qk.dm.dataquality.vo.SchedulerInstanceConstantsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 数据质量_调度_实例信息
 *
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
     * 实例列表
     *
     * @param dqcSchedulerInstanceParamsDTO
     * @return DefaultCommonResult<List < DqcProcessInstanceVO>>
     */
    @PostMapping("/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcProcessInstanceVO>> search(@RequestBody DqcSchedulerInstanceParamsDTO dqcSchedulerInstanceParamsDTO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInstanceService.search(dqcSchedulerInstanceParamsDTO));
    }

    /**
     * 实例操作
     *
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
     *
     * @return DefaultCommonResult<SchedulerInstanceConstantsVO>
     */
    @GetMapping("/constants")
    public DefaultCommonResult<SchedulerInstanceConstantsVO> getInstanceConstants() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInstanceService.getInstanceConstants());
    }

    /**
     * 任务实例列表
     *
     * @param taskInstanceParamsDTO
     * @return DefaultCommonResult<PageResultVO < DqcProcessTaskInstanceVO>>
     */
    @PostMapping("/task/list")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<PageResultVO<DqcProcessTaskInstanceVO>> searchTask(@RequestBody DqcSchedulerTaskInstanceParamsDTO taskInstanceParamsDTO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInstanceService.searchTask(taskInstanceParamsDTO));
    }

    /**
     * 任务实例日志
     *
     * @param taskInstanceLogDTO
     * @return DefaultCommonResult<String>
     */
    @PostMapping("/task/log")
    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.LIST)
    public DefaultCommonResult<Object> searchTaskLog(@RequestBody DqcSchedulerTaskInstanceLogDTO taskInstanceLogDTO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dqcSchedulerInstanceService.searchTaskLog(taskInstanceLogDTO));
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
        Object log = dqcSchedulerInstanceService.taskLogDownload(taskInstanceId);
        ExportTextUtil.writeToTxt(response, log.toString(), "任务实例日志_" + taskInstanceId + "_" + DateUtil.format(new Date(), "yyyyMMddHHmm"));
    }


}
