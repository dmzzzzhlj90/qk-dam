package com.qk.dm.datacollect.dolphin;

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

    private long getTaskCode() {
        long taskCode = 0L;
        try {
            taskCode = CodeGenerateUtils.getInstance().genCode();
        } catch (CodeGenerateUtils.CodeGenerateException e) {
            e.printStackTrace();
        }
        return taskCode;
    }

    public Result createProcessDefinition(Long projectId,
                                          String name,
                                          String url,
                                          Object httpParams,
                                          String httpMethod,
                                          String description) throws ApiException {
        return createProcessDefinition(projectId, name, getTaskCode(), url, httpParams, httpMethod, description);
    }



    private Result createProcessDefinition(Long projectId,
                                           String name,
                                           long taskCode,
                                           String url,
                                           Object httpParams,
                                           String httpMethod
            , String description) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode, name, url, httpParams, httpMethod, dolphinTaskDefinitionPropertiesBean);
        Result result = dolphinschedulerManager.defaultApi().createProcessDefinitionUsingPOST(
                processDefinition.getLocations(),
                processDefinition.getName(),
                projectId,
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
                                          Long projectId,
                                          String name,
                                          String url,
                                          Object httpParams,
                                          String httpMethod,
                                          String description,
                                          DolphinTaskDefinitionPropertiesBean taskParam) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                getTaskCode(), name, url, httpParams, httpMethod, taskParam, dolphinTaskDefinitionPropertiesBean);
        Result result = dolphinschedulerManager.defaultApi().updateProcessDefinitionUsingPUT(
                processDefinitionCode,
                processDefinition.getLocations(),
                processDefinition.getName(),
                projectId,
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
