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
import com.qk.dm.dataquality.dolphinapi.dto.ScheduleResultDTO;
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

/**
 * @author shenpj
 * @date 2021/11/16 4:40 下午
 * @since 1.0.0
 */
@Service
public class ScheduleApiServiceImpl implements ScheduleApiService {
  /** 发送组ID */
  private static final Integer SCHEDULE_WARNING_GROUP_ID = 0;
  /** WORKER_GROUP */
  private static final String SCHEDULE_WORKER_GROUP = "default";
  /** 收件人 */
  private static final String SCHEDULE_RECEIVERS = null;
  /** 收件人(抄送) */
  private static final String SCHEDULE_RECEIVERS_CC = null;

  /** 删除定时所需字段 */
  private static final String SCHEDULE_ALERT_GROUP = null;

  private static final OffsetDateTime SCHEDULE_CREATE_TIME = null;
  private static final String SCHEDULE_EMAIL = null;
  private static final Integer ID = null;
  private static final String SCHEDULE_PHONE = null;
  private static final String SCHEDULE_QUEUE = null;
  private static final String SCHEDULE_QUEUE_NAME = null;
  private static final String SCHEDULE_TENANT_CODE = null;
  private static final Integer SCHEDULE_TENANT_ID = null;
  private static final String SCHEDULE_TENANT_NAME = null;
  private static final OffsetDateTime SCHEDULE_UPDATE_TIME = null;
  private static final String SCHEDULE_USER_NAME = null;
  private static final String SCHEDULE_USER_PASSWORD = null;
  private static final String SCHEDULE_USER_TYPE = null;

  private final DefaultApi defaultApi;

  public ScheduleApiServiceImpl(DefaultApi defaultApi) {
    this.defaultApi = defaultApi;
  }

  /** createSchedule 创建定时 */
  @Override
  public void create(Integer processDefinitionId, DqcSchedulerConfigVO dqcSchedulerConfigVO) {

    try {
      Result result =
          defaultApi.createScheduleUsingPOST(
              processDefinitionId,
              DqcConstant.PROJECT_NAME,
              FailureStrategyEnum.CONTINUE.getValue(),
              ProcessInstancePriorityEnum.MEDIUM.getValue(),
              SCHEDULE_RECEIVERS,
              SCHEDULE_RECEIVERS_CC,
              schedule(dqcSchedulerConfigVO),
              SCHEDULE_WARNING_GROUP_ID,
              WarningTypeEnum.NONE.getValue(),
              SCHEDULE_WORKER_GROUP);
      DqcConstant.verification(result, "创建定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void update(Integer scheduleId, DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    try {
      Result result =
          defaultApi.updateScheduleUsingPOST(
              scheduleId,
              DqcConstant.PROJECT_NAME,
              FailureStrategyEnum.CONTINUE.getValue(),
              ProcessInstancePriorityEnum.MEDIUM.getValue(),
              SCHEDULE_RECEIVERS,
              SCHEDULE_RECEIVERS_CC,
              schedule(dqcSchedulerConfigVO),
              SCHEDULE_WARNING_GROUP_ID,
              WarningTypeEnum.NONE.getValue(),
              SCHEDULE_WORKER_GROUP);
      DqcConstant.verification(result, "修改定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void online(Integer scheduleId) {
    try {
      Result result = defaultApi.onlineUsingPOST(scheduleId, DqcConstant.PROJECT_NAME);
      DqcConstant.verification(result, "定时上线失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void offline(Integer scheduleId) {
    try {
      Result result = defaultApi.offlineUsingPOST(scheduleId, DqcConstant.PROJECT_NAME);
      DqcConstant.verification(result, "定时下线失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void deleteOne(Integer scheduleId) {

    try {
      Result result =
          defaultApi.deleteScheduleByIdUsingGET(
              DqcConstant.PROJECT_NAME,
              scheduleId,
              SCHEDULE_ALERT_GROUP,
              SCHEDULE_CREATE_TIME,
              SCHEDULE_EMAIL,
              ID,
              SCHEDULE_PHONE,
              SCHEDULE_QUEUE,
              SCHEDULE_QUEUE_NAME,
              SCHEDULE_TENANT_CODE,
              SCHEDULE_TENANT_ID,
              SCHEDULE_TENANT_NAME,
              SCHEDULE_UPDATE_TIME,
              SCHEDULE_USER_NAME,
              SCHEDULE_USER_PASSWORD,
              SCHEDULE_USER_TYPE);
      DqcConstant.verification(result, "删除定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public ScheduleResultDTO search(
      Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal) {
    try {
      Result result =
          defaultApi.queryScheduleListPagingUsingGET(
              processDefinitionId, DqcConstant.PROJECT_NAME, pageNo, pageSize, searchVal);
      DqcConstant.verification(result, "获取定时列表失败{},");
      return GsonUtil.fromJsonString(
          GsonUtil.toJsonString(result.getData()), new TypeToken<ScheduleResultDTO>() {}.getType());
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
    return null;
  }

  private static String schedule(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    return DqcConstant.schedule(
        dqcSchedulerConfigVO.getEffectiveTimeStart(),
        dqcSchedulerConfigVO.getEffectiveTimeEnt(),
        dqcSchedulerConfigVO.getCron());
  }
}
