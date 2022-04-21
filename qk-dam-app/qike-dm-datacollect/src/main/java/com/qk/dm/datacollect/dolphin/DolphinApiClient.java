package com.qk.dm.datacollect.dolphin;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.ProcessInstance;
import com.qk.datacenter.model.Result;
import com.qk.datacenter.model.Resultstring;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shenpj
 * @date 2022/4/21 14:41
 * @since 1.0.0
 */
@Service
public class DolphinApiClient {
    private final DolphinschedulerManager dolphinschedulerManager;

    public DolphinApiClient(DolphinschedulerManager dolphinschedulerManager) {
        this.dolphinschedulerManager = dolphinschedulerManager;
    }

    /**
     * 删除流程定义
     * @param code
     * @param projectCode
     * @return
     * @throws ApiException
     */
    public Result dolphin_process_delete(Long code, Long projectCode) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().deleteProcessDefinitionByCodeUsingDELETE(code, projectCode);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }

    /**
     * 流程定义上下线
     *
     * @param code         流程定义编码
     * @param projectCode
     * @param releaseState
     * @return
     * @throws ApiException
     */
    public Result dolphin_process_release(Long code, Long projectCode, ProcessDefinition.ReleaseStateEnum releaseState) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().releaseProcessDefinitionUsingPOST(code, projectCode, releaseState);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }

    /**
     * 检查流程定义
     *
     * @param processDefinitionCode
     * @param projectCode
     * @return
     * @throws ApiException
     */
    public Result dolphin_process_check(Long processDefinitionCode, Long projectCode) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().startCheckProcessDefinitionUsingPOST(
                processDefinitionCode,
                projectCode);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }

    /**
     * 流程定义运行
     *
     * @param projectCode
     * @param processDefinitionCode
     * @param environmentCode
     * @return
     * @throws ApiException
     */
    public Result dolphin_process_runing(Long processDefinitionCode, Long projectCode, Long environmentCode) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().startProcessInstanceUsingPOST(
                ProcessInstance.FailureStrategyEnum.CONTINUE,
                processDefinitionCode,
                ProcessInstance.ProcessInstancePriorityEnum.MEDIUM,
                projectCode,
                "",
                0,
                ProcessInstance.WarningTypeEnum.NONE,
                null,
                environmentCode,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }

    /**
     * 流程定义详情
     *
     * @param processDefinitionCode
     * @param projectCode
     * @return
     * @throws ApiException
     */
    public Result dolphin_process_detail(Long processDefinitionCode, Long projectCode) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().queryProcessDefinitionByCodeUsingGET(
                processDefinitionCode,
                projectCode
        );
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }

    /**
     * 流程定义列表
     *
     * @param projectCode
     * @param searchVal
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    public Result dolphin_process_list(Long projectCode,
                                       String searchVal,
                                       Integer pageNo,
                                       Integer pageSize) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().queryProcessDefinitionListPagingUsingGET(
                pageNo,
                pageSize,
                projectCode,
                searchVal,
                null);

//        ProcessDefinitionResultDTO processDefinitionResultDTO = DctConstant.changeObjectToClass(result.getData(), ProcessDefinitionResultDTO.class);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }


    /**
     * 创建定时
     *
     * @param processDefinitionCode
     * @param projectCode
     * @param effectiveTimeStart
     * @param effectiveTimeEnt
     * @param cron
     * @throws ApiException
     */
    public void schedule_create(Long processDefinitionCode,
                                Long projectCode,
                                Date effectiveTimeStart,
                                Date effectiveTimeEnt,
                                String cron) throws ApiException {
        DolphinScheduleDefinition dolphinScheduleDefinition = new DolphinScheduleDefinition(effectiveTimeStart, effectiveTimeEnt, cron);
        Result result =
                dolphinschedulerManager.defaultApi().createScheduleUsingPOST(
                        processDefinitionCode,
                        projectCode,
                        dolphinScheduleDefinition.getEnvironmentCode(),
                        dolphinScheduleDefinition.getFailureStrategy(),
                        dolphinScheduleDefinition.getProcessInstancePriority(),
                        dolphinScheduleDefinition.getSchedule(),
                        dolphinScheduleDefinition.getWarningGroupId(),
                        dolphinScheduleDefinition.getWarningType(),
                        dolphinScheduleDefinition.getWorkerGroup());
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
    }

    /**
     * 修改定时
     *
     * @param scheduleId
     * @param projectCode
     * @param effectiveTimeStart
     * @param effectiveTimeEnt
     * @param cron
     * @throws ApiException
     */
    public void schedule_update(Integer scheduleId,
                                Long projectCode,
                                Date effectiveTimeStart,
                                Date effectiveTimeEnt,
                                String cron) throws ApiException {
        DolphinScheduleDefinition dolphinScheduleDefinition = new DolphinScheduleDefinition(effectiveTimeStart, effectiveTimeEnt, cron);
        Result result =
                dolphinschedulerManager.defaultApi().updateScheduleUsingPUT(
                        scheduleId,
                        projectCode,
                        dolphinScheduleDefinition.getEnvironmentCode(),
                        dolphinScheduleDefinition.getFailureStrategy(),
                        dolphinScheduleDefinition.getProcessInstancePriority(),
                        dolphinScheduleDefinition.getSchedule(),
                        dolphinScheduleDefinition.getWarningGroupId(),
                        dolphinScheduleDefinition.getWarningType(),
                        dolphinScheduleDefinition.getWorkerGroup());
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }

    }

    /**
     * 定时上线
     *
     * @param scheduleId
     * @param projectCode
     * @throws ApiException
     */
    public void schedule_online(Integer scheduleId, Long projectCode) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().onlineUsingPOST(scheduleId, projectCode);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
    }

    /**
     * 定时下线
     *
     * @param scheduleId
     * @param projectCode
     * @throws ApiException
     */
    public void schedule_offline(Integer scheduleId, Long projectCode) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().offlineUsingPOST(scheduleId, projectCode);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
    }

    /**
     * 定时删除
     *
     * @param scheduleId
     * @param projectCode
     * @throws ApiException
     */
    public void schedule_delete(Integer scheduleId, Long projectCode) throws ApiException {
        Result result =
                dolphinschedulerManager.defaultApi().deleteScheduleByIdUsingDELETE(
                        scheduleId,
                        projectCode,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
    }

    /**
     * 查询某流程定义的定时
     *
     * @param processDefinitionCode
     * @param projectCode
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    public Result schedule_search(Long processDefinitionCode,
                                  Long projectCode,
                                  Integer pageNo,
                                  Integer pageSize) throws ApiException {
        Result result =
                dolphinschedulerManager.defaultApi().queryScheduleListPagingUsingGET(
                        processDefinitionCode,
                        projectCode,
                        pageNo,
                        pageSize,
                        null);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
//        return DctConstant.changeObjectToClass(result.getData(), ScheduleResultDTO.class);
        return result;
    }

    /**
     * 流程实例操作
     * executeType 执行类型 (required)
     * processInstanceId 流程实例ID (required)
     * projectCode PROJECT_CODE (required)
     */
    public void instance_execute(Integer processInstanceId,
                                 Long projectCode,
                                 ProcessInstance.CmdTypeIfComplementEnum executeType) throws ApiException {
        Result result =
                dolphinschedulerManager.defaultApi().executeUsingPOST(
                        executeType,
                        processInstanceId,
                        projectCode
                );
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
    }

    /**
     * 流程实例查询
     *
     * @param projectCode
     * @param instanceSearchDTO
     * @return
     * @throws ApiException
     */
    public Result instance_search(Long projectCode, ProcessInstanceSearchDTO instanceSearchDTO) throws ApiException {
        Result result =
                dolphinschedulerManager.defaultApi().queryProcessInstanceListUsingGET(
                        instanceSearchDTO.getPageNo(),
                        instanceSearchDTO.getPageSize(),
                        projectCode,
                        instanceSearchDTO.getEndDate(),
                        instanceSearchDTO.getExecutorName(),
                        instanceSearchDTO.getHost(),
                        instanceSearchDTO.getProcessDefineCode(),
                        instanceSearchDTO.getSearchVal(),
                        instanceSearchDTO.getStartDate(),
                        instanceSearchDTO.getStateType()
                );
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
//        return DctConstant.changeObjectToClass(result.getData(), ProcessInstanceResultDTO.class);
        return result;
    }

    /**
     * 流程实例详情
     *
     * @param processInstanceId
     * @param projectCode
     * @return
     * @throws ApiException
     */
    public Result instance_detail(Integer processInstanceId, Long projectCode) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().queryProcessInstanceByIdUsingGET(
                processInstanceId,
                projectCode
        );
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
//        return DctConstant.changeObjectToClass(result.getData(), ProcessInstanceDTO.class);
        return result;
    }

    /**
     * 根据流程实例查询任务实例
     *
     * @param processInstanceId
     * @param projectCode
     * @return
     * @throws ApiException
     */
    public Result taskByProcessId(Integer processInstanceId, Long projectCode) throws ApiException {
        Result result =
                dolphinschedulerManager.defaultApi().queryTaskListByProcessIdUsingGET(
                        processInstanceId,
                        projectCode
                );
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        //ProcessTaskInstanceResultDTO
        return result;
    }

    /**
     * 任务执行日志
     *
     * @param taskInstanceId
     * @param limit
     * @param skipLineNum
     * @return
     * @throws ApiException
     */
    public Resultstring taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum) throws ApiException {
        Resultstring result =
                dolphinschedulerManager.defaultApi().queryLogUsingGET(
                        limit,
                        skipLineNum,
                        taskInstanceId
                );
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }
}
