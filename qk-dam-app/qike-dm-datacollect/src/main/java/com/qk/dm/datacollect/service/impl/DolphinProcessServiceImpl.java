package com.qk.dm.datacollect.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.datacollect.dolphin.DolphinApiClient;
import com.qk.dm.datacollect.dolphin.DolphinHttpClient;
import com.qk.dm.datacollect.dolphin.DolphinTaskDefinitionPropertiesBean;
import com.qk.dm.datacollect.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.dto.ProcessDefinitionResultDTO;
import com.qk.dm.datacollect.service.DolphinProcessService;
import com.qk.dm.datacollect.util.DctConstant;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2022/4/21 15:46
 * @since 1.0.0
 */
@Service
public class DolphinProcessServiceImpl implements DolphinProcessService {
    private final DolphinApiClient dolphinApiClient;
    private final DolphinHttpClient dolphinHttpClient;

    public DolphinProcessServiceImpl(DolphinApiClient dolphinApiClient, DolphinHttpClient dolphinHttpClient) {
        this.dolphinApiClient = dolphinApiClient;
        this.dolphinHttpClient = dolphinHttpClient;
    }

    @Override
    public ProcessDefinitionDTO createProcessDefinition(Long projectId, String name, String url, Object httpParams, String httpMethod, String description) {
        try {
            Result result = dolphinHttpClient.createProcessDefinition(projectId, name, url, httpParams, httpMethod, description);
            return DctConstant.changeObjectToClass(result.getData(), ProcessDefinitionDTO.class);
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin process exception error :" + a.getMessage());
        }
    }

    @Override
    public void updateProcessDefinition(Long projectId, String name, long taskCode, String url, Object httpParams, String httpMethod, String description) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(taskCode, projectId);
            DolphinTaskDefinitionPropertiesBean propertiesBean = DctConstant.changeObjectToClass(result.getData(), DolphinTaskDefinitionPropertiesBean.class);
            dolphinHttpClient.updateProcessDefinition(projectId, name, taskCode, url, httpParams, httpMethod, description, propertiesBean);
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        } catch (JsonProcessingException a) {
            throw new BizException("dolphin process toClass error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin process exception error :" + a.getMessage());
        }
    }

    @Override
    public void release(Long code, Long projectCode, ProcessDefinition.ReleaseStateEnum releaseState) {
        try {
            dolphinApiClient.dolphin_process_release(code, projectCode, releaseState);
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        }
    }

    @Override
    public void runing(Long projectCode, Long processDefinitionCode, Long environmentCode) {
        try {
            dolphinApiClient.dolphin_process_check(projectCode, processDefinitionCode);
            dolphinApiClient.dolphin_process_runing(projectCode, processDefinitionCode, null);
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        }
    }

    @Override
    public ProcessDefinitionResultDTO list(Long projectCode, String searchVal, Integer pageNo, Integer pageSize) {
        try {
            Result result = dolphinApiClient.dolphin_process_list(projectCode, searchVal, pageNo, pageSize);
            return DctConstant.changeObjectToClass(result.getData(), ProcessDefinitionResultDTO.class);
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        } catch (JsonProcessingException a) {
            throw new BizException("dolphin process toClass error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin process exception error :" + a.getMessage());
        }
    }
}
