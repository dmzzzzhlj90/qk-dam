package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.FailureStrategyEnum;
import com.qk.dm.dataquality.constant.schedule.ProcessInstancePriorityEnum;
import com.qk.dm.dataquality.constant.schedule.WarningTypeEnum;
import com.qk.dm.dataquality.dolphinapi.config.DolphinRunInfoConfig;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.ScheduleDeleteDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ScheduleResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ScheduleSearchDTO;
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shenpj
 * @date 2021/11/16 4:40 下午
 * @since 1.0.0
 */
@Service
@Slf4j
public class ScheduleApiServiceImpl implements ScheduleApiService {
    private final DefaultApi defaultApi;
    private final DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig;
    private final DolphinRunInfoConfig dolphinRunInfoConfig;

    public ScheduleApiServiceImpl(
            DefaultApi defaultApi,
            DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
            DolphinRunInfoConfig dolphinRunInfoConfig) {
        this.defaultApi = defaultApi;
        this.dolphinSchedulerInfoConfig = dolphinSchedulerInfoConfig;
        this.dolphinRunInfoConfig = dolphinRunInfoConfig;
    }

    /**
     * createSchedule
     * 创建定时
     * processDefinitionCode 流程定义编码 (required)
     * projectCode PROJECT_CODE (required)
     * environmentCode ENVIRONMENT_CODE (optional)
     * failureStrategy 失败策略 (optional)
     * processInstancePriority 流程实例优先级 (optional)
     * schedule 定时 (optional)
     * warningGroupId 发送组ID (optional)
     * warningType 发送策略 (optional)
     * workerGroup workerGroup (optional, default to default)
     */
    @Override
    public void create(Long processDefinitionCode, Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        Result result = null;
        try {
            result =
                    defaultApi.createScheduleUsingPOST(
                            processDefinitionCode,
                            dolphinSchedulerInfoConfig.getProjectCode(),
                            dolphinRunInfoConfig.getEnvironmentCode(),
                            FailureStrategyEnum.fromValue(dolphinRunInfoConfig.getFailureStrategy()).getCode(),
                            ProcessInstancePriorityEnum.fromValue(dolphinRunInfoConfig.getProcessInstancePriority()).getCode(),
                            // 拼接定时时间
                            DqcConstant.schedule(effectiveTimeStart, effectiveTimeEnt, cron),
                            dolphinRunInfoConfig.getWarningGroupId(),
                            WarningTypeEnum.fromValue(dolphinRunInfoConfig.getWarningType()).getCode(),
                            dolphinSchedulerInfoConfig.getTaskWorkerGroup());
            DqcConstant.verification(result, "创建定时失败{},");
        } catch (ApiException e) {
            log.error("=============接口入参：{},{},{},{}==============", processDefinitionCode, effectiveTimeStart, effectiveTimeEnt, cron);
            DqcConstant.printException(e);
        }
    }

    /**
     * updateSchedule
     * 更新定时
     * id 定时ID (required)
     * projectCode PROJECT_CODE (required)
     * environmentCode ENVIRONMENT_CODE (optional)
     * failureStrategy 失败策略 (optional)
     * processInstancePriority 流程实例优先级 (optional)
     * schedule 定时 (optional)
     * warningGroupId 发送组ID (optional)
     * warningType 发送策略 (optional)
     * workerGroup worker群组 (optional)
     */
    @Override
    public void update(Integer scheduleId, Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        Result result = null;
        try {
            result =
                    defaultApi.updateScheduleUsingPUT(
                            scheduleId,
                            dolphinSchedulerInfoConfig.getProjectCode(),
                            dolphinRunInfoConfig.getEnvironmentCode(),
                            FailureStrategyEnum.fromValue(dolphinRunInfoConfig.getFailureStrategy()).getCode(),
                            ProcessInstancePriorityEnum.fromValue(dolphinRunInfoConfig.getProcessInstancePriority()).getCode(),
                            DqcConstant.schedule(effectiveTimeStart, effectiveTimeEnt, cron),
                            dolphinRunInfoConfig.getWarningGroupId(),
                            WarningTypeEnum.fromValue(dolphinRunInfoConfig.getWarningType()).getCode(),
                            dolphinSchedulerInfoConfig.getTaskWorkerGroup());
            DqcConstant.verification(result, "修改定时失败{},");
        } catch (ApiException e) {
            log.error("=============接口入参：{},{},{},{}==============", scheduleId, effectiveTimeStart, effectiveTimeEnt, cron);
            DqcConstant.printException(e);
        }
    }

    @Override
    public void online(Integer scheduleId) {
        try {
            Result result = defaultApi.onlineUsingPOST(scheduleId, dolphinSchedulerInfoConfig.getProjectCode());
            DqcConstant.verification(result, "定时上线失败{},");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    @Override
    public void offline(Integer scheduleId) {
        try {
            Result result = defaultApi.offlineUsingPOST(scheduleId, dolphinSchedulerInfoConfig.getProjectCode());
            DqcConstant.verification(result, "定时下线失败{},");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }


    @Override
    public void deleteOne(ScheduleDeleteDTO scheduleDeleteDTO) {
        try {
            Result result =
                    defaultApi.deleteScheduleByIdUsingDELETE(
                            scheduleDeleteDTO.getScheduleId(),
                            dolphinSchedulerInfoConfig.getProjectCode(),
                            scheduleDeleteDTO.getAlertGroup(),
                            scheduleDeleteDTO.getCreateTime(),
                            scheduleDeleteDTO.getEmail(),
                            scheduleDeleteDTO.getPhone(),
                            scheduleDeleteDTO.getQueue(),
                            scheduleDeleteDTO.getQueueName(),
                            scheduleDeleteDTO.getState(),
                            scheduleDeleteDTO.getTenantCode(),
                            scheduleDeleteDTO.getTenantId(),
                            scheduleDeleteDTO.getUpdateTime(),
                            scheduleDeleteDTO.getUserName(),
                            scheduleDeleteDTO.getUserPassword(),
                            scheduleDeleteDTO.getUserType());
            DqcConstant.verification(result, "删除定时失败{},");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    /**
     * queryScheduleListPaging
     * 分页查询定时
     * processDefinitionCode processDefinitionCode (required)
     * projectCode PROJECT_CODE (required)
     * pageNo 页码号 (optional)
     * pageSize 页大小 (optional)
     * searchVal 搜索值 (optional)
     */
    @Override
    public ScheduleResultDTO search(ScheduleSearchDTO scheduleSearchDTO) {
        ScheduleResultDTO scheduleResultDTO = new ScheduleResultDTO();
        Result result = null;
        try {
            result =
                    defaultApi.queryScheduleListPagingUsingGET(
                            scheduleSearchDTO.getProcessDefinitionCode(),
                            dolphinSchedulerInfoConfig.getProjectCode(),
                            scheduleSearchDTO.getPageNo(),
                            scheduleSearchDTO.getPageSize(),
                            scheduleSearchDTO.getSearchVal());
            DqcConstant.verification(result, "获取定时列表失败{},");
            if (result.getData() != null) {
                scheduleResultDTO = DqcConstant.changeObjectToClass(result.getData(), ScheduleResultDTO.class);
            }
        } catch (Exception e) {
            log.error("=============接口入参：{}==============", scheduleSearchDTO);
            log.error("=============接口结果：{}==============", result);
            DqcConstant.printException(e);
        }
        return scheduleResultDTO;
    }
}
