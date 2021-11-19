package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.FailureStrategyEnum;
import com.qk.dm.dataquality.constant.schedule.ProcessInstancePriorityEnum;
import com.qk.dm.dataquality.constant.schedule.WarningTypeEnum;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/11/16
 * @since 1.0.0
 */
@Service
public class ProcessDefinitionApiServiceImpl implements ProcessDefinitionApiService {

  private final DefaultApi defaultApi;

  @Autowired
  public ProcessDefinitionApiServiceImpl(DefaultApi defaultApi) {
    this.defaultApi = defaultApi;
  }

  @Override
  public void save(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
    try {
      // 构建ProcessData对象

      // 构建规则流程实例

      // 构建同步条件流程实例

      // 构建回调接口流程实例

      // 创建工作流实例
      //        ApiClient defaultClient = Configuration.getDefaultApiClient();
      //
      // defaultClient.setDefaultBaseUri("http://dolphinscheduler.qk.cc:30875/dolphinscheduler");
      //        defaultClient.setRequestInterceptor((r) -> {
      //          r.header("token", "4631e5fb85b7ac4dc84aba541e64e0e0");
      //        });
      //        DefaultApi apiInstance = new DefaultApi(defaultClient);

      //            //String connects, String locations, String name, String processDefinitionJson,
      // String projectName, String description
      String connects =
          "[{\"endPointSourceId\":\"tasks-49000\",\"endPointTargetId\":\"tasks-56268\"},{\"endPointSourceId\":\"tasks-87230\",\"endPointTargetId\":\"tasks-56268\"},{\"endPointSourceId\":\"tasks-56268\",\"endPointTargetId\":\"tasks-42847\"},{\"endPointSourceId\":\"tasks-56268\",\"endPointTargetId\":\"tasks-72883\"}]";
      String locations =
          "{\"tasks-49000\":{\"name\":\"sql_test01\",\"targetarr\":\"\",\"nodenumber\":\"1\",\"x\":481,\"y\":151},\"tasks-87230\":{\"name\":\"sql_test02\",\"targetarr\":\"\",\"nodenumber\":\"1\",\"x\":490,\"y\":278},\"tasks-42847\":{\"name\":\"callback_01\",\"targetarr\":\"tasks-56268\",\"nodenumber\":\"0\",\"x\":802,\"y\":171},\"tasks-72883\":{\"name\":\"callback_02\",\"targetarr\":\"tasks-56268\",\"nodenumber\":\"0\",\"x\":802,\"y\":300},\"tasks-56268\":{\"name\":\"sync_01\",\"targetarr\":\"tasks-49000,tasks-87230\",\"nodenumber\":\"2\",\"x\":656,\"y\":232}}";
      String name = "sql_8877";
      String processDefinitionJson =
          "{\"globalParams\":[],\"tasks\":[{\"type\":\"SQL\",\"id\":\"tasks-49000\",\"name\":\"sql_test01\",\"params\":{\"type\":\"MYSQL\",\"datasource\":1,\"sql\":\"SELECT * from TABLE\",\"udfs\":\"\",\"sqlType\":\"0\",\"sendEmail\":false,\"displayRows\":10,\"limit\":10000,\"title\":\"\",\"receivers\":\"\",\"receiversCc\":\"\",\"showType\":\"TABLE\",\"localParams\":[],\"connParams\":\"\",\"preStatements\":[],\"postStatements\":[]},\"description\":\"\",\"timeout\":{\"strategy\":\"\",\"interval\":null,\"enable\":false},\"runFlag\":\"NORMAL\",\"conditionResult\":{\"successNode\":[\"\"],\"failedNode\":[\"\"]},\"dependence\":{},\"maxRetryTimes\":\"0\",\"retryInterval\":\"1\",\"taskInstancePriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"preTasks\":[]},{\"type\":\"SQL\",\"id\":\"tasks-87230\",\"name\":\"sql_test02\",\"params\":{\"type\":\"MYSQL\",\"datasource\":1,\"sql\":\"select * from TABLE\",\"udfs\":\"\",\"sqlType\":\"0\",\"sendEmail\":false,\"displayRows\":10,\"limit\":10000,\"title\":\"\",\"receivers\":\"\",\"receiversCc\":\"\",\"showType\":\"TABLE\",\"localParams\":[],\"connParams\":\"\",\"preStatements\":[],\"postStatements\":[]},\"description\":\"\",\"timeout\":{\"strategy\":\"\",\"interval\":null,\"enable\":false},\"runFlag\":\"NORMAL\",\"conditionResult\":{\"successNode\":[\"\"],\"failedNode\":[\"\"]},\"dependence\":{},\"maxRetryTimes\":\"0\",\"retryInterval\":\"1\",\"taskInstancePriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"preTasks\":[]},{\"type\":\"HTTP\",\"id\":\"tasks-42847\",\"name\":\"callback_01\",\"params\":{\"localParams\":[],\"httpParams\":[],\"url\":\"http://127.0.0.1/api/bid_info/bid_search/\",\"httpMethod\":\"GET\",\"httpCheckCondition\":\"STATUS_CODE_DEFAULT\",\"condition\":\"\",\"connectTimeout\":60000,\"socketTimeout\":60000},\"description\":\"\",\"timeout\":{\"strategy\":\"\",\"interval\":null,\"enable\":false},\"runFlag\":\"NORMAL\",\"conditionResult\":{\"successNode\":[\"\"],\"failedNode\":[\"\"]},\"dependence\":{},\"maxRetryTimes\":\"0\",\"retryInterval\":\"1\",\"taskInstancePriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"preTasks\":[\"sync_01\"]},{\"type\":\"HTTP\",\"id\":\"tasks-72883\",\"name\":\"callback_02\",\"params\":{\"localParams\":[],\"httpParams\":[],\"url\":\"http://127.0.0.1/api/bid_info/bid_search/\",\"httpMethod\":\"GET\",\"httpCheckCondition\":\"STATUS_CODE_DEFAULT\",\"condition\":\"\",\"connectTimeout\":60000,\"socketTimeout\":60000},\"description\":\"\",\"timeout\":{\"strategy\":\"\",\"interval\":null,\"enable\":false},\"runFlag\":\"NORMAL\",\"conditionResult\":{\"successNode\":[\"\"],\"failedNode\":[\"\"]},\"dependence\":{},\"maxRetryTimes\":\"0\",\"retryInterval\":\"1\",\"taskInstancePriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"preTasks\":[\"sync_01\"]},{\"type\":\"CONDITIONS\",\"id\":\"tasks-56268\",\"name\":\"sync_01\",\"params\":{},\"description\":\"\",\"timeout\":{\"strategy\":\"\",\"interval\":null,\"enable\":false},\"runFlag\":\"NORMAL\",\"conditionResult\":{\"successNode\":[\"callback_01\"],\"failedNode\":[\"callback_02\"]},\"dependence\":{\"relation\":\"AND\",\"dependTaskList\":[{\"relation\":\"AND\",\"dependItemList\":[{\"depTasks\":\"sql_test01\",\"status\":\"SUCCESS\"}]},{\"relation\":\"AND\",\"dependItemList\":[{\"depTasks\":\"sql_test02\",\"status\":\"FAILURE\"}]}]},\"maxRetryTimes\":\"0\",\"retryInterval\":\"1\",\"taskInstancePriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"preTasks\":[\"sql_test01\",\"sql_test02\"]}],\"tenantId\":2,\"timeout\":0}";
      String projectName = "数据质量测试_wei";
      String description = "";

      //            String json = "";
      //            String locations = "";
      //
      //            String projectName = "数据质量测试_wei";
      //            String name = "dag_test";
      //            String description = "desc test";
      //            String connects = "";
      defaultApi.createProcessDefinitionUsingPOSTWithHttpInfo(
          connects, locations, name, processDefinitionJson, projectName, description);
    } catch (ApiException e) {
      e.printStackTrace();
    }
  }

