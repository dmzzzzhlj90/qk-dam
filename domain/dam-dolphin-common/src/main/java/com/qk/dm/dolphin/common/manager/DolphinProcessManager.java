package com.qk.dm.dolphin.common.manager;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
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
 * 流程定义管理
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

    /**
     * 流程定义上下线
     * @param processDefinitionCode 流程定义code
     * @param releaseState 上下线状态 ONLINE-上线 OFFLINE-下线
     */
    public void release(Long processDefinitionCode, String releaseState) {
        try {
            dolphinApiClient.dolphin_process_release(processDefinitionCode, ProcessDefinition.ReleaseStateEnum.fromValue(releaseState));
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 上下线失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("操作失败");
        }
    }

    /**
     * 流程定义运行
     * @param processDefinitionCode 流程定义code
     * @param environmentCode
     */
    public void runing(Long processDefinitionCode,  Long environmentCode) {
        try {
            dolphinApiClient.dolphin_process_check(processDefinitionCode);
            dolphinApiClient.dolphin_process_runing(processDefinitionCode, environmentCode);
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 运行失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("运行失败");
        }
    }

    /**
     * 流程定义分页列表
     * @param searchVal 查询字段，包含名称及详情
     * @param pageNo 页码
     * @param pageSize 条数
     * @return
     */
    public ProcessDefinitionResultDTO pageList(String searchVal, Integer pageNo, Integer pageSize) {
        try {
            Result result = dolphinApiClient.dolphin_process_page(searchVal, pageNo, pageSize);
            return ConstantUtil.changeObjectToClass(result.getData(), ProcessDefinitionResultDTO.class);
        } catch (Exception a) {
            LOG.error("Dolphin 查询流程报错，原因为[{}]", a.getMessage());
            throw new BizException("查询失败");
        }
    }

    /**
     * 流程定义列表
     * @return
     */
    public List<ProcessDefinitionDTO> list() {
        try {
            Result result = dolphinApiClient.dolphin_process_list();
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.getData();
            List<Map<String, Object>> processDefinitionList = data.stream().map(da -> (Map<String, Object>) da.get("processDefinition")).collect(Collectors.toList());
            return BeanMapUtils.changeListToBeans(processDefinitionList, ProcessDefinitionDTO.class);
        } catch (Exception a) {
            LOG.error("Dolphin 查询流程报错，原因为[{}]", a.getMessage());
            throw new BizException("查询失败");
        }
    }

    /**
     * 流程定义删除
     * @param processDefinitionCode 流程定义code
     */
    public void delete(Long processDefinitionCode) {
        try {
            dolphinApiClient.dolphin_process_delete(processDefinitionCode);
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 删除失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("删除失败");
        }
    }

    /**
     * 流程定义详情
     * @param processDefinitionCode 流程定义code
     * @return 全部信息
     */
    public Object detail(Long processDefinitionCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode);
            return result.getData();
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 查询详情失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("查询失败");
        }
    }

    /**
     * 流程定义详情
     * @param processDefinitionCode 流程定义code
     * @return processDefinition信息
     */
    public ProcessDefinitionDTO detailToProcess(Long processDefinitionCode) {
        try {
            Result result = dolphinApiClient.dolphin_process_detail(processDefinitionCode);
            Map<String, Object> data = (Map<String, Object>) result.getData();
            Map<String, Object> processDefinition = (Map<String, Object>) data.get("processDefinition");
            return BeanMapUtils.changeMapToBean(processDefinition, new ProcessDefinitionDTO());
        } catch (Exception a) {
            LOG.error("Dolphin 流程[{}] 查询详情失败，原因为[{}]", processDefinitionCode, a.getMessage());
            throw new BizException("查询失败");
        }
    }
}
