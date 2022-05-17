package com.qk.dm.dataingestion.datax;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.dam.commons.util.CodeGenerateUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.ProcessInstance;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataingestion.enums.FailureStrategy;
import com.qk.dm.dataingestion.enums.ProcessInstancePriority;
import com.qk.dm.dataingestion.model.DolphinProcessDefinition;
import com.qk.dm.dataingestion.model.DolphinTaskDefinitionPropertiesBean;
import com.qk.dm.dataingestion.util.CronUtil;
import com.qk.dm.dataingestion.vo.ProcessDefinitionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class DataxDolphinClient {
    private final DolphinschedulerManager dolphinschedulerManager;

    private final DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean;


    public DataxDolphinClient(DolphinschedulerManager dolphinschedulerManager, DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean) {
        this.dolphinschedulerManager = dolphinschedulerManager;
        this.dolphinTaskDefinitionPropertiesBean = dolphinTaskDefinitionPropertiesBean;
    }

    public Result createProcessDefinition(String name,
                                          String dataxJson) throws ApiException {
        long taskCode =0L;
        try {
            taskCode = CodeGenerateUtils.getInstance().genCode();
        } catch (CodeGenerateUtils.CodeGenerateException e) {
            e.printStackTrace();
        }
        return createProcessDefinition(name,taskCode,dataxJson);

    }
    public Result createProcessDefinition(String name,
                                          long taskCode,
                                          String dataxJson) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(taskCode, name, dataxJson, dolphinTaskDefinitionPropertiesBean);
        Result result = dolphinschedulerManager.defaultApi().createProcessDefinitionUsingPOST(processDefinition.getLocations(),
                processDefinition.getName(),
                dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                processDefinition.getTaskDefinitionJson(),
                processDefinition.getTaskRelationJson(),
                processDefinition.getTenantCode(),
                "",
                "[]", 0);
        if (Boolean.TRUE.equals(result.getFailed())){
            throw new ApiException(400, result.getMsg());
        }
        return result;

    }

    public Result updateProcessDefinition(Long processDefinitionCode,
                                          String name,
                                          long taskCode,
                                          String dataxJson,
                                          DolphinTaskDefinitionPropertiesBean taskParam) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode,
                name,
                dataxJson,
                taskParam,
                dolphinTaskDefinitionPropertiesBean);
        Result result = dolphinschedulerManager.defaultApi().updateProcessDefinitionUsingPUT(
                processDefinitionCode,
                processDefinition.getLocations(),
                processDefinition.getName(),
                dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                processDefinition.getTaskDefinitionJson(),
                processDefinition.getTaskRelationJson(),
                processDefinition.getTenantCode(),
                "",
                "[]",
                processDefinition.getReleaseState(),
                0);
        if (Boolean.TRUE.equals(result.getFailed())){
            throw new ApiException(400, result.getMsg());
        }
        return result;

    }

    /**
     * 获取流程定义详情
     *
     * @param processDefinitionCode
     * @return
     * @throws ApiException
     */
    public ProcessDefinitionVO processDefinitionDetail(Long processDefinitionCode) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().queryProcessDefinitionByCodeUsingGET(
                processDefinitionCode,
                dolphinTaskDefinitionPropertiesBean.getProjectCode()
        );
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        Map<String, Object> data = (Map<String, Object>) result.getData();
        Map<String, Object> processDefinition = (Map<String, Object>) data.get("processDefinition");
        return BeanMapUtils.changeMapToBean(processDefinition, new ProcessDefinitionVO());
    }

    public Result dolphinProcessRelease(Long processDefinitionCode, ProcessDefinition.ReleaseStateEnum releaseState) {
        Result result = null;
        try {
            result = dolphinschedulerManager.defaultApi().
                    releaseProcessDefinitionUsingPOST(processDefinitionCode,
                    dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                    releaseState);
            if (Boolean.TRUE.equals(result.getFailed())){
                throw new ApiException(400, result.getMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Result runing(Long processDefinitionCode, Long environmentCode){
        Result result;
        try {
         result = dolphinschedulerManager.defaultApi().startProcessInstanceUsingPOST(
                ProcessInstance.FailureStrategyEnum.CONTINUE,
                processDefinitionCode,
                ProcessInstance.ProcessInstancePriorityEnum.MEDIUM,
                dolphinTaskDefinitionPropertiesBean.getProjectCode(),
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
        if (Boolean.TRUE.equals(result.getFailed())){
            throw new ApiException(400, result.getMsg());
        }
    } catch (Exception e) {
        e.printStackTrace();
        throw new BizException("dolphin runing error【{}】," + e.getMessage());
    }
        return result;
    }


    public Result getProcessInstance(Long processDefinitionCode) {
        Result result;
        try {
            result = dolphinschedulerManager.defaultApi().queryProcessInstanceListUsingGET(
                    1,
                    1,
                    dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                    null,
                    null,
                    null,
                    processDefinitionCode,
                    null,
                    null,
                    null
            );
            if (Boolean.TRUE.equals(result.getFailed())) {
                throw new ApiException(400, result.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("dolphin error【{}】," + e.getMessage());
        }

        return result;
    }

    /**
     * 创建定时
     * @param processDefinitionCode
     * @param effectiveTimeStart
     * @param effectiveTimeEnt
     * @param cron
     */
    public void createSchedule(Long processDefinitionCode, Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {

        try {
            Result  result = dolphinschedulerManager.defaultApi().createScheduleUsingPOST(
                            processDefinitionCode,
                     dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                    dolphinTaskDefinitionPropertiesBean.getEnvironmentCode(),
                    FailureStrategy.getVal(dolphinTaskDefinitionPropertiesBean.getFailureStrategy()).getCode(),
                    ProcessInstancePriority.getVal(dolphinTaskDefinitionPropertiesBean.getTaskPriority()).getCode(),
                            // 拼接定时时间
                    CronUtil.schedule(effectiveTimeStart, effectiveTimeEnt, cron),
                    dolphinTaskDefinitionPropertiesBean.getWarningGroupId(),
                    "NONE",//发送策略
                    "default"//Worker分组 默认 "default"
            );
            if (Boolean.TRUE.equals(result.getFailed())) {
                throw new ApiException(400, result.getMsg());
            }

        } catch (ApiException e) {
            e.printStackTrace();
            log.error("创建定时接口入参：【{}】,【{}】,【{}】,【{}】",
                    processDefinitionCode, effectiveTimeStart, effectiveTimeEnt, cron);
            throw new BizException("dolphin error【{}】," + e.getMessage());
        }
    }

    /**
     * 修改定时
     * @param scheduleId
     * @param effectiveTimeStart
     * @param effectiveTimeEnt
     * @param cron
     */
    public void updateSchedule(Integer scheduleId, Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {

        try {
            Result result = dolphinschedulerManager.defaultApi().updateScheduleUsingPUT(
                            scheduleId,
                    dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                    dolphinTaskDefinitionPropertiesBean.getEnvironmentCode(),
                    FailureStrategy.getVal(dolphinTaskDefinitionPropertiesBean.getFailureStrategy()).getCode(),
                    ProcessInstancePriority.getVal(dolphinTaskDefinitionPropertiesBean.getTaskPriority()).getCode(),
                    CronUtil.schedule(effectiveTimeStart, effectiveTimeEnt, cron),
                    dolphinTaskDefinitionPropertiesBean.getWarningGroupId(),
                    "NONE",//发送策略
                    "default"//Worker分组 默认 "default"
            );
            if (Boolean.TRUE.equals(result.getFailed())) {
                throw new ApiException(400, result.getMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("修改定时接口入参：【{}】,【{}】,【{}】,【{}】",
                    scheduleId, effectiveTimeStart, effectiveTimeEnt, cron);
            throw new BizException("dolphin error【{}】," + e.getMessage());
        }
    }

    /**
     * 查找定时
     * @param processDefinitionCode
     * @return
     */
    public Result searchSchedule(Long processDefinitionCode) {
        Result result = null;
        try {
            result = dolphinschedulerManager.defaultApi().queryScheduleListPagingUsingGET(
                    processDefinitionCode,
                    dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                    1,
                    100,
                    null);
            if (Boolean.TRUE.equals(result.getFailed())) {
                throw new ApiException(400, result.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询定时接口入参：【{}】", processDefinitionCode);
            throw new BizException("dolphin error【{}】," + e.getMessage());
        }
        return result;
    }

    /**
     * 定时任务上线
     * @param scheduleId
     * @return
     */
    public void onlineSchedule(Integer scheduleId){
        try {
            Result result = dolphinschedulerManager.defaultApi().onlineUsingPOST(scheduleId,
                    dolphinTaskDefinitionPropertiesBean.getProjectCode());
            if (Boolean.TRUE.equals(result.getFailed())) {
                throw new ApiException(400, result.getMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时任务下线
     * @param scheduleId
     */
    public void offlineSchedule(Integer scheduleId){
        try {
            Result result = dolphinschedulerManager.defaultApi().offlineUsingPOST(scheduleId,
                    dolphinTaskDefinitionPropertiesBean.getProjectCode());
            if (Boolean.TRUE.equals(result.getFailed())) {
                throw new ApiException(400, result.getMsg());
            }
        } catch (ApiException e) {
            log.error("定时任务下线失败：【{}】", scheduleId);
            e.printStackTrace();
        }
    }

    /**
     * 流程定义删除
     * @param processDefinitionCodes
     */
    public void deleteProcessDefinition(String processDefinitionCodes){
        try {
            Result result = dolphinschedulerManager.defaultApi()
                    .batchDeleteProcessDefinitionByCodesUsingPOST(processDefinitionCodes,
                            dolphinTaskDefinitionPropertiesBean.getProjectCode());
            if (Boolean.TRUE.equals(result.getFailed())) {
                throw new ApiException(400, result.getMsg());
            }
        }catch (Exception e){
            log.error("流程定义删除失败：【{}】", processDefinitionCodes);
            e.printStackTrace();
        }

    }

    /**
     * 删除定时
     * @param scheduleId
     */
    public void deleteSchedule(Integer scheduleId){
        try {
            Result result = dolphinschedulerManager.defaultApi().deleteScheduleByIdUsingDELETE(
                    scheduleId,
                    dolphinTaskDefinitionPropertiesBean.getProjectCode(),
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
                            null
            );

            if (Boolean.TRUE.equals(result.getFailed())) {
                throw new ApiException(400, result.getMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
