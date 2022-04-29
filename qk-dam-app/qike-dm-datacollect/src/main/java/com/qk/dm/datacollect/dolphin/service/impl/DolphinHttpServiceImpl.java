package com.qk.dm.datacollect.dolphin.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.datacollect.dolphin.client.DolphinHttpClient;
import com.qk.dm.datacollect.dolphin.client.DolphinTaskDefinitionPropertiesBean;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.dolphin.service.DolphinProcessDefinitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author shenpj
 * @date 2022/4/28 15:50
 * @since 1.0.0
 */
@Service
public class DolphinHttpServiceImpl implements DolphinProcessDefinitionService {
    private static final Logger LOG = LoggerFactory.getLogger(DolphinHttpServiceImpl.class);
    private final DolphinHttpClient dolphinHttpClient;

    public DolphinHttpServiceImpl(DolphinHttpClient dolphinHttpClient) {
        this.dolphinHttpClient = dolphinHttpClient;
    }

    @Override
    public ProcessDefinitionDTO createProcessDefinition(String name, Object httpParams, String description) {
        try {
            Result result = dolphinHttpClient.createProcessDefinition(name, httpParams, description);
            ProcessDefinitionDTO processDefinition = BeanMapUtils.changeMapToBean((Map<String, Object>) result.getData(), new ProcessDefinitionDTO());
            return processDefinition;
        } catch (ApiException a) {
            LOG.error("Dolphin 项目 创建流程失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process create error");
        } catch (Exception a) {
            LOG.error("Dolphin 项目 创建流程成功，解析时失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process analysis error");
        }
    }

    @Override
    public void updateProcessDefinition(Long processDefinitionCode,
                                        long taskCode,
                                        String name,
                                        Object httpParams,
                                        String description) {
        try {
            dolphinHttpClient.updateProcessDefinition(processDefinitionCode, taskCode, name, httpParams, description, new DolphinTaskDefinitionPropertiesBean());
        } catch (ApiException a) {
            LOG.error("Dolphin 项目 修改流程[{}] 失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process update error");
        } catch (Exception a) {
            LOG.error("Dolphin 项目 修改流程[{}] 成功，解析时失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process analysis error");
        }
    }
}
