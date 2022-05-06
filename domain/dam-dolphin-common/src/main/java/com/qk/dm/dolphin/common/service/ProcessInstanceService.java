package com.qk.dm.dolphin.common.service;

import com.qk.datacenter.model.ProcessInstance;
import com.qk.dm.dolphin.common.dto.*;

/**
 * @author shenpj
 * @date 2022/4/21 15:14
 * @since 1.0.0
 */
public interface ProcessInstanceService {
    void execute(Integer processInstanceId,  ProcessInstance.CmdTypeIfComplementEnum executeType);

    ProcessInstanceResultDTO search(ProcessInstanceSearchDTO instanceSearchDTO);

    ProcessInstanceDTO detail(Integer processInstanceId);

    TaskInstanceListResultDTO taskByProcessId(Integer processInstanceId);

    String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum);

    TaskInstanceResultDTO taskPageByProcessId(TaskInstanceSearchDTO instanceSearchDTO);
}
