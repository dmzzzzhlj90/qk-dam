package com.dolphinscheduler.shell.client;

import com.dolphinscheduler.shell.DolphinTaskDefinitionPropertiesBean;


/**
 * @author zhudaoming
 */
public class DolphinProcessDefinition {
    private String locations;
    private String name;
    private String taskDefinitionJson;
    private String taskRelationJson;
    private String tenantCode;
    private final String releaseState = "OFFLINE";

    public DolphinProcessDefinition(long taskCode,
                                    String name,
                                    Object params,
                                    Object resourceList,
                                    String rawScript,
                                    DolphinTaskDefinitionPropertiesBean definitionPropertiesBean) {
        this.locations = "[{\"taskCode\":" + taskCode + ",\"x\":63,\"y\":97}]";
        this.name = name;
        this.taskDefinitionJson = definitionPropertiesBean.taskDefinitionJson(taskCode, params, resourceList, rawScript);
        this.taskRelationJson = "[{\"name\":\"\",\"postTaskCode\":" + taskCode + "}]";
        this.tenantCode = definitionPropertiesBean.getTenantCode();
    }

    public DolphinProcessDefinition(long taskCode,
                                    String name,
                                    Object params,
                                    Object resourceList,
                                    String rawScript,
                                    DolphinTaskDefinitionPropertiesBean taskParam,
                                    DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean) {
        this.locations = "[{\"taskCode\":" + taskCode + ",\"x\":63,\"y\":97}]";
        this.name = name;
        this.taskDefinitionJson = dolphinTaskDefinitionPropertiesBean.taskDefinitionJson(taskCode, params, resourceList, rawScript, taskParam);
        this.taskRelationJson = "[{\"name\":\"\",\"postTaskCode\":" + taskCode + "}]";
        this.tenantCode = dolphinTaskDefinitionPropertiesBean.getTenantCode();
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskDefinitionJson() {
        return taskDefinitionJson;
    }

    public void setTaskDefinitionJson(String taskDefinitionJson) {
        this.taskDefinitionJson = taskDefinitionJson;
    }

    public String getTaskRelationJson() {
        return taskRelationJson;
    }

    public void setTaskRelationJson(String taskRelationJson) {
        this.taskRelationJson = taskRelationJson;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getReleaseState() {
        return releaseState;
    }
}
