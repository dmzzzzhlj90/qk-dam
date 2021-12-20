package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.client.DataBaseInfoDefaultApi;
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
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wjq
 * @date 2021/11/16
 * @since 1.0.0
 */
@Service
public class ProcessDefinitionApiServiceImpl implements ProcessDefinitionApiService {


    private final DefaultApi defaultApi;
    private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;
    private final DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig;
    private final DolphinRunInfoConfig dolphinRunInfoConfig;

    @Autowired
    public ProcessDefinitionApiServiceImpl(DefaultApi defaultApi,
                                           DataBaseInfoDefaultApi dataBaseInfoDefaultApi,
                                           DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                           DolphinRunInfoConfig dolphinRunInfoConfig) {
        this.defaultApi = defaultApi;
        this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
        this.dolphinSchedulerInfoConfig = dolphinSchedulerInfoConfig;
        this.dolphinRunInfoConfig = dolphinRunInfoConfig;
    }

    @Override
    public int saveAndFlush(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        int processDefinitionId = 0;
        //是否存在工作流
        ProcessDefinitionDTO queryProcessDefinition =
                queryProcessDefinitionInfo(dolphinSchedulerInfoConfig.getProjectName(), dqcSchedulerBasicInfoVO.getJobName(), dqcSchedulerBasicInfoVO.getJobId());
        //获取数据源信息
        Map<String, ConnectBasicInfo> dataSourceInfo = getDataSourceInfo(dqcSchedulerBasicInfoVO);

        if (null == queryProcessDefinition) {
            //新增
            save(dqcSchedulerBasicInfoVO, dataSourceInfo);
            ProcessDefinitionDTO saveProcessDefinition =
                    queryProcessDefinitionInfo(dolphinSchedulerInfoConfig.getProjectName(), dqcSchedulerBasicInfoVO.getJobName(), dqcSchedulerBasicInfoVO.getJobId());
            processDefinitionId = saveProcessDefinition.getId();
        } else {
            //编辑
            update(dqcSchedulerBasicInfoVO, dataSourceInfo);
            processDefinitionId = queryProcessDefinition.getId();
        }
        return processDefinitionId;
    }

