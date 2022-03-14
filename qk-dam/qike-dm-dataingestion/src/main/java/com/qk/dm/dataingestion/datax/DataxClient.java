package com.qk.dm.dataingestion.datax;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.qk.dam.commons.util.CodeGenerateUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataingestion.model.DolphinProcessDefinition;
import com.qk.dm.dataingestion.model.DolphinTaskDefinitionPropertiesBean;
import org.springframework.stereotype.Service;

@Service
public class DataxClient {
    private final DolphinschedulerManager dolphinschedulerManager;

    private final DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean;

    public DataxClient(DolphinschedulerManager dolphinschedulerManager, DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean) {
        this.dolphinschedulerManager = dolphinschedulerManager;
        this.dolphinTaskDefinitionPropertiesBean = dolphinTaskDefinitionPropertiesBean;
    }

    public Result createProcessDefinition(Long projectId,String dataxJson) throws ApiException {
        long taskCode =0L;
        try {
            taskCode = CodeGenerateUtils.getInstance().genCode();
        } catch (CodeGenerateUtils.CodeGenerateException e) {
            e.printStackTrace();
        }
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(taskCode, "dataxceshi", dataxJson, dolphinTaskDefinitionPropertiesBean);
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

    public Result updateProcessDefinition(Long projectId,long taskCode,String dataxJson) throws ApiException {
        DolphinProcessDefinition processDefinition = new DolphinProcessDefinition(taskCode, "dataxceshi", dataxJson, dolphinTaskDefinitionPropertiesBean);
        return dolphinschedulerManager.defaultApi().updateProcessDefinitionUsingPUT(
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

    }

}
