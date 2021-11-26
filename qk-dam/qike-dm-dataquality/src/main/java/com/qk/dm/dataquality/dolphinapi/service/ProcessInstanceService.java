package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceResultDTO;

/**
 * @author shenpj
 * @date 2021/11/23 2:30 下午
 * @since 1.0.0
 */
public interface ProcessInstanceService {

    void execute(Integer processInstanceId, String executeType);

    ProcessInstanceResultDTO search(Integer processDefinitionId, Integer pageNo, Integer pageSize);

    ProcessInstanceDTO detail(Integer processInstanceId);
}
