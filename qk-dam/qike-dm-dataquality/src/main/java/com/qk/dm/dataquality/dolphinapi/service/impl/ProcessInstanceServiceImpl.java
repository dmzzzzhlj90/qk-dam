package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.datacenter.model.ResultProcessInstance;
import com.qk.datacenter.model.Resultstring;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.schedule.ExecuteTypeEnum;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.dolphinapi.service.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.qk.dam.datasource.utils.ConnectInfoConvertUtils.objectMapper;

/**
 * @author shenpj
 * @date 2021/11/23 2:30 下午
 * @since 1.0.0
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
    private final static Logger LOGGER= LoggerFactory.getLogger(ProcessInstanceServiceImpl.class);
    private final DefaultApi defaultApi;
    private final DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig;

    public ProcessInstanceServiceImpl(
            DefaultApi defaultApi, DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        this.defaultApi = defaultApi;
        this.dolphinSchedulerInfoConfig = dolphinSchedulerInfoConfig;
    }

    /**
     * execute
     * 执行流程实例的各种操作(暂停、停止、重跑、恢复等)
     * executeType 执行类型 (required)
     * processInstanceId 流程实例ID (required)
     * projectCode PROJECT_CODE (required)
     */
    @Override
    public void execute(Integer processInstanceId, String executeType) {
        try {
            Result result =
                    defaultApi.executeUsingPOST(
                            ExecuteTypeEnum.fromValue(executeType).getCode(), processInstanceId, dolphinSchedulerInfoConfig.getProjectCode()
                    );
            DqcConstant.verification(result, "执行流程实例操作失败{}，");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    /**
     * queryProcessInstanceListPaging
     * 查询流程实例列表
     * pageNo 页码号 (required)
     * pageSize 页大小 (required)
     * projectCode PROJECT_CODE (required)
     * endDate 结束时间 (optional)
     * executorName 流程名称 (optional)
     * host 运行任务的主机IP地址 (optional)
     * processDefineCode processDefineCode (optional, default to 0)
     * searchVal 搜索值 (optional)
     * startDate 开始时间 (optional)
     * stateType 工作流和任务节点的运行状态 (optional)
     */
    @Override
    public ProcessInstanceResultDTO search(ProcessInstanceSearchDTO instanceSearchDTO) {
        ProcessInstanceResultDTO processInstanceResultDTO = new ProcessInstanceResultDTO();
        try {
            Result result =
                    defaultApi.queryProcessInstanceListUsingGET(
                            instanceSearchDTO.getPageNo(),
                            instanceSearchDTO.getPageSize(),
                            dolphinSchedulerInfoConfig.getProjectCode(),
                            instanceSearchDTO.getEndDate(),
                            instanceSearchDTO.getExecutorName(),
                            instanceSearchDTO.getHost(),
                            instanceSearchDTO.getProcessDefineCode(),
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

    /**
     * 通过流程实例ID查询流程实例
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public ProcessInstanceDTO detail(Integer processInstanceId) {
        ProcessInstanceDTO processInstanceDTO = new ProcessInstanceDTO();
        try {
            Result result = defaultApi.queryProcessInstanceByIdUsingGET(processInstanceId, dolphinSchedulerInfoConfig.getProjectCode());
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
            ResultProcessInstance result = defaultApi.deleteProcessInstanceByIdUsingDELETE(processInstanceId, dolphinSchedulerInfoConfig.getProjectCode());
            DqcConstant.verification(result, "删除流程实例失败{}，");
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
    }

    @Override
    public void deleteBulk(ProcessInstanceDeleteDTO deleteDTO) {
        try {
            Result result =
                    defaultApi.batchDeleteProcessInstanceByIdsUsingPOST(
                            deleteDTO.getProcessInstanceIds(),
                            dolphinSchedulerInfoConfig.getProjectCode(),
                            deleteDTO.getAlertGroup(),
                            deleteDTO.getCreateTime(),
                            deleteDTO.getEmail(),
                            deleteDTO.getId(),
                            deleteDTO.getPhone(),
                            deleteDTO.getQueue(),
                            deleteDTO.getQueueName(),
                            deleteDTO.getState(),
                            deleteDTO.getTenantCode(),
                            deleteDTO.getTenantId(),
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
        Result result = null;
        try {
            result =
                    defaultApi.queryTaskListPagingUsingGET(
                            TaskInstanceSearch.getPageNo(),
                            TaskInstanceSearch.getPageSize(),
                            dolphinSchedulerInfoConfig.getProjectCode(),
                            TaskInstanceSearch.getEndDate(),
                            TaskInstanceSearch.getExecutorName(),
                            TaskInstanceSearch.getHost(),
                            TaskInstanceSearch.getProcessInstanceId(),
                            TaskInstanceSearch.getProcessInstanceName(),
                            TaskInstanceSearch.getSearchVal(),
                            TaskInstanceSearch.getStartDate(),
                            TaskInstanceSearch.getStateType(),
                            TaskInstanceSearch.getTaskName()
                    );
            DqcConstant.verification(result, "查询任务实例列表失败{}，");
        } catch (ApiException e) {
            LOGGER.info("=============接口入参：{}==============",TaskInstanceSearch);
            LOGGER.info("=============接口结果：{}==============",result);
            DqcConstant.printException(e);
        }
        try {
            processTaskInstanceResultDTO = objectMapper.readValue(objectMapper.writeValueAsString(result.getData()), ProcessTaskInstanceResultDTO.class);
        } catch (Exception e) {
            LOGGER.info("=============接口结果：{}==============",result);
            LOGGER.info("=============数据转换出错：{}==============",processTaskInstanceResultDTO);
            DqcConstant.printException(e);
        }
        return processTaskInstanceResultDTO;
    }

    @Override
    public String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum) {
        try {
            Resultstring result =
                    defaultApi.queryLogUsingGET(limit, skipLineNum, taskInstanceId);
            DqcConstant.verification(result, "查询任务实例日志失败{}，");
            return result.getData();
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return null;
    }

    @Override
    public String taskLogDownload(Integer taskInstanceId) {
        try {
            Resultstring result =
                    defaultApi.queryLogUsingGET(0, 100000, taskInstanceId);
            DqcConstant.verification(result, "查询任务实例日志失败{}，");
            return result.getData();
        } catch (ApiException e) {
            DqcConstant.printException(e);
        }
        return null;
    }


}
