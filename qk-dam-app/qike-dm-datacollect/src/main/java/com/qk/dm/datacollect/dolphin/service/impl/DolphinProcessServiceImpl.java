package com.qk.dm.datacollect.dolphin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.datacollect.dolphin.client.DolphinApiClient;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.dolphin.dto.ProcessDefinitionResultDTO;
import com.qk.dm.datacollect.dolphin.service.DolphinProcessService;
import com.qk.dm.datacollect.util.DctConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2022/4/21 15:46
 * @since 1.0.0
 */
@Service
public class DolphinProcessServiceImpl implements DolphinProcessService {
    private static final Logger LOG = LoggerFactory.getLogger(DolphinProcessServiceImpl.class);
    private final DolphinApiClient dolphinApiClient;


    public DolphinProcessServiceImpl(DolphinApiClient dolphinApiClient) {
        this.dolphinApiClient = dolphinApiClient;
    }

    @Override
    public void release(Long processDefinitionCode, ProcessDefinition.ReleaseStateEnum releaseState) {
        try {
            dolphinApiClient.dolphin_process_release(processDefinitionCode, releaseState);
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 上下线失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process release error");
        }
    }

    @Override
    public void runing(Long processDefinitionCode,  Long environmentCode) {
        try {
            dolphinApiClient.dolphin_process_check(processDefinitionCode);
            dolphinApiClient.dolphin_process_runing(processDefinitionCode, environmentCode);
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 运行失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process runing error");
        }
    }

    @Override
    public ProcessDefinitionResultDTO pageList(String searchVal, Integer pageNo, Integer pageSize) {
        try {
            Result result = dolphinApiClient.dolphin_process_page(searchVal, pageNo, pageSize);
            return DctConstant.changeObjectToClass(result.getData(), ProcessDefinitionResultDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 查询流程列表失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process search error");
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 查询流程成功，解析时失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 查询流程报错，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process search exception error");
        }
    }

    @Override
    public List<ProcessDefinitionDTO> list() {
        try {
            Result result = dolphinApiClient.dolphin_process_list();
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.getData();
            List<Map<String, Object>> processDefinitionList = data.stream().map(da -> (Map<String, Object>) da.get("processDefinition")).collect(Collectors.toList());
            return BeanMapUtils.changeListToBeans(processDefinitionList, ProcessDefinitionDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 查询流程列表失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process search error");
        } catch (Exception a) {
            LOG.error("Dolphin 查询流程报错，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process search exception error");
        }
    }

    @Override
    public void delete(Long processDefinitionCode) {
        try {
            dolphinApiClient.dolphin_process_delete(processDefinitionCode);
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 删除失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process delete error");
        }
    }

    @Override
    public Object detail(Long processDefinitionCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode);
            return result.getData();
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 查询详情失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail error");
        }
    }

    @Override
    public ProcessDefinitionDTO detailToProcess(Long processDefinitionCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode);
            Map<String, Object> data = (Map<String, Object>) result.getData();
            Map<String, Object> processDefinition = (Map<String, Object>) data.get("processDefinition");
            return BeanMapUtils.changeMapToBean(processDefinition, new ProcessDefinitionDTO());
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 查询详情失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail error");
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 查询详情成功，解析时失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail exception error");
        }
    }
}
