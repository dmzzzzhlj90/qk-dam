package com.qk.dm.dataingestion.rest;

import com.google.gson.Gson;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataingestion.datax.DataxClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 创建datax dolphin节点
 * @author zhudaoming
 */
@RestController
@RequestMapping("/datax")
public class DataxController {
    final DataxClient dataxClient;

    public DataxController(DataxClient dataxClient) {
        this.dataxClient = dataxClient;
    }

    /**
     * 创建datax 任务定义
     * @param projectId 项目id
     * @param dataxJson datax配置脚本
     * @return  DefaultCommonResult
     * @throws ApiException api异常
     */
    @PostMapping("/process-Definition/{projectId}")
    public DefaultCommonResult<Result> createProcessDefinition(@PathVariable final Long projectId,
                                                               @RequestBody final String dataxJson) throws ApiException {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dataxClient.createProcessDefinition(projectId,dataxJson));
    }

    /**
     * 更新datax 任务定义
     * @param projectId 项目id
     * @param dataxJson datax配置脚本
     * @param taskCode 任务code
     * @param optionJson 环境配置
     * @return DefaultCommonResult
     * @throws ApiException api异常
     */
    @PutMapping("/process-Definition/{projectId}")
    public DefaultCommonResult<Result> putProcessDefinition(@PathVariable final Long projectId,
                                                            final long taskCode,
                                                            @RequestParam(defaultValue = "{}") final String optionJson,
                                                            @RequestBody final String dataxJson
                                                               ) throws ApiException {
        Map<String,Object> optionMap = new Gson().fromJson(optionJson, Map.class);
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dataxClient.updateProcessDefinition(projectId,taskCode,dataxJson));
    }
}
