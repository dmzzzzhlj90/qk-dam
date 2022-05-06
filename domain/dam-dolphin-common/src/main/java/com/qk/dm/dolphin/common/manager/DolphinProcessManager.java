package com.qk.dm.dolphin.common.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.dolphin.common.client.DolphinApiClient;
import com.qk.dm.dolphin.common.dto.ProcessDefinitionDTO;
import com.qk.dm.dolphin.common.dto.ProcessDefinitionResultDTO;
import com.qk.dm.dolphin.common.utils.ConstantUtil;
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
public class DolphinProcessManager{
    private static final Logger LOG = LoggerFactory.getLogger(DolphinProcessManager.class);
    private final DolphinApiClient dolphinApiClient;

    public DolphinProcessManager(DolphinApiClient dolphinApiClient) {
        this.dolphinApiClient = dolphinApiClient;
    }

    public void release(Long processDefinitionCode, String releaseState) {
        try {
            dolphinApiClient.dolphin_process_release(processDefinitionCode, ProcessDefinition.ReleaseStateEnum.fromValue(releaseState));
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 上下线失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process release error ：" + a.getMessage());
        }
    }

    public void runing(Long processDefinitionCode,  Long environmentCode) {
        try {
            dolphinApiClient.dolphin_process_check(processDefinitionCode);
            dolphinApiClient.dolphin_process_runing(processDefinitionCode, environmentCode);
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 运行失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process runing error ：" + a.getMessage());
        }
    }

    public ProcessDefinitionResultDTO pageList(String searchVal, Integer pageNo, Integer pageSize) {
        try {
            Result result = dolphinApiClient.dolphin_process_page(searchVal, pageNo, pageSize);
            return ConstantUtil.changeObjectToClass(result.getData(), ProcessDefinitionResultDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 查询流程列表失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process search error ：" + a.getMessage());
        } catch (JsonProcessingException a) {
            LOG.error("Dolphin 查询流程成功，解析时失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process toClass error");
        } catch (Exception a) {
            LOG.error("Dolphin 查询流程报错，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process search exception error");
        }
    }

    public List<ProcessDefinitionDTO> list() {
        try {
            Result result = dolphinApiClient.dolphin_process_list();
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.getData();
            List<Map<String, Object>> processDefinitionList = data.stream().map(da -> (Map<String, Object>) da.get("processDefinition")).collect(Collectors.toList());
            return BeanMapUtils.changeListToBeans(processDefinitionList, ProcessDefinitionDTO.class);
        } catch (ApiException a) {
            LOG.error("Dolphin 查询流程列表失败，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process search error ：" + a.getMessage());
        } catch (Exception a) {
            LOG.error("Dolphin 查询流程报错，原因为[{}]", a.getMessage());
            throw new BizException("dolphin process search exception error");
        }
    }

    public void delete(Long processDefinitionCode) {
        try {
            dolphinApiClient.dolphin_process_delete(processDefinitionCode);
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 删除失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process delete error ：" + a.getMessage());
        }
    }

    public Object detail(Long processDefinitionCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode);
            return result.getData();
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 查询详情失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail error ：" + a.getMessage());
        }
    }

    public ProcessDefinitionDTO detailToProcess(Long processDefinitionCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode);
            Map<String, Object> data = (Map<String, Object>) result.getData();
            Map<String, Object> processDefinition = (Map<String, Object>) data.get("processDefinition");
            return BeanMapUtils.changeMapToBean(processDefinition, new ProcessDefinitionDTO());
        } catch (ApiException a) {
            LOG.error("Dolphin 流程[{}] 查询详情失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail error ：" + a.getMessage());
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 查询详情成功，解析时失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("dolphin process detail exception error");
        }
    }
}
