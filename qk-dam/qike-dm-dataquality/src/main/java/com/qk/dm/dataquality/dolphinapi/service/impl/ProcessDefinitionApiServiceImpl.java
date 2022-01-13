package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
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
import com.qk.dm.dataquality.dolphinapi.executor.TaskDefinitionExecutor;
import com.qk.dm.dataquality.dolphinapi.executor.TaskRelationExecutor;
import com.qk.dm.dataquality.dolphinapi.manager.ResourceFileManager;
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

    public static final int poll_count = 5;
    public static final int poll_time = 1000;
    public static final long DEFINITION_CODE = 0L;
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
    public Long saveAndFlush(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        Long processDefinitionCode = DEFINITION_CODE;
        //是否存在工作流
        ProcessDefinitionDTO queryProcessDefinition =
                queryProcessDefinitionInfo(
                        dolphinSchedulerInfoConfig.getProjectCode(),
                        dqcSchedulerBasicInfoVO.getJobName(),
                        dqcSchedulerBasicInfoVO.getJobId());
        //获取数据源信息
        Map<String, ConnectBasicInfo> dataSourceInfo = getDataSourceInfo(dqcSchedulerBasicInfoVO);

        if (null == queryProcessDefinition) {
            //新增
            Integer version = SchedulerConstant.ZERO_VALUE;
            save(dqcSchedulerBasicInfoVO, dataSourceInfo, version);
            //轮询查看是否创建工作流成功
            processDefinitionCode = getDefinitionCode(dqcSchedulerBasicInfoVO, processDefinitionCode);
            if (processDefinitionCode == 0L) {
                throw new BizException("创建工作流失败!!!");
            }
        } else {
            //编辑
            processDefinitionCode = queryProcessDefinition.getCode();
            update(dqcSchedulerBasicInfoVO, dataSourceInfo, queryProcessDefinition);

        }
        return processDefinitionCode;
    }

    /**
     * 轮询查看是否创建工作流成功
     */
    private Long getDefinitionCode(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Long processDefinitionCode) {
        for (int i = 0; i < poll_count; i++) {
            try {
                ProcessDefinitionDTO saveProcessDefinition =
                        queryProcessDefinitionInfo(
                                dolphinSchedulerInfoConfig.getProjectCode(),
                                dqcSchedulerBasicInfoVO.getJobName(),
                                dqcSchedulerBasicInfoVO.getJobId());
                processDefinitionCode = saveProcessDefinition.getCode();
                if (processDefinitionCode != 0L && saveProcessDefinition.getCode() != null) {
                    break;
                }
                Thread.sleep(poll_time);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException("创建工作流失败!!!");
            }
        }
        return processDefinitionCode;
    }

    private Map<String, ConnectBasicInfo> getDataSourceInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        List<String> dataSourceNames = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList().stream().map(DqcSchedulerRulesVO::getDataSourceName).collect(Collectors.toList());
        return dataBaseInfoDefaultApi.getDataSourceMap(dataSourceNames);
    }

    @Override
    public void save(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo, Integer version) {
        try {
            // 构建locations
            String locations = getLocations(dqcSchedulerBasicInfoVO);
            // 构建TaskDefinition对象
            String taskDefinitionJson = getTaskDefinitionJson(dqcSchedulerBasicInfoVO, dataSourceInfo, null);
            //构建流程定义节点关联集合
            String taskRelationJson = getTaskRelationJson(dqcSchedulerBasicInfoVO, null);
            //基础信息
            String name = dqcSchedulerBasicInfoVO.getJobName();
            Long projectCode = dolphinSchedulerInfoConfig.getProjectCode();
            String tenantCode = dolphinSchedulerInfoConfig.getTenantRoot();
            String description = dqcSchedulerBasicInfoVO.getJobId();
            String globalParams = SchedulerConstant.EMPTY_ARRAY;
            Integer timeout = SchedulerConstant.ZERO_VALUE;
            //执行创建
            defaultApi.createProcessDefinitionUsingPOSTWithHttpInfo(
                    locations, name, projectCode, taskDefinitionJson, taskRelationJson, tenantCode, description, globalParams, timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dqcSchedulerBasicInfoVO
     * @param dataSourceInfo
     * @param processDefinitionDTO
     */
    @Override
    public void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo, ProcessDefinitionDTO processDefinitionDTO) {
        try {
            // 构建locations
            String locations = getLocations(dqcSchedulerBasicInfoVO);
            // 构建TaskDefinition对象
            int version = processDefinitionDTO.getVersion();
            String taskDefinitionJson = getTaskDefinitionJson(dqcSchedulerBasicInfoVO, dataSourceInfo, version);
            //构建流程定义节点关联集合
            String taskRelationJson = getTaskRelationJson(dqcSchedulerBasicInfoVO, version);
            //基础信息
            String name = dqcSchedulerBasicInfoVO.getJobName();
            Long projectCode = dolphinSchedulerInfoConfig.getProjectCode();
            String tenantCode = dolphinSchedulerInfoConfig.getTenantRoot();
            String description = dqcSchedulerBasicInfoVO.getJobId();
            String globalParams = SchedulerConstant.EMPTY_ARRAY;
            Integer timeout = SchedulerConstant.ZERO_VALUE;
            Long processDefinitionCode = processDefinitionDTO.getCode();
            //执行更新
            defaultApi.updateProcessDefinitionUsingPUT(
                    processDefinitionCode, locations, name, projectCode, taskDefinitionJson, taskRelationJson, tenantCode, description, globalParams, "OFFLINE", timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询工作流实例是否存在
     *
     * @param projectCode
     * @param searchVal
     * @param jobId
     * @return ProcessDefinitionDTO
     */
    @Override
    public ProcessDefinitionDTO queryProcessDefinitionInfo(Long projectCode, String searchVal, String jobId) {
        ProcessDefinitionDTO processDefinitionDTO = null;
        try {
            Result result = defaultApi.
                    queryProcessDefinitionListPagingUsingGET(
                            SchedulerConstant.PAGE_NO,
                            SchedulerConstant.PAGE_SIZE,
                            projectCode,
                            searchVal,
                            null);
            Object data = result.getData();
            ProcessResultDataDTO processResultDataDTO =
                    GsonUtil.fromJsonString(GsonUtil.toJsonString(data), new TypeToken<ProcessResultDataDTO>() {
                    }.getType());
            if (processResultDataDTO.getTotal() != 0) {
                List<ProcessDefinitionDTO> totalList = processResultDataDTO.getTotalList();

                List<ProcessDefinitionDTO> processDefinitions = totalList.stream()
                        .filter(processDefinition -> processDefinition.getName().equals(searchVal) && processDefinition.getDescription().equals(jobId))
                        .collect(Collectors.toList());
                processDefinitionDTO = processDefinitions.get(0);
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BizException("未获取到实例ID!!!");
        }
        return processDefinitionDTO;
    }

    @Override
    public void delete(Long processDefinitionCode,Long projectCode) {
        try {
            defaultApi.deleteProcessDefinitionByCodeUsingDELETE(processDefinitionCode.intValue(),projectCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBulk(List<Long> processDefinitionIdList,Long projectCode) {
        try {
            String processDefinitionIds = processDefinitionIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
            defaultApi.batchDeleteProcessDefinitionByCodesUsingPOST(processDefinitionIds, projectCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建locations
     */
    private String getLocations(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        List<TaskNodeLocation> locationList =
                LocationsExecutor
                        .dqcLocations(
                                dqcSchedulerBasicInfoVO,
                                dolphinSchedulerInfoConfig);
        return GsonUtil.toJsonString(locationList);
    }

    /**
     * 构建TaskDefinition对象
     */
    private String getTaskDefinitionJson(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Map<String, ConnectBasicInfo> dataSourceInfo, Integer version) {
        // 获取DolphinScheduler 资源信息
        ResourceDTO mySqlScriptResource = ResourceFileManager.queryMySqlScriptResource(defaultApi, dolphinSchedulerInfoConfig);
//        // 获取DolphinScheduler 租户信息
//        TenantDTO tenantDTO = TenantManager.queryTenantInfo(defaultApi, dolphinSchedulerInfoConfig);
        // 构建taskDefinitionDTOList
        List<TaskDefinitionDTO> taskDefinitionDTOList =
                TaskDefinitionExecutor
                        .dqcTaskDefinitionData(
                                dqcSchedulerBasicInfoVO,
                                mySqlScriptResource,
                                dolphinSchedulerInfoConfig,
                                dataSourceInfo,
                                version);
        return GsonUtil.toJsonString(taskDefinitionDTOList);
    }

    /**
     * 构建流程定义节点关联集合
     */
    private String getTaskRelationJson(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Integer version) {
        List<TaskRelationDTO> taskRelationList =
                TaskRelationExecutor
                        .dqcTaskRelations(
                                dqcSchedulerBasicInfoVO,
                                version);
        return GsonUtil.toJsonString(taskRelationList);
    }


    /****************************************************************************/

    /**
     * release
     * 发布流程定义
     * code 流程定义编码 (required)
     * projectCode PROJECT_CODE (required)
     * releaseState PROCESS_DEFINITION_RELEASE (required)
     */
    @Override
    public void release(Long processDefinitionCode, String releaseState) {
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
                    defaultApi.startCheckProcessDefinitionUsingPOST(processDefinitionCode,dolphinSchedulerInfoConfig.getProjectCode());
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
