package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.FailureStrategyEnum;
import com.qk.dm.dataquality.constant.schedule.ProcessInstancePriorityEnum;
import com.qk.dm.dataquality.constant.schedule.WarningTypeEnum;
import com.qk.dm.dataquality.dolphinapi.builder.ProcessDataBuilder;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.dolphinapi.manager.ResourceFileManager;
import com.qk.dm.dataquality.dolphinapi.manager.TenantManager;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import org.apache.dolphinscheduler.dao.entity.ProcessData;
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
            //获取DolphinScheduler 资源信息
            ResourceDTO mySqlScriptResource = ResourceFileManager.queryMySqlScriptResource(defaultApi);

            //获取DolphinScheduler 租户信息
            TenantManager.queryTenantInfo(defaultApi);

            // 构建ProcessData对象
            ProcessDataBuilder processDataBuilder = ProcessDataBuilder.builder().build().info(dqcSchedulerInfoVO,mySqlScriptResource);
            ProcessData processData = processDataBuilder.getProcessData();
            // 构建规则流程实例

            // 构建同步条件流程实例

            // 构建回调接口流程实例

            // 创建工作流实例
            String connects = "[]";
            String locations = "{\"tasks-66666\":{\"name\":\"test_0002\",\"targetarr\":\"\",\"nodenumber\":\"0\",\"x\":344,\"y\":171}}";
            String name = "test_sql_123666";
            String processDefinitionJson = "{\"globalParams\":[],\n" +
                    "\t\n" +
                    "\t\"tasks\":[{\n" +
                    "\t\t\"type\":\"SHELL\",\n" +
                    "\t\t\"id\":\"tasks-66666\",\n" +
                    "\t\t\"name\":\"test_0002\",\n" +
                    "\t\t\"params\":\n" +
                    "\t\t\t{\n" +
                    "\t\t\t\"resourceList\":[{\"id\":4,\"name\":\"sql_temp_param.py\",\"res\":\"wei/sql_temp_param.py\"}],\n" +
                    "\t\t\t\"localParams\":[],\n" +
                    "\t\t\t\"rawScript\":\"/opt/soft/python3/bin/python3 wei/sql_temp_param.py '{\\n    \\\"from_host\\\":\\\"172.20.0.24\\\",\\n    \\\"from_user\\\":\\\"root\\\",\\n    \\\"from_password\\\":\\\"Zhudao123!\\\",\\n    \\\"from_database\\\":\\\"qkdam\\\",\\n    \\\"search_sql\\\":\\\"select count(1) from qk_dqc_rule_template\\\",\\n\\t\\\"to_host\\\":\\\"172.20.0.24\\\",\\n    \\\"to_user\\\":\\\"root\\\",\\n    \\\"to_password\\\":\\\"Zhudao123!\\\",\\n    \\\"to_database\\\":\\\"qkdam\\\",\\n\\t\\\"job_id\\\":\\\"job_id1\\\",\\n\\t\\\"job_name\\\":\\\"job_name1\\\",\\n\\t\\\"dir_id\\\":\\\"dir_id1\\\",\\n\\t\\\"rule_temp_id\\\":\\\"rule_temp_id1\\\"\\n}'\"\n" +
                    "\t\t\t},\n" +
                    "\t\t\"description\":\"\",\n" +
                    "\t\t\"timeout\":{\"strategy\":\"\",\"interval\":null,\"enable\":false},\n" +
                    "\t\t\"runFlag\":\"NORMAL\",\n" +
                    "\t\t\"conditionResult\":{\"successNode\":[\"\"],\"failedNode\":[\"\"]},\n" +
                    "\t\t\"dependence\":{},\n" +
                    "\t\t\"maxRetryTimes\":\"0\",\n" +
                    "\t\t\"retryInterval\":\"1\",\n" +
                    "\t\t\"taskInstancePriority\":\"MEDIUM\",\n" +
                    "\t\t\"workerGroup\":\"default\",\n" +
                    "\t\t\"preTasks\":[]}\n" +
                    "\t],\n" +
                    "\t\n" +
                    "\t\"tenantId\":1,\"timeout\":0}";
            String projectName = "数据质量_wei";
            String description = "";
//
//            defaultApi.createProcessDefinitionUsingPOSTWithHttpInfo(connects, locations, name, processDefinitionJson, projectName, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****************************************************************************/

    /**
     * 流程定义发布
     *
     * @param processDefinitionId
     * @param releaseState        0-下线 1-上线
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
