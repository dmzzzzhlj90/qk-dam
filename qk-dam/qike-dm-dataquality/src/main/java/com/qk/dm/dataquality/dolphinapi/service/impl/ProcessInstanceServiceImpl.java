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
                            ExecuteTypeEnum.fromValue(executeType).getCode(), processInstanceId, dolphinSchedulerInfoConfig.getProjectName());
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
                            instanceSearchDTO.getStateType());
            DqcConstant.verification(result, "查询流程实例列表失败{}，");
            return GsonUtil.fromJsonString(
                    GsonUtil.toJsonString(result.getData()),
                    new TypeToken<ProcessInstanceResultDTO>() {
                    }.getType());
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return null;
    }

    @Override
    public ProcessInstanceDTO detail(Integer processInstanceId) {
        try {
            Result result =
                    defaultApi.queryProcessInstanceByIdUsingGET(
                            dolphinSchedulerInfoConfig.getProjectName(), processInstanceId);
            DqcConstant.verification(result, "查询流程实例通过流程实例ID失败{}，");
            return GsonUtil.fromJsonString(
                    GsonUtil.toJsonString(result.getData()),
                    new TypeToken<ProcessInstanceDTO>() {
                    }.getType());
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return null;
    }

    @Override
    public ProcessTaskInstanceResultDTO searchTask(ProcessTaskInstanceSearchDTO TaskInstanceSearch) {
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
                            TaskInstanceSearch.getTaskName());
            DqcConstant.verification(result, "查询任务实例列表失败{}，");
            return GsonUtil.fromJsonString(
                    GsonUtil.toJsonString(result.getData()),
                    new TypeToken<ProcessTaskInstanceResultDTO>() {
                    }.getType());
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return null;
    }


}
