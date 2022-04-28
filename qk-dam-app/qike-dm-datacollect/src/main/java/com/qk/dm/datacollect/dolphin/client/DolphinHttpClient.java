package com.qk.dm.datacollect.dolphin.client;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.qk.dam.commons.util.CodeGenerateUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import org.springframework.stereotype.Service;

@Service
public class DolphinHttpClient {
    private final DolphinschedulerManager dolphinschedulerManager;
    private final DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean;

    public DolphinHttpClient(DolphinschedulerManager dolphinschedulerManager, DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean) {
        this.dolphinschedulerManager = dolphinschedulerManager;
        this.dolphinTaskDefinitionPropertiesBean = dolphinTaskDefinitionPropertiesBean;
    }

    public Result createProcessDefinition(String name, Object httpParams, String description) throws ApiException {
        long taskCode = 0L;
        try {
            taskCode = CodeGenerateUtils.getInstance().genCode();
        } catch (CodeGenerateUtils.CodeGenerateException e) {
            e.printStackTrace();
        }
        return createProcessDefinition(name, taskCode, httpParams, description);
    }

    private Result createProcessDefinition(String name,
                                           long taskCode,
                                           Object httpParams,
                                           String description) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode, name,  httpParams,  dolphinTaskDefinitionPropertiesBean);
        Result result = dolphinschedulerManager.defaultApi().createProcessDefinitionUsingPOST(
                processDefinition.getLocations(),
                processDefinition.getName(),
                dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                processDefinition.getTaskDefinitionJson(),
                processDefinition.getTaskRelationJson(),
                processDefinition.getTenantCode(),
                description,
                "[]",
                0);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }

    public Result updateProcessDefinition(Long processDefinitionCode,
                                          long taskCode,
                                          String name,
                                          Object httpParams,
                                          String description,
                                          DolphinTaskDefinitionPropertiesBean taskParam) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode, name,  httpParams,  taskParam, dolphinTaskDefinitionPropertiesBean);
        Result result = dolphinschedulerManager.defaultApi().updateProcessDefinitionUsingPUT(
                processDefinitionCode,
                processDefinition.getLocations(),
                processDefinition.getName(),
                dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                processDefinition.getTaskDefinitionJson(),
                processDefinition.getTaskRelationJson(),
                processDefinition.getTenantCode(),
                description,
                "[]",
                processDefinition.getReleaseState(),
                0);
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new ApiException(400, result.getMsg());
        }
        return result;
    }
}
