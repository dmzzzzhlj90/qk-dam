package com.qk.dm.dataquality.dolphinapi.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
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
              4,
              "数据质量",
              FailureStrategyEnum.fromValue(1).getValue(),
              ProcessInstancePriorityEnum.fromValue(3).getValue(),
              "",
              "",
              schedule(dqcSchedulerInfoVO.getDqcSchedulerConfigVO()),
              0,
              WarningTypeEnum.fromValue(1).getValue(),
              "default");
      if (result.getCode() != 0) {
        throw new BizException("创建定时失败!!!" + result.getMsg());
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public void update(Integer scheduleId, DqcSchedulerInfoVO dqcSchedulerInfoVO) {
    scheduleId = 7;
    try {
      Result result =
          defaultApi.updateScheduleUsingPOST(
              scheduleId,
              "数据质量",
              FailureStrategyEnum.fromValue(1).getValue(),
              ProcessInstancePriorityEnum.fromValue(3).getValue(),
              "",
              "",
              schedule(dqcSchedulerInfoVO.getDqcSchedulerConfigVO()),
              0,
              WarningTypeEnum.fromValue(1).getValue(),
              "default");
      if (result.getCode() != 0) {
        throw new BizException("修改定时失败!!!" + result.getMsg());
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public void online(Integer scheduleId) {
    scheduleId = 7;
    try {
      Result result = defaultApi.onlineUsingPOST(scheduleId, "数据质量");
      if (result.getCode() != 0) {
        throw new BizException("定时上线失败!!!" + result.getMsg());
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public void offline(Integer scheduleId) {
    scheduleId = 7;
    try {
      Result result = defaultApi.offlineUsingPOST(scheduleId, "数据质量");
      if (result.getCode() != 0) {
        throw new BizException("定时下线失败!!!" + result.getMsg());
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public void delete(Integer scheduleId) {
    scheduleId = 7;
    try {
      Result result =
          defaultApi.deleteScheduleByIdUsingGET(
              "数据质量", scheduleId, "", null, "", null, "", "", "", "", null, "", null, "", "", "");
      if (result.getCode() != 0) {
        throw new BizException("删除定时失败!!!" + result.getMsg());
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public ScheduleListPageVo search(
      Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal) {
    processDefinitionId = 4;
    try {
      Result result =
          defaultApi.queryScheduleListPagingUsingGET(
              processDefinitionId, "数据质量", pageNo, pageSize, searchVal);
      if (result.getCode() != 0) {
        throw new BizException("获取定时列表失败!!!" + result.getMsg());
      }
      return JSONObject.toJavaObject(
          (JSON) JSONObject.toJSON(JSONObject.toJSONString(result.getData())),
          ScheduleListPageVo.class);
    } catch (ApiException e) {
      printException(e);
    }
    return null;
  }

  private void printException(ApiException e) {
    System.err.println("Exception when calling DefaultApi#schedule");
    System.err.println("Status code: " + e.getCode());
    System.err.println("Reason: " + e.getResponseBody());
    System.err.println("Response headers: " + e.getResponseHeaders());
    e.printStackTrace();
  }

  private String schedule(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    JSONObject object = new JSONObject();
    object.put("startTime", DateUtil.format(dqcSchedulerConfigVO.getEffectiveTimeStart(), format));
    object.put("endTime", DateUtil.format(dqcSchedulerConfigVO.getEffectiveTimeEnt(), format));
    object.put("crontab", dqcSchedulerConfigVO.getCron());
    return object.toJSONString();
  }
}
