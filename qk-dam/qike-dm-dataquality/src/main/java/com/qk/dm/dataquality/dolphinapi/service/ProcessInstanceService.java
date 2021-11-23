package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.dolphinapi.builder.InstanceData;
import com.qk.dm.dataquality.dolphinapi.builder.InstanceDataBuilder;

/**
 * @author shenpj
 * @date 2021/11/23 2:30 下午
 * @since 1.0.0
 */
public interface ProcessInstanceService {

    void execute(Integer processInstanceId, String executeType);

    InstanceDataBuilder search(Integer processDefinitionId);

    InstanceData detail(Integer processInstanceId);
}
