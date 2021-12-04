package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
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
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shenpj
 * @date 2021/11/16 4:40 下午
 * @since 1.0.0
 */
@Service
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

  /** createSchedule 创建定时 */
  @Override
  public void create(
      Integer processDefinitionId, Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {

    try {
      Result result =
          defaultApi.createScheduleUsingPOST(
              // 流程定义Id
              processDefinitionId,
              // 项目名称
              dolphinSchedulerInfoConfig.getProjectName(),
              // 失败策略
              FailureStrategyEnum.fromValue(dolphinRunInfoConfig.getFailureStrategy()).getCode(),
              // 流程实例优先级
              ProcessInstancePriorityEnum.fromValue(dolphinRunInfoConfig.getProcessInstancePriority()).getCode(),
              // 收件人
              dolphinRunInfoConfig.getReceivers(),
              // 收件人(抄送)
              dolphinRunInfoConfig.getReceiversCc(),
              // 拼接定时时间
              DqcConstant.schedule(effectiveTimeStart, effectiveTimeEnt, cron),
              // 发送组ID
              dolphinRunInfoConfig.getWarningGroupId(),
              // 发送策略
              WarningTypeEnum.fromValue(dolphinRunInfoConfig.getWarningType()).getCode(),
              // WORKER_GROUP
              dolphinSchedulerInfoConfig.getTaskWorkerGroup());
      DqcConstant.verification(result, "创建定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void update(
      Integer scheduleId, Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
    try {
      Result result =
          defaultApi.updateScheduleUsingPOST(
              // 定时id
              scheduleId,
              // 项目名称
              dolphinSchedulerInfoConfig.getProjectName(),
              // 失败策略
              FailureStrategyEnum.fromValue(dolphinRunInfoConfig.getFailureStrategy()).getCode(),
              // 流程实例优先级
              ProcessInstancePriorityEnum.fromValue(dolphinRunInfoConfig.getProcessInstancePriority()).getCode(),
              // 收件人
              dolphinRunInfoConfig.getReceivers(),
              // 收件人(抄送)
              dolphinRunInfoConfig.getReceiversCc(),
              // 拼接定时时间
              DqcConstant.schedule(effectiveTimeStart, effectiveTimeEnt, cron),
              // 发送组ID
              dolphinRunInfoConfig.getWarningGroupId(),
              // 发送策略
              WarningTypeEnum.fromValue(dolphinRunInfoConfig.getWarningType()).getCode(),
              // WORKER_GROUP
              dolphinSchedulerInfoConfig.getTaskWorkerGroup());
      DqcConstant.verification(result, "修改定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void online(Integer scheduleId) {
    try {
      Result result = defaultApi.onlineUsingPOST(scheduleId, dolphinSchedulerInfoConfig.getProjectName());
      DqcConstant.verification(result, "定时上线失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void offline(Integer scheduleId) {
    try {
      Result result = defaultApi.offlineUsingPOST(scheduleId, dolphinSchedulerInfoConfig.getProjectName());
      DqcConstant.verification(result, "定时下线失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void deleteOne(ScheduleDeleteDTO scheduleDeleteDTO) {
    try {
      Result result =
          defaultApi.deleteScheduleByIdUsingGET(
              dolphinSchedulerInfoConfig.getProjectName(),
              scheduleDeleteDTO.getScheduleId(),
              scheduleDeleteDTO.getAlertGroup(),
              scheduleDeleteDTO.getCreateTime(),
              scheduleDeleteDTO.getEmail(),
              scheduleDeleteDTO.getId(),
              scheduleDeleteDTO.getPhone(),
              scheduleDeleteDTO.getQueue(),
              scheduleDeleteDTO.getQueueName(),
              scheduleDeleteDTO.getTenantCode(),
              scheduleDeleteDTO.getTenantId(),
              scheduleDeleteDTO.getTenantName(),
              scheduleDeleteDTO.getUpdateTime(),
              scheduleDeleteDTO.getUserName(),
              scheduleDeleteDTO.getUserPassword(),
              scheduleDeleteDTO.getUserType());
      DqcConstant.verification(result, "删除定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public ScheduleResultDTO search(ScheduleSearchDTO scheduleSearchDTO) {
    try {
      Result result =
          defaultApi.queryScheduleListPagingUsingGET(
              scheduleSearchDTO.getProcessDefinitionId(),
              dolphinSchedulerInfoConfig.getProjectName(),
              scheduleSearchDTO.getPageNo(),
              scheduleSearchDTO.getPageSize(),
              scheduleSearchDTO.getSearchVal());
      DqcConstant.verification(result, "获取定时列表失败{},");
      return GsonUtil.fromJsonString(GsonUtil.toJsonString(result.getData()), new TypeToken<ScheduleResultDTO>() {}.getType());
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
    return null;
  }
}
