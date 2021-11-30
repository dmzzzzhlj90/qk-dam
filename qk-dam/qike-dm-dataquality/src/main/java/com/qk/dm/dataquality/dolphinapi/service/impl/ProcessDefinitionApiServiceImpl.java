package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.*;
import com.qk.dm.dataquality.dolphinapi.config.DolphinRunInfoConfig;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.constant.SchedulerConstant;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.dolphinapi.executor.LocationsExecutor;
import com.qk.dm.dataquality.dolphinapi.executor.ProcessDataExecutor;
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
    private final DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig;
    private final DolphinRunInfoConfig dolphinRunInfoConfig;

    @Autowired
    public ProcessDefinitionApiServiceImpl(DefaultApi defaultApi, DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig, DolphinRunInfoConfig dolphinRunInfoConfig) {
        this.defaultApi = defaultApi;
        this.dolphinSchedulerInfoConfig = dolphinSchedulerInfoConfig;
        this.dolphinRunInfoConfig = dolphinRunInfoConfig;
    }

    @Override
    public void save(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        try {
            // 获取DolphinScheduler 资源信息
            ResourceDTO mySqlScriptResource = ResourceFileManager.queryMySqlScriptResource(defaultApi, dolphinSchedulerInfoConfig);
            // 获取DolphinScheduler 租户信息
            TenantDTO tenantDTO = TenantManager.queryTenantInfo(defaultApi, dolphinSchedulerInfoConfig);
            // 构建ProcessData对象
            ProcessDataDTO processDataDTO = ProcessDataExecutor.dqcProcessData(dqcSchedulerBasicInfoVO, mySqlScriptResource, tenantDTO, dolphinSchedulerInfoConfig);
            // 构建locations
            LocationsDTO locationsDTO = LocationsExecutor.dqcLocations(dqcSchedulerBasicInfoVO, dolphinSchedulerInfoConfig);

            // 创建工作流实例
            String connects = SchedulerConstant.EMPTY_ARRAY;
            String locations = GsonUtil.toJsonString(locationsDTO.getTaskNodeLocationMap());
            String name = dqcSchedulerBasicInfoVO.getJobName();
            String processDefinitionJson = GsonUtil.toJsonString(processDataDTO);
            String projectName = dolphinSchedulerInfoConfig.getProjectName();
            String description = dqcSchedulerBasicInfoVO.getJobId();

            defaultApi.createProcessDefinitionUsingPOSTWithHttpInfo(connects, locations, name, processDefinitionJson, projectName, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        try {
            Integer processDefinitionId = dqcSchedulerBasicInfoVO.getProcessDefinitionId();
            // 获取DolphinScheduler 资源信息
            ResourceDTO mySqlScriptResource = ResourceFileManager.queryMySqlScriptResource(defaultApi, dolphinSchedulerInfoConfig);
            // 获取DolphinScheduler 租户信息
            TenantDTO tenantDTO = TenantManager.queryTenantInfo(defaultApi, dolphinSchedulerInfoConfig);
            // 构建ProcessData对象
            ProcessDataDTO processDataDTO = ProcessDataExecutor.dqcProcessData(dqcSchedulerBasicInfoVO, mySqlScriptResource, tenantDTO, dolphinSchedulerInfoConfig);
            // 构建locations
            LocationsDTO locationsDTO = LocationsExecutor.dqcLocations(dqcSchedulerBasicInfoVO, dolphinSchedulerInfoConfig);

            // 创建工作流实例
            String connects = SchedulerConstant.EMPTY_ARRAY;
            String locations = GsonUtil.toJsonString(locationsDTO.getTaskNodeLocationMap());
            String name = dqcSchedulerBasicInfoVO.getJobName();
            String processDefinitionJson = GsonUtil.toJsonString(processDataDTO);
            String projectName = dolphinSchedulerInfoConfig.getProjectName();
            String description = dqcSchedulerBasicInfoVO.getJobId();

            defaultApi.updateProcessDefinitionUsingPOST(connects, processDefinitionId, locations, name, processDefinitionJson, projectName, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProcessDefinitionDTO queryProcessDefinitionInfo(String projectName, String searchVal, String jobId) {
        ProcessDefinitionDTO processDefinitionDTO = null;
        try {
            Result result = defaultApi.queryProcessDefinitionListPagingUsingGET(SchedulerConstant.PAGE_NO, SchedulerConstant.PAGE_SIZE, projectName, searchVal, null);
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

    @Override
    public void delete(String projectName, Integer processDefinitionId) {
        try {
            defaultApi.deleteProcessDefinitionByIdUsingGET(projectName, processDefinitionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBulk(String projectName, List<Integer> processDefinitionIdList) {
        try {
            String processDefinitionIds = processDefinitionIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
            defaultApi.batchDeleteProcessDefinitionByIdsUsingGETWithHttpInfo(projectName, processDefinitionIds);
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
                            processDefinitionId, dolphinSchedulerInfoConfig.getProjectName(), releaseState);
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
                            dolphinSchedulerInfoConfig.getProjectName(), processDefinitionId);
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
            Result result = defaultApi.verifyProcessDefinitionNameUsingGET(name, dolphinSchedulerInfoConfig.getProjectName());
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
                    defaultApi.copyProcessDefinitionUsingPOST(processDefinitionId, dolphinSchedulerInfoConfig.getProjectName());
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
                            processDefinitionId, dolphinSchedulerInfoConfig.getProjectName());
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
                            //失败策略
                            FailureStrategyEnum.fromValue(dolphinRunInfoConfig.getFailureStrategy()).getValue(),
                            //流程定义id
                            processDefinitionId,
                            //流程实例优先级
                            ProcessInstancePriorityEnum.fromValue(dolphinRunInfoConfig.getProcessInstancePriority()).getValue(),
                            //项目名称
                            dolphinSchedulerInfoConfig.getProjectName(),
                            // 定时时间
                            dolphinRunInfoConfig.getScheduleTime(),
                            // 发送组ID
                            dolphinRunInfoConfig.getWarningGroupId(),
                            //发送策略
                            WarningTypeEnum.fromValue(dolphinRunInfoConfig.getWarningType()).getValue(),
                            //指令类型
                            ExecTypeEnum.fromValue(dolphinRunInfoConfig.getExecType()).getValue(),
                            // 收件人
                            dolphinRunInfoConfig.getReceivers(),
                            // 收件人(抄送)
                            dolphinRunInfoConfig.getReceiversCc(),
                            //运行模式
                            RunModeEnum.fromValue(dolphinRunInfoConfig.getRunMode()).getValue(),
                            // 开始节点列表(节点name)
                            dolphinRunInfoConfig.getStartNodeList(),
                            //任务依赖类型
                            TaskDependTypeEnum.fromValue(dolphinRunInfoConfig.getTaskDependType()).getValue(),
                            // 超时时间
                            dolphinRunInfoConfig.getTimeout(),
                            // WORKER_GROUP
                            dolphinSchedulerInfoConfig.getTaskWorkerGroup()
                    );
            DqcConstant.verification(result, "运行失败{}");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

}