    private Map<String, ConnectBasicInfo> getDataSourceInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        List<String> dataSourceNames = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList().stream().map(DqcSchedulerRulesVO::getDataSourceName).collect(Collectors.toList());
        return dataBaseInfoDefaultApi.getDataSourceMap(dataSourceNames);
    }

    @Override
    public void save(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo) {
        try {
            // 获取DolphinScheduler 资源信息
            ResourceDTO mySqlScriptResource = ResourceFileManager.queryMySqlScriptResource(defaultApi, dolphinSchedulerInfoConfig);
            // 获取DolphinScheduler 租户信息
            TenantDTO tenantDTO = TenantManager.queryTenantInfo(defaultApi, dolphinSchedulerInfoConfig);
            // 构建ProcessData对象
            ProcessDataDTO processDataDTO = ProcessDataExecutor.dqcProcessData(dqcSchedulerBasicInfoVO, mySqlScriptResource, tenantDTO, dolphinSchedulerInfoConfig, dataSourceInfo);
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
    public void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo) {
        try {
            Long processDefinitionId = dqcSchedulerBasicInfoVO.getProcessDefinitionId();
            // 获取DolphinScheduler 资源信息
            ResourceDTO mySqlScriptResource = ResourceFileManager.queryMySqlScriptResource(defaultApi, dolphinSchedulerInfoConfig);
            // 获取DolphinScheduler 租户信息
            TenantDTO tenantDTO = TenantManager.queryTenantInfo(defaultApi, dolphinSchedulerInfoConfig);
            // 构建ProcessData对象
            ProcessDataDTO processDataDTO = ProcessDataExecutor.dqcProcessData(dqcSchedulerBasicInfoVO, mySqlScriptResource, tenantDTO, dolphinSchedulerInfoConfig, dataSourceInfo);
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
//            e.printStackTrace();
//            throw new BizException("未获取到实例ID!!!");
        }
        return processDefinitionDTO;
    }

    @Override
    public void delete(String projectName, Long processDefinitionId) {
        try {
            defaultApi.deleteProcessDefinitionByIdUsingGET(projectName, processDefinitionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBulk(String projectName, List<Long> processDefinitionIdList) {
        try {
            String processDefinitionIds = processDefinitionIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
            defaultApi.batchDeleteProcessDefinitionByIdsUsingGETWithHttpInfo(projectName, processDefinitionIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****************************************************************************/

    /**
     * release
     * 发布流程定义
     * code 流程定义编码 (required)
     * projectCode PROJECT_CODE (required)
     * releaseState PROCESS_DEFINITION_RELEASE (required) 0-下线 1-上线
     */
    @Override
    public void release(Long processDefinitionCode, Integer releaseState) {
        try {
            Result result =
                    defaultApi.releaseProcessDefinitionUsingPOST(
                            processDefinitionCode, dolphinSchedulerInfoConfig.getProjectCode(), releaseState
                    );
            DqcConstant.verification(result, "流程定义发布失败{}");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    /**
     * startCheckProcessDefinition
     * 检查流程定义
     * processDefinitionCode 流程定义编码 (required)
     */
    @Override
    public void startCheck(Long processDefinitionCode) {
        try {
            Result result =
                    defaultApi.startCheckProcessDefinitionUsingPOST(processDefinitionCode);
            DqcConstant.verification(result, "检查流程失败{}");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    /**
     * startProcessInstance
     * 运行流程实例
     * failureStrategy 失败策略 (required)
     * processDefinitionCode 流程定义编码 (required)
     * processInstancePriority 流程实例优先级 (required)
     * projectCode PROJECT_CODE (required)
     * scheduleTime 定时时间 (required)
     * warningGroupId 发送组ID (required)
     * warningType 发送策略 (required)
     * dryRun dryRun (optional, default to 0)
     * environmentCode ENVIRONMENT_CODE (optional)
     * execType 指令类型 (optional)
     * expectedParallelismNumber 补数任务自定义并行度 (optional)
     * runMode 运行模式 (optional)
     * startNodeList 开始节点列表(节点name) (optional)
     * startParams 启动参数 (optional)
     * taskDependType 任务依赖类型 (optional)
     * timeout 超时时间 (optional)
     * workerGroup worker群组 (optional)
     */
    @Override
    public void startInstance(Long processDefinitionCode) {
        try {
            Result result =
                    defaultApi.startProcessInstanceUsingPOST(
                            FailureStrategyEnum.fromValue(dolphinRunInfoConfig.getFailureStrategy()).getCode(),
                            processDefinitionCode,
                            ProcessInstancePriorityEnum.fromValue(dolphinRunInfoConfig.getProcessInstancePriority()).getCode(),
                            dolphinSchedulerInfoConfig.getProjectCode(),
                            dolphinRunInfoConfig.getScheduleTime(),
                            dolphinRunInfoConfig.getWarningGroupId(),
                            WarningTypeEnum.fromValue(dolphinRunInfoConfig.getWarningType()).getCode(),
                            dolphinRunInfoConfig.getDryRun(),
                            dolphinRunInfoConfig.getEnvironmentCode(),
                            dolphinRunInfoConfig.getExecType(),
                            dolphinRunInfoConfig.getExpectedParallelismNumber(),
                            RunModeEnum.fromValue(dolphinRunInfoConfig.getRunMode()).getCode(),
                            dolphinRunInfoConfig.getStartNodeList(),
                            dolphinRunInfoConfig.getStartParams(),
                            TaskDependTypeEnum.fromValue(dolphinRunInfoConfig.getTaskDependType()).getCode(),
                            dolphinRunInfoConfig.getTimeout(),
                            dolphinSchedulerInfoConfig.getTaskWorkerGroup()
                    );
            DqcConstant.verification(result, "运行失败{}");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

}