  /****************************************************************************/

  /**
   * 流程定义发布
   *
   * @param processDefinitionId
   * @param releaseState 0-下线 1-上线
   */
  @Override
  public void release(Integer processDefinitionId, Integer releaseState) {
    try {
      Result result =
          defaultApi.releaseProcessDefinitionUsingPOST(
              DqcConstant.processDefinitionId, DqcConstant.projectName, releaseState);
      DqcConstant.verification(result, "流程定义发布失败{}");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  /**
   * 删除流程
   *
   * @param processDefinitionId
   */
  @Override
  public void deleteOne(Integer processDefinitionId) {
    try {
      Result result =
          defaultApi.deleteProcessDefinitionByIdUsingGET(
              DqcConstant.projectName, DqcConstant.processDefinitionId);
      DqcConstant.verification(result, "删除流程失败{}");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  /**
   * 复制流程
   *
   * @param processDefinitionId
   */
  @Override
  public void copy(Integer processDefinitionId) {
    try {
      Result result =
          defaultApi.copyProcessDefinitionUsingPOST(
              DqcConstant.processDefinitionId, DqcConstant.projectName);
      DqcConstant.verification(result, "复制流程失败{}");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  /**
   * 实例-检查流程--测试失败
   *
   * @param processDefinitionId
   */
  @Override
  public void startCheck(Integer processDefinitionId) {
    try {
      Result result =
          defaultApi.startCheckProcessDefinitionUsingPOST(
              DqcConstant.processDefinitionId, DqcConstant.projectName);
      DqcConstant.verification(result, "检查流程失败{}");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }

  /**
   * 实例-运行
   *
   * @param processDefinitionId
   */
  @Override
  public void startInstance(Integer processDefinitionId) {
    try {
      Result result =
          defaultApi.startProcessInstanceUsingPOST(
              FailureStrategyEnum.fromValue(1).getValue(),
              DqcConstant.processDefinitionId,
              ProcessInstancePriorityEnum.fromValue(3).getValue(),
              DqcConstant.projectName,
              "",
              0,
              WarningTypeEnum.fromValue(1).getValue(),
              "",
              "",
              "",
              "RUN_MODE_SERIAL",
              "",
              "TASK_POST",
              null,
              "default");
      DqcConstant.verification(result, "运行失败{}");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
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
          defaultApi.executeUsingPOST(
              executeType, DqcConstant.processInstanceId, DqcConstant.projectName);
      DqcConstant.verification(result, "执行流程实例操作失败{}");
    } catch (ApiException e) {
      DqcConstant.printException(e);
    }
  }
}
