package com.qk.dm.dolphin.common.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessInstance;
import com.qk.datacenter.model.Result;
import com.qk.dm.dolphin.common.client.DolphinApiClient;
import com.qk.dm.dolphin.common.dto.*;
import com.qk.dm.dolphin.common.service.ProcessInstanceService;
import com.qk.dm.dolphin.common.utils.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2022/4/21 15:15
 * @since 1.0.0
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessInstanceServiceImpl.class);

    private final DolphinApiClient dolphinApiClient;

    public ProcessInstanceServiceImpl(DolphinApiClient dolphinApiClient) {
        this.dolphinApiClient = dolphinApiClient;
    }

    @Override
    public void execute(Integer processInstanceId, String executeType) {
        try {
            dolphinApiClient.instance_execute(processInstanceId, ProcessInstance.CmdTypeIfComplementEnum.fromValue(executeType));
        } catch (ApiException a) {
            LOG.error("Dolphin 流程实例[{}] 操作失败，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("dolphin instance execute error ：" + a.getMessage());
        }
    }

    @Override
    public ProcessInstanceResultDTO search(ProcessInstanceSearchDTO instanceSearchDTO) {
        try {
            Result result = dolphinApiClient.instance_search(instanceSearchDTO);
            return ConstantUtil.changeObjectToClass(result.getData(), ProcessInstanceResultDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 查询流程实例列表失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin instance search error：" + a.getMessage());
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 查询流程实例列表成功，解释时失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin instance toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 查询流程实例列表报错，原因为[{}]", a.getMessage());
            throw new BizException("dolphin instance exception error");
        }
    }

    @Override
    public ProcessInstanceDTO detail(Integer processInstanceId) {
        try {
            Result result = dolphinApiClient.instance_detail(processInstanceId);
            return ConstantUtil.changeObjectToClass(result.getData(), ProcessInstanceDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 流程实例[{}] 查询详情失败，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("dolphin instance detail error：" + a.getMessage());
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 流程实例[{}] 查询详情成功，解析时失败，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("dolphin instance toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 流程实例[{}] 查询详情报错，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("dolphin instance exception error");
        }
    }

    @Override
    public TaskInstanceListResultDTO taskByProcessId(Integer processInstanceId) {
        try {
            Result result = dolphinApiClient.taskByProcessId(processInstanceId);
            return ConstantUtil.changeObjectToClass(result.getData(), TaskInstanceListResultDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 任务实例[{}] 根据流程实例id查询失败，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("dolphin task search error :" + a.getMessage());
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 任务实例[{}] 根据流程实例id查询成功，解析时失败，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("dolphin task toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 任务实例[{}] 根据流程实例id查询报错，原因为[{}]", processInstanceId, a.getMessage());
            throw new BizException("dolphin task exception error");
        }
    }

    @Override
    public TaskInstanceResultDTO taskPageByProcessId(TaskInstanceSearchDTO instanceSearchDTO) {
        try {
            Result result = dolphinApiClient.taskPageByProcessId(instanceSearchDTO);
            return ConstantUtil.changeObjectToClass(result.getData(), TaskInstanceResultDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 任务实例[{}] 根据流程实例id查询失败，原因为[{}]", instanceSearchDTO.getProcessInstanceId(), a.getMessage());
            throw new BizException("dolphin task search error :" + a.getMessage());
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 任务实例[{}] 根据流程实例id查询成功，解析时失败，原因为[{}]", instanceSearchDTO.getProcessInstanceId(), a.getMessage());
            throw new BizException("dolphin task toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 任务实例[{}] 根据流程实例id查询报错，原因为[{}]", instanceSearchDTO.getProcessInstanceId(), a.getMessage());
            throw new BizException("dolphin task exception error");
        }
    }

    @Override
    public String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum) {
        try {
            return dolphinApiClient.taskLog(taskInstanceId, limit, skipLineNum).getData();
        } catch (ApiException a) {
            LOG.error("Dolphin 任务实例[{}] 查询日志失败，原因为[{}]", taskInstanceId, a.getMessage());
            throw new BizException("dolphin task log error :" + a.getMessage());
        }
    }
}
