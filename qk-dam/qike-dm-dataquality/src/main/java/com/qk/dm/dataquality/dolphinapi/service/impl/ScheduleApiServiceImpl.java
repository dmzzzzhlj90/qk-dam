package com.qk.dm.dataquality.dolphinapi.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.FailureStrategyEnum;
import com.qk.dm.dataquality.constant.schedule.ProcessInstancePriorityEnum;
import com.qk.dm.dataquality.constant.schedule.WarningTypeEnum;
import com.qk.dm.dataquality.dolphinapi.builder.ScheduleDataBuilder;
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/11/16 4:40 下午
 * @since 1.0.0
 */
@Service
public class ScheduleApiServiceImpl implements ScheduleApiService {
  private final DefaultApi defaultApi;

  public ScheduleApiServiceImpl(DefaultApi defaultApi) {
    this.defaultApi = defaultApi;
  }

  /** createSchedule 创建定时 */
  @Override
  public void create(Integer processDefinitionId, DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    // 发送组ID
    Integer warningGroupId = 0;
    // 收件人
    String receivers = "";
    // 收件人(抄送)
    String receiversCc = "";
    // WORKER_GROUP
    String workerGroup = "default";
    try {
      Result result =
          defaultApi.createScheduleUsingPOST(
              processDefinitionId,
              DqcConstant.projectName,
              FailureStrategyEnum.CONTINUE.getValue(),
              ProcessInstancePriorityEnum.MEDIUM.getValue(),
              receivers,
              receiversCc,
              schedule(dqcSchedulerConfigVO),
              warningGroupId,
              WarningTypeEnum.NONE.getValue(),
              workerGroup);
      DqcConstant.verification(result, "创建定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void update(Integer scheduleId, DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    // 发送组ID
    Integer warningGroupId = 0;
    // 收件人
    String receivers = "";
    // 收件人(抄送)
    String receiversCc = "";
    // WORKER_GROUP
    String workerGroup = "default";
    try {
      Result result =
          defaultApi.updateScheduleUsingPOST(
              scheduleId,
              DqcConstant.projectName,
              FailureStrategyEnum.CONTINUE.getValue(),
              ProcessInstancePriorityEnum.MEDIUM.getValue(),
              receivers,
              receiversCc,
              schedule(dqcSchedulerConfigVO),
              warningGroupId,
              WarningTypeEnum.NONE.getValue(),
              workerGroup);
      DqcConstant.verification(result, "修改定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void online(Integer scheduleId) {
    try {
      Result result = defaultApi.onlineUsingPOST(scheduleId, DqcConstant.projectName);
      DqcConstant.verification(result, "定时上线失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void offline(Integer scheduleId) {
    try {
      Result result = defaultApi.offlineUsingPOST(scheduleId, DqcConstant.projectName);
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
              DqcConstant.projectName,
              scheduleId,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null);
      DqcConstant.verification(result, "删除定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public ScheduleDataBuilder search(
      Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal) {
    try {
      Result result =
          defaultApi.queryScheduleListPagingUsingGET(
              processDefinitionId, DqcConstant.projectName, pageNo, pageSize, searchVal);
      DqcConstant.verification(result, "获取定时列表失败{},");
      return JSON.toJavaObject(
          JSONObject.parseObject(JSONObject.toJSONString(result.getData())),
          ScheduleDataBuilder.class);
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
    return null;
  }

  private String schedule(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    JSONObject object = new JSONObject();
    object.put(
        "startTime",
        DateUtil.format(dqcSchedulerConfigVO.getEffectiveTimeStart(), DqcConstant.format));
    object.put(
        "endTime", DateUtil.format(dqcSchedulerConfigVO.getEffectiveTimeEnt(), DqcConstant.format));
    object.put("crontab", dqcSchedulerConfigVO.getCron());
    return object.toJSONString();
  }
}
