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
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import com.qk.dm.dataquality.vo.ScheduleListPageVo;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/11/16 4:40 下午
 * @since 1.0.0
 */
@Service
public class ScheduleApiServiceImpl implements ScheduleApiService {
  private static final String format = "yyyy-MM-dd HH:mm:ss";
  private final DefaultApi defaultApi;

  public ScheduleApiServiceImpl(DefaultApi defaultApi) {
    this.defaultApi = defaultApi;
  }

  /** createSchedule 创建定时 */
  @Override
  public void create(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
    try {
      Result result =
          defaultApi.createScheduleUsingPOST(
              DqcConstant.processDefinitionId,
              DqcConstant.projectName,
              FailureStrategyEnum.fromValue(1).getValue(),
              ProcessInstancePriorityEnum.fromValue(3).getValue(),
              "",
              "",
              schedule(dqcSchedulerInfoVO.getDqcSchedulerConfigVO()),
              0,
              WarningTypeEnum.fromValue(1).getValue(),
              "default");
      DqcConstant.verification(result, "创建定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void update(Integer scheduleId, DqcSchedulerInfoVO dqcSchedulerInfoVO) {
    try {
      Result result =
          defaultApi.updateScheduleUsingPOST(
              DqcConstant.scheduleId,
              DqcConstant.projectName,
              FailureStrategyEnum.fromValue(1).getValue(),
              ProcessInstancePriorityEnum.fromValue(3).getValue(),
              "",
              "",
              schedule(dqcSchedulerInfoVO.getDqcSchedulerConfigVO()),
              0,
              WarningTypeEnum.fromValue(1).getValue(),
              "default");
      DqcConstant.verification(result, "修改定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void online(Integer scheduleId) {
    try {
      Result result = defaultApi.onlineUsingPOST(DqcConstant.scheduleId, DqcConstant.projectName);
      DqcConstant.verification(result, "定时上线失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void offline(Integer scheduleId) {
    try {
      Result result = defaultApi.offlineUsingPOST(DqcConstant.scheduleId, DqcConstant.projectName);
      DqcConstant.verification(result, "定时下线失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public void delete(Integer scheduleId) {
    try {
      Result result =
          defaultApi.deleteScheduleByIdUsingGET(
              DqcConstant.projectName,
              DqcConstant.scheduleId,
              "",
              null,
              "",
              null,
              "",
              "",
              "",
              "",
              null,
              "",
              null,
              "",
              "",
              "");
      DqcConstant.verification(result, "删除定时失败{},");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  @Override
  public ScheduleListPageVo search(
      Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal) {
    try {
      Result result =
          defaultApi.queryScheduleListPagingUsingGET(
              DqcConstant.processDefinitionId,
              DqcConstant.projectName,
              pageNo,
              pageSize,
              searchVal);
      DqcConstant.verification(result, "获取定时列表失败{},");
      return JSONObject.toJavaObject(
          (JSON) JSONObject.toJSON(JSONObject.toJSONString(result.getData())),
          ScheduleListPageVo.class);
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
    return null;
  }

  private String schedule(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    JSONObject object = new JSONObject();
    object.put("startTime", DateUtil.format(dqcSchedulerConfigVO.getEffectiveTimeStart(), format));
    object.put("endTime", DateUtil.format(dqcSchedulerConfigVO.getEffectiveTimeEnt(), format));
    object.put("crontab", dqcSchedulerConfigVO.getCron());
    return object.toJSONString();
  }
}
