package com.qk.dm.dataingestion.datax;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.CodeGenerateUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.ProcessInstance;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataingestion.model.DolphinProcessDefinition;
import com.qk.dm.dataingestion.model.DolphinTaskDefinitionPropertiesBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public Result updateProcessDefinition(String name,
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
                taskCode,
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

    public Result dolphinProcessRelease(Long code, ProcessDefinition.ReleaseStateEnum releaseState) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().releaseProcessDefinitionUsingPOST(code, dolphinTaskDefinitionPropertiesBean.getProjectCode(), releaseState);
        if (Boolean.TRUE.equals(result.getFailed())){
            throw new ApiException(400, result.getMsg());
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
}
