package com.qk.dm.datacollect.dolphin.client;

import lombok.Data;


/**
 * @author zhudaoming
 */
@Data
public class DolphinProcessDefinition {
    private String locations;
    private String name;
    private String taskDefinitionJson;
    private String taskRelationJson;
    private String tenantCode;
    private final String releaseState = "OFFLINE";

    public DolphinProcessDefinition(long taskCode,
                                    String name,
                                    String url,
                                    Object httpParams,
                                    String httpMethod,
                                    DolphinTaskDefinitionPropertiesBean definitionPropertiesBean) {
        this.locations = "[{\"taskCode\":"+taskCode+",\"x\":63,\"y\":97}]";
        this.name = name;
        this.taskDefinitionJson = definitionPropertiesBean.taskDefinitionJson(taskCode,url,httpParams,httpMethod);
        this.taskRelationJson = "[{\"name\":\"\",\"postTaskCode\":"+taskCode+"}]";
        this.tenantCode = definitionPropertiesBean.getTenantCode();
    }

    public DolphinProcessDefinition(long taskCode,
                                    String name,
                                    String url,
                                    Object httpParams,
                                    String httpMethod,
                                    DolphinTaskDefinitionPropertiesBean taskParam,
                                    DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean) {
        this.locations = "[{\"taskCode\":"+taskCode+",\"x\":63,\"y\":97}]";
        this.name = name;
        this.taskDefinitionJson = dolphinTaskDefinitionPropertiesBean.taskDefinitionJson(taskCode,url,httpParams,httpMethod,taskParam);
        this.taskRelationJson = "[{\"name\":\"\",\"postTaskCode\":"+taskCode+"}]";
        this.tenantCode = dolphinTaskDefinitionPropertiesBean.getTenantCode();
    }
}
