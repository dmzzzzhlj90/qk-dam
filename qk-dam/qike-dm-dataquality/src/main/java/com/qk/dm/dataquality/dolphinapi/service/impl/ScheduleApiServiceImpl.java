package com.qk.dm.dataquality.dolphinapi.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiClient;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.client.Configuration;
import com.qk.datacenter.model.Result;
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

  /** createSchedule 创建定时 */
  @Override
  public void create(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result =
          apiInstance.createScheduleUsingPOST(
              4,
              "数据质量",
              "CONTINUE",
              "MEDIUM",
              "",
              "",
              schedule(dqcSchedulerInfoVO.getDqcSchedulerConfigVO()),
              0,
              "NONE",
              "default");
      if (result.getCode() != 0) {
        throw new BizException("创建定时失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public void update(Integer scheduleId, DqcSchedulerInfoVO dqcSchedulerInfoVO) {
    scheduleId = 7;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result =
          apiInstance.updateScheduleUsingPOST(
              scheduleId,
              "数据质量",
              "CONTINUE",
              "MEDIUM",
              "",
              "",
              schedule(dqcSchedulerInfoVO.getDqcSchedulerConfigVO()),
              0,
              "NONE",
              "default");
      if (result.getCode() != 0) {
        throw new BizException("修改定时失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public void online(Integer scheduleId) {
    scheduleId = 7;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result = apiInstance.onlineUsingPOST(scheduleId, "数据质量");
      if (result.getCode() != 0) {
        throw new BizException("定时上线失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public void offline(Integer scheduleId) {
    scheduleId = 7;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result = apiInstance.offlineUsingPOST(scheduleId, "数据质量");
      if (result.getCode() != 0) {
        throw new BizException("定时下线失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public void delete(Integer scheduleId) {
    scheduleId = 7;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result =
          apiInstance.deleteScheduleByIdUsingGET(
              "数据质量", scheduleId, "", null, "", null, "", "", "", "", null, "", null, "", "", "");
      if (result.getCode() != 0) {
        throw new BizException("删除定时失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  @Override
  public ScheduleListPageVo search(
      Integer processDefinitionId, Integer pageNo, Integer pageSize, String searchVal) {
    processDefinitionId = 4;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result =
          apiInstance.queryScheduleListPagingUsingGET(
              processDefinitionId, "数据质量", pageNo, pageSize, searchVal);
      if (result.getCode() != 0) {
        throw new BizException("获取定时列表失败!!!");
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
    System.err.println("Exception when calling DefaultApi#createSchedule");
    System.err.println("Status code: " + e.getCode());
    System.err.println("Reason: " + e.getResponseBody());
    System.err.println("Response headers: " + e.getResponseHeaders());
    e.printStackTrace();
  }

  private ApiClient getApiClient() {
    return Configuration.getDefaultApiClient()
        .setRequestInterceptor(
            (r) -> {
              r.header("token", "2b29f18d15f3be6642814355f3dc9229");
            });
  }

  private String schedule(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    JSONObject object = new JSONObject();
    object.put("startTime", DateUtil.format(dqcSchedulerConfigVO.getEffectiveTimeStart(), format));
    object.put("endTime", DateUtil.format(dqcSchedulerConfigVO.getEffectiveTimeEnt(), format));
    object.put("crontab", dqcSchedulerConfigVO.getCron());
    return object.toJSONString();
  }
}
