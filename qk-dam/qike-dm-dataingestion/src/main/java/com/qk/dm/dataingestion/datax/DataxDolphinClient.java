package com.qk.dm.dataingestion.datax;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.qk.dam.commons.util.CodeGenerateUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.ProcessInstance;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataingestion.model.DolphinProcessDefinition;
import com.qk.dm.dataingestion.model.DolphinTaskDefinitionPropertiesBean;
import org.springframework.stereotype.Service;

@Service
public class DataxDolphinClient {
    private final DolphinschedulerManager dolphinschedulerManager;

    private final DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean;

    public DataxDolphinClient(DolphinschedulerManager dolphinschedulerManager, DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean) {
        this.dolphinschedulerManager = dolphinschedulerManager;
        this.dolphinTaskDefinitionPropertiesBean = dolphinTaskDefinitionPropertiesBean;
    }

    public Result createProcessDefinition(Long projectId,
                                          String name,
                                          String dataxJson) throws ApiException {
        long taskCode =0L;
        try {
            taskCode = CodeGenerateUtils.getInstance().genCode();
        } catch (CodeGenerateUtils.CodeGenerateException e) {
            e.printStackTrace();
        }
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(taskCode, name, dataxJson, dolphinTaskDefinitionPropertiesBean);
        Result result = dolphinschedulerManager.defaultApi().createProcessDefinitionUsingPOST(processDefinition.getLocations(),
                processDefinition.getName(),
                projectId,
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

    public Result updateProcessDefinition(Long projectId,
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
                taskCode,
                processDefinition.getLocations(),
                processDefinition.getName(),
                projectId,
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

    public Result dolphinProcessRelease(Long code, Long projectCode, ProcessDefinition.ReleaseStateEnum releaseState) throws ApiException {
        Result result = dolphinschedulerManager.defaultApi().releaseProcessDefinitionUsingPOST(code, projectCode, releaseState);
        if (Boolean.TRUE.equals(result.getFailed())){
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }
    public Result runing(Long projectCode, Long processDefinitionCode, Long environmentCode) throws ApiException {
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
        if (Boolean.TRUE.equals(result.getFailed())){
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }
}
