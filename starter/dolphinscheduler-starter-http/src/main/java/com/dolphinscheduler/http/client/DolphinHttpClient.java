package com.dolphinscheduler.http.client;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.dolphinscheduler.http.DolphinTaskDefinitionPropertiesBean;
import com.qk.dam.commons.util.CodeGenerateUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;

public class DolphinHttpClient {
    private final DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean;
    private final DolphinschedulerManager dolphinschedulerManager;

    public DolphinHttpClient(DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean, DolphinschedulerManager dolphinschedulerManager) {
        this.dolphinTaskDefinitionPropertiesBean = dolphinTaskDefinitionPropertiesBean;
        this.dolphinschedulerManager = dolphinschedulerManager;
    }

    public Result createProcessDefinition(String name,
                                          Object httpParams,
                                          String description) throws ApiException {
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
                taskCode, name, httpParams, dolphinTaskDefinitionPropertiesBean);
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
                                          String description) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode, name, httpParams, dolphinTaskDefinitionPropertiesBean);
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

    public Result updateProcessDefinition(Long processDefinitionCode,
                                          long taskCode,
                                          String name,
                                          Object httpParams,
                                          String description,
                                          DolphinTaskDefinitionPropertiesBean taskParam) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode, name, httpParams, taskParam, dolphinTaskDefinitionPropertiesBean);
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
