package com.qk.dm.datacollect.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.dam.commons.util.GsonUtil;
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
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import com.qk.dm.datacollect.vo.DctSchedulerConfigVO;
import com.qk.dm.datacollect.vo.DctSchedulerRulesVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public ProcessDefinitionDTO createProcessDefinition(Long projectId,
                                                        String name,
                                                        String url,
                                                        Object httpParams,
                                                        String httpMethod,
                                                        String description) {
        try {
            Result result = dolphinHttpClient.createProcessDefinition(projectId, name, url, httpParams, httpMethod, description);
            return BeanMapUtils.changeMapToBean((Map<String, Object>) result.getData(), new ProcessDefinitionDTO());
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin process exception error :" + a.getMessage());
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
            throw new BizException("dolphin process search error :" + a.getMessage());
        } catch (Exception a) {
            throw new BizException("dolphin process exception error :" + a.getMessage());
        }
    }

    @Override
    public void release(Long processDefinitionCode, Long projectCode, ProcessDefinition.ReleaseStateEnum releaseState) {
        try {
            dolphinApiClient.dolphin_process_release(processDefinitionCode, projectCode, releaseState);
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        }
    }

    @Override
    public void runing(Long processDefinitionCode, Long projectCode, Long environmentCode) {
        try {
            dolphinApiClient.dolphin_process_check(processDefinitionCode, projectCode);
            dolphinApiClient.dolphin_process_runing(processDefinitionCode, projectCode, environmentCode);
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

    @Override
    public void delete(Long processDefinitionCode, Long projectCode) {
        try {
            dolphinApiClient.dolphin_process_delete(processDefinitionCode, projectCode);
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        }
    }

    @Override
    public DctSchedulerBasicInfoVO detail(Long processDefinitionCode, Long projectCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode, projectCode);
            return getDctSchedulerBasicInfoVO(result.getData());
        } catch (ApiException a) {
            throw new BizException("dolphin process search error :" + a.getMessage());
        }
    }

    private DctSchedulerBasicInfoVO getDctSchedulerBasicInfoVO(Object resultData) {
        try {
            Map<String, Object> data = (Map<String, Object>) resultData;
            //封装dctSchedulerBasicInfoVO
            Map<String, Object> processDefinition = (Map<String, Object>) data.get("processDefinition");
            DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO = BeanMapUtils.changeMapToBean(processDefinition, new DctSchedulerBasicInfoVO());
            List<Map<String, Object>> taskDefinitionList = (List<Map<String, Object>>) data.get("taskDefinitionList");
            Map<String, Object> taskParams = (Map<String, Object>) taskDefinitionList.get(taskDefinitionList.size()-1).get("taskParams");
            List<Map<String, Object>> httpParams = (List<Map<String, Object>>) taskParams.get("httpParams");

            for (Map<String, Object> httpParam : httpParams) {
                String value = httpParam.get("value").toString();
                switch (httpParam.get("prop").toString()) {
                    case "schedulerRules":
                        dctSchedulerBasicInfoVO.setSchedulerRules(
                                BeanMapUtils.changeMapToBean(
                                        (Map<String, Object>) GsonUtil.fromJsonString(value),
                                        new DctSchedulerRulesVO()));
                        break;
                    case "schedulerConfig":
                        dctSchedulerBasicInfoVO.setSchedulerConfig(
                                BeanMapUtils.changeMapToBean(
                                        (Map<String, Object>) GsonUtil.fromJsonString(value),
                                        new DctSchedulerConfigVO()));
                        break;
                    case "dirId":
                        dctSchedulerBasicInfoVO.setDirId(value);
                        break;
                }
            }

            return dctSchedulerBasicInfoVO;
        } catch (Exception a) {
            throw new BizException("dolphin detail error :" + a.getMessage());
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
            throw new BizException("dolphin process search error :" + a.getMessage());
        }
    }
}
