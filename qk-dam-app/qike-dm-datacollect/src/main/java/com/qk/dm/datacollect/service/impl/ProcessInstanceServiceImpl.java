package com.qk.dm.datacollect.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessInstance;
import com.qk.datacenter.model.Result;
import com.qk.dm.datacollect.dolphin.DolphinApiClient;
import com.qk.dm.datacollect.dolphin.ProcessInstanceSearchDTO;
import com.qk.dm.datacollect.service.ProcessInstanceService;
import com.qk.dm.datacollect.util.DctConstant;
import com.qk.dm.datacollect.dto.ProcessInstanceDTO;
import com.qk.dm.datacollect.dto.ProcessInstanceResultDTO;
import com.qk.dm.datacollect.dto.TaskInstanceResultDTO;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2022/4/21 15:15
 * @since 1.0.0
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    private final DolphinApiClient dolphinApiClient;

    public ProcessInstanceServiceImpl(DolphinApiClient dolphinApiClient) {
        this.dolphinApiClient = dolphinApiClient;
    }

    @Override
    public void execute(Integer processInstanceId, Long projectCode, ProcessInstance.CmdTypeIfComplementEnum executeType) {
        try {
            dolphinApiClient.instance_execute(processInstanceId, projectCode, executeType);
        } catch (ApiException a) {
            throw new BizException("dolphin instance execute error :" + a.getMessage());
        }
    }

    @Override
    public ProcessInstanceResultDTO search(Long projectCode, ProcessInstanceSearchDTO instanceSearchDTO) {
        try {
            Result result = dolphinApiClient.instance_search(projectCode, instanceSearchDTO);
            return DctConstant.changeObjectToClass(result.getData(), ProcessInstanceResultDTO.class);
        } catch (ApiException a) {
            throw new BizException("dolphin instance search error :" + a.getMessage());
        }catch (JsonProcessingException a) {
            throw new BizException("dolphin instance toClass error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin instance exception error :" + a.getMessage());
        }
    }

    @Override
    public ProcessInstanceDTO detail(Integer processInstanceId, Long projectCode) {
        try {
            Result result = dolphinApiClient.instance_detail(processInstanceId,projectCode);
            return DctConstant.changeObjectToClass(result.getData(), ProcessInstanceDTO.class);
        } catch (ApiException a) {
            throw new BizException("dolphin instance detail error :" + a.getMessage());
        }catch (JsonProcessingException a) {
            throw new BizException("dolphin instance toClass error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin instance exception error :" + a.getMessage());
        }
    }

    @Override
    public TaskInstanceResultDTO taskByProcessId(Integer processInstanceId, Long projectCode) {
        try {
            Result result = dolphinApiClient.taskByProcessId(processInstanceId,projectCode);
            return DctConstant.changeObjectToClass(result.getData(), TaskInstanceResultDTO.class);
        } catch (ApiException a) {
            throw new BizException("dolphin task search error :" + a.getMessage());
        }catch (JsonProcessingException a) {
            throw new BizException("dolphin task toClass error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin task exception error :" + a.getMessage());
        }
    }

    @Override
    public String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum) {
        try {
            return dolphinApiClient.taskLog(taskInstanceId, limit, skipLineNum).getData();
        } catch (ApiException a) {
            throw new BizException("dolphin task log error :" + a.getMessage());
        }
    }
}
