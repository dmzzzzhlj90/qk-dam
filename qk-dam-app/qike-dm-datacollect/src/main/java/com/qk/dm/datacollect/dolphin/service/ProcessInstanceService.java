package com.qk.dm.datacollect.dolphin.service;

import com.qk.datacenter.model.ProcessInstance;
import com.qk.dm.datacollect.dolphin.dto.ProcessInstanceSearchDTO;
import com.qk.dm.datacollect.dolphin.dto.*;

/**
 * @author shenpj
 * @date 2022/4/21 15:14
 * @since 1.0.0
 */
public interface ProcessInstanceService {
    void execute(Integer processInstanceId, Long projectCode, ProcessInstance.CmdTypeIfComplementEnum executeType);

    ProcessInstanceResultDTO search(Long projectCode, ProcessInstanceSearchDTO instanceSearchDTO);

    ProcessInstanceDTO detail(Integer processInstanceId, Long projectCode);

    TaskInstanceListResultDTO taskByProcessId(Integer processInstanceId, Long projectCode);

    String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum);

    TaskInstanceResultDTO taskPageByProcessId(Long projectCode, TaskInstanceSearchDTO instanceSearchDTO);
}
