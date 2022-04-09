package com.qk.dm.dataingestion.rest;

import com.google.gson.Gson;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataingestion.datax.DataxDolphinClient;
import com.qk.dm.dataingestion.model.DolphinTaskDefinitionPropertiesBean;
import org.springframework.web.bind.annotation.*;


/**
 * datax 任务定义空值rest接口
 *
 * 创建datax dolphin节点
 * @author zhudaoming
 */
@RestController
@RequestMapping("/datax")
public class DataxTaskDefineController {
    final DataxDolphinClient dataxDolphinClient;

    public DataxTaskDefineController(DataxDolphinClient dataxDolphinClient) {
        this.dataxDolphinClient = dataxDolphinClient;
    }

    /**
     * 创建datax 任务定义
     * @param projectId 项目id
     * @param dataxJson datax配置脚本
     * @return  DefaultCommonResult
     * @throws ApiException api异常
     */
    @PostMapping("/projects/{projectId}")
    public DefaultCommonResult<Result> createProcessDefinition(@PathVariable final Long projectId,
                                                               final String dataxName,
                                                               @RequestBody final String dataxJson) throws ApiException {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dataxDolphinClient.createProcessDefinition(projectId,dataxName,dataxJson));
    }

    /**
     * 更新datax 任务定义
     * @param projectId 项目id
     * @param dataxJson datax配置脚本
     * @param taskCode 任务code
     * @param taskParam 环境配置
     * @return DefaultCommonResult
     * @throws ApiException api异常
     */
    @PutMapping("/projects/{projectId}/process/{taskCode}")
    public DefaultCommonResult<Result> putProcessDefinition(@PathVariable final Long projectId,
                                                            @PathVariable final long taskCode,
                                                            final String dataxName,
                                                            @RequestParam(defaultValue = "{}") final String taskParam,
                                                            @RequestBody final String dataxJson
                                                               ) throws ApiException {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dataxDolphinClient.updateProcessDefinition(projectId,dataxName,taskCode,dataxJson,new Gson().fromJson(taskParam, DolphinTaskDefinitionPropertiesBean.class)));
    }


    /**
     * 上线
     * @param projectId  项目id
     * @param taskCode 任务code
     * @return DefaultCommonResult
     * @throws ApiException api异常
     */
    @PostMapping("/process/online")
    public DefaultCommonResult<Result> processReleaseOnline(final Long projectId,
                                                      final long taskCode) throws ApiException {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dataxDolphinClient.dolphinProcessRelease(taskCode,projectId,ProcessDefinition.ReleaseStateEnum.ONLINE));
    }

    /**
     * 下线
     * @param projectId  项目id
     * @param taskCode 任务code
     * @return DefaultCommonResult
     * @throws ApiException api异常
     */
    @PostMapping("/process/offline")
    public DefaultCommonResult<Result> processRelease(final Long projectId,
                                                      final long taskCode) throws ApiException {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dataxDolphinClient.dolphinProcessRelease(taskCode,projectId,ProcessDefinition.ReleaseStateEnum.OFFLINE));
    }

    /**
     * 运行
     * @return DefaultCommonResult
     * @throws ApiException api异常
     */
    @PostMapping("/executors/start")
    public DefaultCommonResult<Result> processRunning( final long projectId,
                                                       final long taskCode,
                                                      Long environmentCode) throws ApiException {

        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dataxDolphinClient.runing(projectId,taskCode,environmentCode));
    }

}
