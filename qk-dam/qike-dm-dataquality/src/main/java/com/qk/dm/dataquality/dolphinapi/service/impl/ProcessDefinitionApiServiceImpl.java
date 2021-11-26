package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.*;
import com.qk.dm.dataquality.dolphinapi.builder.LocationsBuilder;
import com.qk.dm.dataquality.dolphinapi.builder.ProcessDataBuilder;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.dolphinapi.manager.ResourceFileManager;
import com.qk.dm.dataquality.dolphinapi.manager.TenantManager;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void save(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        try {
            // 获取DolphinScheduler 资源信息
            ResourceDTO mySqlScriptResource = ResourceFileManager.queryMySqlScriptResource(defaultApi);
            // 获取DolphinScheduler 租户信息
            TenantDTO tenantDTO = TenantManager.queryTenantInfo(defaultApi);
            // 构建ProcessData对象
            ProcessDataBuilder processDataBuilder =
                    ProcessDataBuilder.builder()
                            .build()
                            .info(dqcSchedulerBasicInfoVO, mySqlScriptResource, tenantDTO);
            ProcessDataDTO processData = processDataBuilder.getProcessData();
            // 构建locations
            LocationsDTO locationsDTO =
                    LocationsBuilder.builder().build().info(dqcSchedulerBasicInfoVO).taskNodeLocations();

            // 创建工作流实例
            String connects = "[]";
            String locations = GsonUtil.toJsonString(locationsDTO.getTaskNodeLocationMap());
            String name = dqcSchedulerBasicInfoVO.getJobName();
            String processDefinitionJson = GsonUtil.toJsonString(processData);
            String projectName = "数据质量_test";
            String description = dqcSchedulerBasicInfoVO.getJobId();

            defaultApi.createProcessDefinitionUsingPOSTWithHttpInfo(
                    connects, locations, name, processDefinitionJson, projectName, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProcessDefinitionDTO queryProcessDefinitionInfo(String projectName, String searchVal, String jobId) {
        ProcessDefinitionDTO processDefinitionDTO = null;
        try {
            Result result = defaultApi.queryProcessDefinitionListPagingUsingGET(1, 100, projectName, searchVal, null);
            Object data = result.getData();
            ProcessResultDataDTO processResultDataDTO = GsonUtil.fromJsonString(GsonUtil.toJsonString(data), new TypeToken<ProcessResultDataDTO>() {
            }.getType());
            List<ProcessDefinitionDTO> totalList = processResultDataDTO.getTotalList();

            List<ProcessDefinitionDTO> processDefinitions = totalList.stream()
                    .filter(processDefinition -> processDefinition.getName().equals(searchVal) && processDefinition.getDescription().equals(jobId))
                    .collect(Collectors.toList());
            processDefinitionDTO = processDefinitions.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("未获取到实例ID!!!");
        }
        return processDefinitionDTO;
    }

    /****************************************************************************/

    public List<com.qk.dm.dataquality.dolphinapi.builder.ProcessData> list() {
        try {
            Result result = defaultApi.queryProcessDefinitionListUsingGET(DqcConstant.projectName);
            DqcConstant.verification(result, "查询流程定义列表失败{}");
            JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(result.getData()));
            return (List<com.qk.dm.dataquality.dolphinapi.builder.ProcessData>)
                    JSON.toJavaObject(objects, com.qk.dm.dataquality.dolphinapi.builder.ProcessData.class);
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return null;
    }

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
                            processDefinitionId, DqcConstant.projectName, releaseState);
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
                            DqcConstant.projectName, processDefinitionId);
            DqcConstant.verification(result, "删除流程失败{}");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    /**
     * 验证流程定义名字
     *
     * @param name
     */
    @Override
    public void verifyName(String name) {
        try {
            Result result = defaultApi.verifyProcessDefinitionNameUsingGET(name, DqcConstant.projectName);
            DqcConstant.verification(result, "验证失败{}");
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
                    defaultApi.copyProcessDefinitionUsingPOST(processDefinitionId, DqcConstant.projectName);
            DqcConstant.verification(result, "复制流程失败{}");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    /**
     * 实例-检查流程
     *
     * @param processDefinitionId
     */
    @Override
    public void startCheck(Integer processDefinitionId) {
        try {
            Result result =
                    defaultApi.startCheckProcessDefinitionUsingPOST(
                            processDefinitionId, DqcConstant.projectName);
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
        // 定时时间
        String scheduleTime = "";
        // 发送组ID
        Integer warningGroupId = 0;
        // 收件人
        String receivers = "";
        // 收件人(抄送)
        String receiversCc = "";
        // 开始节点列表(节点name)
        String startNodeList = "";
        // 超时时间
        Integer timeout = null;
        // WORKER_GROUP
        String workerGroup = "default";
        try {
            Result result =
                    defaultApi.startProcessInstanceUsingPOST(
                            FailureStrategyEnum.CONTINUE.getValue(),
                            processDefinitionId,
                            ProcessInstancePriorityEnum.MEDIUM.getValue(),
                            DqcConstant.projectName,
                            scheduleTime,
                            warningGroupId,
                            WarningTypeEnum.NONE.getValue(),
                            "",
                            receivers,
                            receiversCc,
                            RunModeEnum.RUN_MODE_SERIAL.getValue(),
                            startNodeList,
                            TaskDependTypeEnum.TASK_POST.getValue(),
                            timeout,
                            workerGroup);
            DqcConstant.verification(result, "运行失败{}");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }
}
