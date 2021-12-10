package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.ExecuteTypeEnum;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.*;
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
    private final DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig;

    public ProcessInstanceServiceImpl(
            DefaultApi defaultApi, DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        this.defaultApi = defaultApi;
        this.dolphinSchedulerInfoConfig = dolphinSchedulerInfoConfig;
    }

    /**
     * 实例-操作
     *
     * @param processInstanceId
     * @param executeType
     */
    @Override
    public void execute(Integer processInstanceId, String executeType) {
        try {
            Result result =
                    defaultApi.executeUsingPOST(
                            ExecuteTypeEnum.fromValue(executeType).getCode(), processInstanceId, dolphinSchedulerInfoConfig.getProjectName()
                    );
            DqcConstant.verification(result, "执行流程实例操作失败{}，");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    /**
     * 查询流程实例列表
     *
     * @param instanceSearchDTO
     * @return
     */
    @Override
    public ProcessInstanceResultDTO search(ProcessInstanceSearchDTO instanceSearchDTO) {
        ProcessInstanceResultDTO processInstanceResultDTO = new ProcessInstanceResultDTO();
        try {
            Result result =
                    defaultApi.queryProcessInstanceListUsingGET(
                            dolphinSchedulerInfoConfig.getProjectName(),
                            instanceSearchDTO.getEndDate(),
                            instanceSearchDTO.getExecutorName(),
                            instanceSearchDTO.getHost(),
                            instanceSearchDTO.getPageNo(),
                            instanceSearchDTO.getPageSize(),
                            instanceSearchDTO.getProcessDefinitionId(),
                            instanceSearchDTO.getSearchVal(),
                            instanceSearchDTO.getStartDate(),
                            instanceSearchDTO.getStateType()
                    );
            DqcConstant.verification(result, "查询流程实例列表失败{}，");
            processInstanceResultDTO = GsonUtil.fromJsonString(GsonUtil.toJsonString(result.getData()), new TypeToken<ProcessInstanceResultDTO>() {
            }.getType());
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return processInstanceResultDTO;
    }

    @Override
    public ProcessInstanceDTO detail(Integer processInstanceId) {
        ProcessInstanceDTO processInstanceDTO = new ProcessInstanceDTO();
        try {
            Result result =
                    defaultApi.queryProcessInstanceByIdUsingGET(
                            dolphinSchedulerInfoConfig.getProjectName(), processInstanceId
                    );
            DqcConstant.verification(result, "查询流程实例通过流程实例ID失败{}，");
            processInstanceDTO = GsonUtil.fromJsonString(GsonUtil.toJsonString(result.getData()), new TypeToken<ProcessInstanceDTO>() {
            }.getType());
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return processInstanceDTO;
    }

    @Override
    public void deleteOne(Integer processInstanceId) {
        try {
            Result result =
                    defaultApi.deleteProcessInstanceByIdUsingGET(
                            dolphinSchedulerInfoConfig.getProjectName(), processInstanceId
                    );
            DqcConstant.verification(result, "删除流程实例失败{}，");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    @Override
    public void deleteBulk(ProcessInstanceDeleteDTO deleteDTO) {
        try {
            Result result =
                    defaultApi.batchDeleteProcessInstanceByIdsUsingGET(
                            deleteDTO.getProcessInstanceIds(),
                            dolphinSchedulerInfoConfig.getProjectName(),
                            deleteDTO.getAlertGroup(),
                            deleteDTO.getCreateTime(),
                            deleteDTO.getEmail(),
                            deleteDTO.getId(),
                            deleteDTO.getPhone(),
                            deleteDTO.getQueue(),
                            deleteDTO.getQueueName(),
                            deleteDTO.getTenantCode(),
                            deleteDTO.getTenantId(),
                            deleteDTO.getTenantName(),
                            deleteDTO.getUpdateTime(),
                            deleteDTO.getUserName(),
                            deleteDTO.getUserPassword(),
                            deleteDTO.getUserType()
                    );
            DqcConstant.verification(result, "批量删除流程实例失败{}，");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }


    @Override
    public ProcessTaskInstanceResultDTO searchTask(ProcessTaskInstanceSearchDTO TaskInstanceSearch) {
        ProcessTaskInstanceResultDTO processTaskInstanceResultDTO = new ProcessTaskInstanceResultDTO();
        try {
            Result result =
                    defaultApi.queryTaskListPagingUsingGET(
                            dolphinSchedulerInfoConfig.getProjectName(),
                            TaskInstanceSearch.getEndDate(),
                            TaskInstanceSearch.getExecutorName(),
                            TaskInstanceSearch.getHost(),
                            TaskInstanceSearch.getPageNo(),
                            TaskInstanceSearch.getPageSize(),
                            TaskInstanceSearch.getProcessInstanceId(),
                            TaskInstanceSearch.getSearchVal(),
                            TaskInstanceSearch.getStartDate(),
                            TaskInstanceSearch.getStateType(),
                            TaskInstanceSearch.getTaskName()
                    );
            DqcConstant.verification(result, "查询任务实例列表失败{}，");
            processTaskInstanceResultDTO = GsonUtil.fromJsonString(GsonUtil.toJsonString(result.getData()), new TypeToken<ProcessTaskInstanceResultDTO>() {
            }.getType());
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return processTaskInstanceResultDTO;
    }

    @Override
    public String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum) {
        try {
            Result result =
                    defaultApi.queryLogUsingGET(limit, skipLineNum, taskInstanceId);
            DqcConstant.verification(result, "查询任务实例日志失败{}，");
            return (String) result.getData();
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return null;
    }

    @Override
    public String taskLogDownload(Integer taskInstanceId) {
        try {
            Result result =
                    defaultApi.queryLogUsingGET(0, 100000, taskInstanceId);
            DqcConstant.verification(result, "查询任务实例日志失败{}，");
            return (String) result.getData();
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return null;
    }


}
