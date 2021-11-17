package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiClient;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.client.Configuration;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/11/16
 * @since 1.0.0
 */
@Service
public class ProcessDefinitionApiServiceImpl implements ProcessDefinitionApiService {

  @Override
  public void save(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
    // 构建ProcessData对象

    // 构建规则流程实例

    // 构建同步条件流程实例

    // 构建回调接口流程实例

    // 创建工作流实例
    ApiClient defaultClient =
        Configuration.getDefaultApiClient()
            .setRequestInterceptor(
                (r) -> {
                  r.header("token", "2b29f18d15f3be6642814355f3dc9229");
                });
    //        DefaultApi apiInstance = new DefaultApi(defaultClient);
    //
    //        apiInstance.createProcessDefinitionUsingPOST();

  }

  /****************************************************************************/

  /**
   * 流程定义发布
   *
   * @param processDefinitionId
   * @param releaseState 0-下线 1-上线
   */
  public void release(Integer processDefinitionId, Integer releaseState) {
    processDefinitionId = 4;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result =
          apiInstance.releaseProcessDefinitionUsingPOST(processDefinitionId, "数据质量", releaseState);
      if (result.getCode() != 0) {
        throw new BizException("删除定时失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  /**
   * 删除流程
   *
   * @param processDefinitionId
   */
  public void delete(Integer processDefinitionId) {
    processDefinitionId = 4;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result = apiInstance.deleteProcessDefinitionByIdUsingGET("数据质量", processDefinitionId);
      if (result.getCode() != 0) {
        throw new BizException("删除流程失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  /**
   * 复制流程
   *
   * @param processDefinitionId
   */
  public void copy(Integer processDefinitionId) {
    processDefinitionId = 4;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result = apiInstance.copyProcessDefinitionUsingPOST(processDefinitionId, "数据质量");
      if (result.getCode() != 0) {
        throw new BizException("删除流程失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  /**
   * 检查流程
   *
   * @param processDefinitionId
   */
  public void startCheck(Integer processDefinitionId) {
    processDefinitionId = 4;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result = apiInstance.startCheckProcessDefinitionUsingPOST(processDefinitionId);
      if (result.getCode() != 0) {
        throw new BizException("删除流程失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
  }

  /**
   * 运行
   *
   * @param processDefinitionId
   */
  public void startInstance(Integer processDefinitionId) {
    processDefinitionId = 4;
    // 创建工作流实例
    ApiClient defaultClient = getApiClient();
    // api-sdk
    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      Result result =
          apiInstance.startProcessInstanceUsingPOST(
              "CONTINUE",
              processDefinitionId,
              "MEDIUM",
              "数据质量",
              "",
              0,
              "NONE",
              "",
              "",
              "",
              "RUN_MODE_SERIAL",
              "",
              "TASK_POST",
              null,
              "default");
      if (result.getCode() != 0) {
        throw new BizException("删除流程失败!!!");
      }
    } catch (ApiException e) {
      printException(e);
    }
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
}
