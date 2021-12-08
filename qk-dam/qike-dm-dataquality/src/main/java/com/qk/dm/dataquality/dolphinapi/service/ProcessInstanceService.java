package com.qk.dm.dataquality.dolphinapi.service;

import com.qk.dm.dataquality.dolphinapi.dto.*;

/**
 * @author shenpj
 * @date 2021/11/23 2:30 下午
 * @since 1.0.0
 */
public interface ProcessInstanceService {

    void execute(Integer processInstanceId, String executeType);

    ProcessInstanceResultDTO search(ProcessInstanceSearchDTO instanceSearchDTO);

    ProcessInstanceDTO detail(Integer processInstanceId);

    ProcessTaskInstanceResultDTO searchTask(ProcessTaskInstanceSearchDTO TaskInstanceSearch);

    String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum);

    String taskLogDownload(Integer taskInstanceId);
}
