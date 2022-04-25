package com.qk.dm.datacollect.dolphin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.datacollect.dolphin.client.DolphinApiClient;
import com.qk.dm.datacollect.dolphin.client.DolphinHttpClient;
import com.qk.dm.datacollect.dolphin.client.DolphinTaskDefinitionPropertiesBean;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionResultDTO;
import com.qk.dm.datacollect.dolphin.service.DolphinProcessService;
import com.qk.dm.datacollect.util.DctConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author shenpj
 * @date 2022/4/21 15:46
 * @since 1.0.0
 */
@Service
public class DolphinProcessServiceImpl implements DolphinProcessService {
    private static final Logger LOG = LoggerFactory.getLogger(DolphinProcessServiceImpl.class);
    private final DolphinApiClient dolphinApiClient;
    private final DolphinHttpClient dolphinHttpClient;

    public DolphinProcessServiceImpl(DolphinApiClient dolphinApiClient, DolphinHttpClient dolphinHttpClient) {
        this.dolphinApiClient = dolphinApiClient;
        this.dolphinHttpClient = dolphinHttpClient;
    }

    @Override
    public ProcessDefinitionDTO createProcessDefinition(Long projectId,
                                                        String name,
                                                        String url,
                                                        Object httpParams,
                                                        String httpMethod,
                                                        String description) {
        try {
            Result result = dolphinHttpClient.createProcessDefinition(projectId, name, url, httpParams, httpMethod, description);
            ProcessDefinitionDTO processDefinition = BeanMapUtils.changeMapToBean((Map<String, Object>) result.getData(), new ProcessDefinitionDTO());
            return processDefinition;
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 创建流程失败，原因为[{}]", projectId, a.getMessage());
            throw new BizException("dolphin process create error");
        } catch (Exception a) {
            LOG.error("Dolphin 项目[{}] 创建流程成功，解析时失败，原因为[{}]", projectId, a.getMessage());
            throw new BizException("dolphin process analysis error");
        }
    }

    @Override
    public void updateProcessDefinition(Long processDefinitionCode,
                                        Long projectId,
                                        long taskCode,
                                        String name,
                                        String url,
                                        Object httpParams,
                                        String httpMethod,
                                        String description) {
        try {
            dolphinHttpClient.updateProcessDefinition(processDefinitionCode, projectId, taskCode, name, url, httpParams, httpMethod, description, new DolphinTaskDefinitionPropertiesBean());
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 修改流程[{}] 失败，原因为[{}]", projectId, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process update error");
        } catch (Exception a) {
            LOG.error("Dolphin 项目[{}] 修改流程[{}] 成功，解析时失败，原因为[{}]", projectId, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process analysis error");
        }
    }

    @Override
    public void release(Long processDefinitionCode, Long projectCode, ProcessDefinition.ReleaseStateEnum releaseState) {
        try {
            dolphinApiClient.dolphin_process_release(processDefinitionCode, projectCode, releaseState);
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 上下线失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process release error");
        }
    }

    @Override
    public void runing(Long processDefinitionCode, Long projectCode, Long environmentCode) {
        try {
            dolphinApiClient.dolphin_process_check(processDefinitionCode, projectCode);
            dolphinApiClient.dolphin_process_runing(processDefinitionCode, projectCode, environmentCode);
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 运行失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process runing error");
        }
    }

    @Override
    public ProcessDefinitionResultDTO list(Long projectCode, String searchVal, Integer pageNo, Integer pageSize) {
        try {
            Result result = dolphinApiClient.dolphin_process_list(projectCode, searchVal, pageNo, pageSize);
            return DctConstant.changeObjectToClass(result.getData(), ProcessDefinitionResultDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 查询流程列表失败，原因为[{}]", projectCode, a.getMessage());
            throw new BizException("dolphin process search error");
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 项目[{}] 查询流程成功，解析时失败，原因为[{}]", projectCode, a.getMessage());
            throw new BizException("dolphin process toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 项目[{}] 查询流程报错，原因为[{}]", projectCode, a.getMessage());
            throw new BizException("dolphin process search exception error");
        }
    }

    @Override
    public void delete(Long processDefinitionCode, Long projectCode) {
        try {
            dolphinApiClient.dolphin_process_delete(processDefinitionCode, projectCode);
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 删除失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process delete error");
        }
    }

    @Override
    public Object detail(Long processDefinitionCode, Long projectCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode, projectCode);
            return result.getData();
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 查询详情失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail error");
        }
    }

    @Override
    public ProcessDefinitionDTO detailToProcess(Long processDefinitionCode, Long projectCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode, projectCode);
            Map<String, Object> data = (Map<String, Object>) result.getData();
            Map<String, Object> processDefinition = (Map<String, Object>) data.get("processDefinition");
            return BeanMapUtils.changeMapToBean(processDefinition, new ProcessDefinitionDTO());
        } catch (ApiException a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 查询详情失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail error");
        } catch (Exception a) {
            LOG.error("Dolphin 项目[{}] 流程[{}] 查询详情成功，解析时失败，原因为[{}]", projectCode, processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail exception error");
        }
    }
}
