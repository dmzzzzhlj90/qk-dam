package com.dolphinscheduler.shell.client;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.dolphinscheduler.shell.DolphinTaskDefinitionPropertiesBean;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.CodeGenerateUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;

public class DolphinShellClient {
    private final DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean;
    private final DolphinschedulerManager dolphinschedulerManager;

    public DolphinShellClient(DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean, DolphinschedulerManager dolphinschedulerManager) {
        this.dolphinTaskDefinitionPropertiesBean = dolphinTaskDefinitionPropertiesBean;
        this.dolphinschedulerManager = dolphinschedulerManager;
    }

    public void createProcessDefinition(String name,
                                        Object params,
                                        Object resourceList,
                                        String rawScript,
                                        String description) {
        long taskCode = 0L;
        try {
            taskCode = CodeGenerateUtils.getInstance().genCode();
        } catch (CodeGenerateUtils.CodeGenerateException e) {
            e.printStackTrace();
        }
        createProcessDefinition(name, taskCode, params, resourceList, rawScript, description);
    }

    private Result createProcessDefinition(String name,
                                           long taskCode,
                                           Object params,
                                           Object resourceList,
                                           String rawScript,
                                           String description) {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode, name, params, resourceList, rawScript, dolphinTaskDefinitionPropertiesBean);
        Result result = null;
        try {
            result = dolphinschedulerManager.defaultApi().createProcessDefinitionUsingPOST(
                    processDefinition.getLocations(),
                    processDefinition.getName(),
                    dolphinTaskDefinitionPropertiesBean.getProjectCode(),
                    processDefinition.getTaskDefinitionJson(),
                    processDefinition.getTaskRelationJson(),
                    processDefinition.getTenantCode(),
                    description,
                    "[]",
                    0);
        } catch (ApiException e) {
            throw new BizException(e.getMessage());
        }
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new BizException(result.getMsg());
        }
        return result;
    }

    public void updateProcessDefinition(Long processDefinitionCode,
                                        long taskCode,
                                        String name,
                                        Object params,
                                        Object resourceList,
                                        String rawScript,
                                        String description) {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode, name, params, resourceList, rawScript, dolphinTaskDefinitionPropertiesBean);
        Result result = null;
        try {
            result = dolphinschedulerManager.defaultApi().updateProcessDefinitionUsingPUT(
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
        } catch (ApiException e) {
            throw new BizException(e.getMessage());
        }
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new BizException(result.getMsg());
        }
    }

    public void updateProcessDefinition(Long processDefinitionCode,
                                        long taskCode,
                                        String name,
                                        Object params,
                                        Object resourceList,
                                        String rawScript,
                                        String description,
                                        DolphinTaskDefinitionPropertiesBean taskParam) {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(
                taskCode, name, params, resourceList, rawScript, taskParam, dolphinTaskDefinitionPropertiesBean);
        Result result = null;
        try {
            result = dolphinschedulerManager.defaultApi().updateProcessDefinitionUsingPUT(
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
        } catch (ApiException e) {
            throw new BizException(e.getMessage());
        }
        if (Boolean.TRUE.equals(result.getFailed())) {
            throw new BizException(result.getMsg());
        }
    }
}
