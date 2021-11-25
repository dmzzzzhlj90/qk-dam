package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.dolphinapi.builder.InstanceData;
import com.qk.dm.dataquality.dolphinapi.builder.InstanceDataBuilder;
import com.qk.dm.dataquality.dolphinapi.service.ProcessInstanceService;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/11/23 2:30 下午
 * @since 1.0.0
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
  private final DefaultApi defaultApi;

  public ProcessInstanceServiceImpl(DefaultApi defaultApi) {
    this.defaultApi = defaultApi;
  }

  /**
   * 实例-操作
   *
   * @param processInstanceId
   * @param executeType
   */
  @Override
  public void execute(Integer processInstanceId, String executeType) {
    executeType = "REPEAT_RUNNING";
    try {
      Result result =
          defaultApi.executeUsingPOST(executeType, processInstanceId, DqcConstant.projectName);
      DqcConstant.verification(result, "执行流程实例操作失败{}");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  /**
   * 查询流程实例列表
   *
   * @param processDefinitionId
   * @return
   */
  @Override
  public InstanceDataBuilder search(Integer processDefinitionId) {
    try {
      Result result =
          defaultApi.queryProcessInstanceListUsingGET(
              DqcConstant.projectName,
              null,
              null,
              null,
              1,
              1,
              processDefinitionId,
              null,
              null,
              null);
      DqcConstant.verification(result, "查询流程实例列表失败{}");
      return JSON.toJavaObject(
          JSONObject.parseObject(JSONObject.toJSONString(result.getData())),
          InstanceDataBuilder.class);
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
    return null;
  }

  @Override
  public InstanceData detail(Integer processInstanceId) {
    try {
      Result result =
          defaultApi.queryProcessInstanceByIdUsingGET(DqcConstant.projectName, processInstanceId);
      DqcConstant.verification(result, "查询流程实例通过流程实例ID失败{}");
      JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(result.getData()));
      return JSON.toJavaObject(jsonObject, InstanceData.class);
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
    return null;
  }
}
