package com.qk.dm.dataingestion.model;

import lombok.Data;


/**
 * @author zhudaoming
 */
@Data
public class DolphinProcessDefinition {
    public DolphinProcessDefinition(long taskCode,
                                    String name,
                                    String environmentCode,
                                    DolphinTaskDefinitionPropertiesBean definitionPropertiesBean) {
        this.locations = "[{\"taskCode\":"+taskCode+",\"x\":63,\"y\":97}]";
        this.name = name;
        this.taskDefinitionJson = definitionPropertiesBean.taskDefinitionJson(taskCode,environmentCode);
        this.taskRelationJson = "[{\"name\":\"\",\"postTaskCode\":"+taskCode+"}]";
        this.tenantCode = definitionPropertiesBean.getTenantCode();
    }
    public DolphinProcessDefinition(long taskCode,
                                    String name,
                                    String dataxJson,
                                    String environmentCode,
                                    DolphinTaskDefinitionPropertiesBean definitionPropertiesBean) {
        this.locations = "[{\"taskCode\":"+taskCode+",\"x\":63,\"y\":97}]";
        this.name = name;
        this.taskDefinitionJson = definitionPropertiesBean.taskDefinitionJson(taskCode,dataxJson,environmentCode);
        this.taskRelationJson = "[{\"name\":\"\",\"postTaskCode\":"+taskCode+"}]";
        this.tenantCode = definitionPropertiesBean.getTenantCode();
    }

    private String locations;
    private String name;
    private String taskDefinitionJson;
    private String taskRelationJson;
    private final  String tenantCode;
    private final String releaseState = "OFFLINE";
}
