package com.qk.dm.dataquality.dolphinapi.builder;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.dolphinapi.constant.Priority;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 构建ProcessData对象
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class ProcessDataBuilder {
    private final ProcessDataDTO processData = new ProcessDataDTO();

    public ProcessDataDTO getProcessData() {
        return processData;
    }

    public ProcessDataBuilder info(List<TaskNodeDTO> taskNodes, TenantDTO tenantDTO) {
        taskNode(taskNodes);
        globalParams();
        timeout();
        tenantId(tenantDTO);
        return this;
    }

    public ProcessDataBuilder taskNode(List<TaskNodeDTO> taskNodes) {
        processData.setTasks(taskNodes);
        return this;
    }

    public ProcessDataBuilder globalParams() {
        List<PropertyDTO> globalParams = new ArrayList<>();
        processData.setGlobalParams(globalParams);
        return this;
    }

    public ProcessDataBuilder timeout() {
        //TODO 超时时间
        processData.setTimeout(0);
        return this;

    }

    public ProcessDataBuilder tenantId(TenantDTO tenantDTO) {
        processData.setTenantId(tenantDTO.getId());
        return this;
    }


}
