package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.service.ProcessInstanceService;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/11/23 2:30 下午
 * @since 1.0.0
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
  /** 结束时间 */
  private static String INSTANCE_END_DATE = null;
  /** EXECUTOR_NAME */
  private static String INSTANCE_EXECUTOR_NAME = null;
  /** 运行任务的主机IP地址 */
  private static String INSTANCE_HOST = null;
  /** 搜索值 */
  private static String INSTANCE_SEARCH_VAL = null;
  /** 开始时间 */
  private static String INSTANCE_START_DATE = null;
  /** 工作流和任务节点的运行状态 结果：InstanceStateTypeEnum.SUCCESS.getCode() */
  private static String INSTANCE_STATE_TYPE = null;

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
          defaultApi.executeUsingPOST(executeType, processInstanceId, DqcConstant.PROJECT_NAME);
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
  public ProcessInstanceResultDTO search(
      Integer processDefinitionId, Integer pageNo, Integer pageSize) {
    try {
      Result result =
          defaultApi.queryProcessInstanceListUsingGET(
              DqcConstant.PROJECT_NAME,
              INSTANCE_END_DATE,
              INSTANCE_EXECUTOR_NAME,
              INSTANCE_HOST,
              pageNo,
              pageSize,
              processDefinitionId,
              INSTANCE_SEARCH_VAL,
              INSTANCE_START_DATE,
              INSTANCE_STATE_TYPE);
      DqcConstant.verification(result, "查询流程实例列表失败{}");
      return GsonUtil.fromJsonString(
          GsonUtil.toJsonString(result.getData()),
          new TypeToken<ProcessInstanceResultDTO>() {}.getType());
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
    return null;
  }

  @Override
  public ProcessInstanceDTO detail(Integer processInstanceId) {
    try {
      Result result =
          defaultApi.queryProcessInstanceByIdUsingGET(DqcConstant.PROJECT_NAME, processInstanceId);
      DqcConstant.verification(result, "查询流程实例通过流程实例ID失败{}");
      return GsonUtil.fromJsonString(
          GsonUtil.toJsonString(result.getData()),
          new TypeToken<ProcessInstanceDTO>() {}.getType());
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
    return null;
  }
}
