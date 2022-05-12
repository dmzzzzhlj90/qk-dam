package com.qk.dm.dolphin.common.manager;

import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.model.ProcessInstance;
import com.qk.datacenter.model.Result;
import com.qk.dm.dolphin.common.client.DolphinApiClient;
import com.qk.dm.dolphin.common.dto.*;
import com.qk.dm.dolphin.common.utils.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 实例管理
 * @author shenpj
 * @date 2022/4/21 15:15
 * @since 1.0.0
 */
@Service
public class ProcessInstanceManager {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessInstanceManager.class);

    private final DolphinApiClient dolphinApiClient;

    public ProcessInstanceManager(DolphinApiClient dolphinApiClient) {
        this.dolphinApiClient = dolphinApiClient;
    }

    /**
     * 实例操作
     * @param processInstanceId 流程实例Id
     * @param executeType 操作状态
     */
    public void execute(Integer processInstanceId, String executeType) {
        try {
            dolphinApiClient.instance_execute(processInstanceId, ProcessInstance.CmdTypeIfComplementEnum.fromValue(executeType));
        } catch (Exception a) {
            LOG.error("Dolphin 流程实例[{}] 操作失败，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("操作失败");
        }
    }

    /**
     * 流程实例分页列表
     * @param instanceSearchDTO
     * @return
     */
    public ProcessInstanceResultDTO search(ProcessInstanceSearchDTO instanceSearchDTO) {
        try {
            Result result = dolphinApiClient.instance_search(instanceSearchDTO);
            return ConstantUtil.changeObjectToClass(result.getData(), ProcessInstanceResultDTO.class);
        } catch (Exception a) {
            LOG.error("Dolphin 查询流程实例列表报错，原因为[{}]", a.getMessage());
            throw new BizException("查询失败");
        }
    }

    /**
     * 流程实例详情
     * @param processInstanceId 流程实例Id
     * @return
     */
    public ProcessInstanceDTO detail(Integer processInstanceId) {
        try {
            Result result = dolphinApiClient.instance_detail(processInstanceId);
            return ConstantUtil.changeObjectToClass(result.getData(), ProcessInstanceDTO.class);
        } catch (Exception a) {
            LOG.error("Dolphin 流程实例[{}] 查询详情报错，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("查询失败");
        }
    }

    /**
     * 任务实例列表
     * @param processInstanceId 流程实例Id
     * @return
     */
    public TaskInstanceListResultDTO taskByProcessId(Integer processInstanceId) {
        try {
            Result result = dolphinApiClient.taskByProcessId(processInstanceId);
            return ConstantUtil.changeObjectToClass(result.getData(), TaskInstanceListResultDTO.class);
        } catch (Exception a) {
            LOG.error("Dolphin 任务实例[{}] 根据流程实例id查询报错，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("查询失败");
        }
    }

    /**
     * 任务实例分页列表
     * @param instanceSearchDTO
     * @return
     */
    public TaskInstanceResultDTO taskPageByProcessId(TaskInstanceSearchDTO instanceSearchDTO) {
        try {
            Result result = dolphinApiClient.taskPageByProcessId(instanceSearchDTO);
            return ConstantUtil.changeObjectToClass(result.getData(), TaskInstanceResultDTO.class);
        } catch (Exception a) {
            LOG.error("Dolphin 任务实例[{}] 根据流程实例id查询报错，原因为[{}]", instanceSearchDTO.getProcessInstanceId(), a.getMessage());
            throw new BizException("查询失败");
        }
    }

    /**
     * 任务实例日志
     * @param taskInstanceId 任务实例id
     * @param limit 页码
     * @param skipLineNum 条数
     * @return
     */
    public String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum) {
        try {
            return dolphinApiClient.taskLog(taskInstanceId, limit, skipLineNum).getData();
        } catch (Exception a) {
            LOG.error("Dolphin 任务实例[{}] 查询日志失败，原因为[{}]", taskInstanceId, a.getMessage());
            throw new BizException("查询失败");
        }
    }
}
