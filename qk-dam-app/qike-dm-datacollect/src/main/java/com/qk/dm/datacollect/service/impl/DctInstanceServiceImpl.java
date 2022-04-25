package com.qk.dm.datacollect.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.dolphin.ProcessInstanceSearchDTO;
import com.qk.dm.datacollect.dto.*;
import com.qk.dm.datacollect.mapstruct.DctProcessInstanceMapper;
import com.qk.dm.datacollect.service.DctInstanceService;
import com.qk.dm.datacollect.service.ProcessInstanceService;
import com.qk.dm.datacollect.vo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/4/25 11:31
 * @since 1.0.0
 */
@Service
public class DctInstanceServiceImpl implements DctInstanceService {
    private final ProcessInstanceService processInstanceService;
    @Value("${dolphinscheduler.task.projectCode}")
    private Long projectCode;

    public DctInstanceServiceImpl(ProcessInstanceService processInstanceService) {
        this.processInstanceService = processInstanceService;
    }

    @Override
    public PageResultVO<DctProcessInstanceVO> search(DctSchedulerInstanceParamsDTO instanceParamsDTO) {
        //dolphin查询
        ProcessInstanceSearchDTO instanceSearch = DctProcessInstanceMapper.INSTANCE.instanceSearchDTO(instanceParamsDTO);
        ProcessInstanceResultDTO instance = processInstanceService.search(projectCode, instanceSearch);
        List<DctProcessInstanceVO> dqcProcessInstanceList = DctProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(instance.getTotalList());
        //查询任务实例并赋值
        dqcProcessInstanceList.forEach(processInstance -> {
            TaskInstanceListResultDTO taskInstanceResultDTO = processInstanceService.taskByProcessId(processInstance.getId().intValue(), projectCode);
            List<TaskInstanceListDTO> taskList = taskInstanceResultDTO.getTaskList();
            if (taskList.size() > 0) {
                processInstance.setTaskInstanceId(taskList.get(0).getId());
            }
        });
        return new PageResultVO<>(
                instance.getTotal(),
                instanceParamsDTO.getPagination().getPage(),
                instanceParamsDTO.getPagination().getSize(),
                dqcProcessInstanceList
        );
    }


    @Override
    public DctProcessInstanceVO detail(Integer instanceId) {
        ProcessInstanceDTO detail = processInstanceService.detail(instanceId, projectCode);
        return DctProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(detail);
    }

    @Override
    public void execute(DctInstanceExecuteVO instanceExecute) {
        processInstanceService.execute(instanceExecute.getInstanceId(), projectCode, instanceExecute.getExecuteType());
    }

    @Override
    public PageResultVO<DctTaskInstanceVO> searchTask(DctTaskInstanceParamsVO dctTaskInstanceParamsVO) {
        TaskInstanceSearchDTO instanceSearchDTO = DctProcessInstanceMapper.INSTANCE.taskInstanceSearchDTO(dctTaskInstanceParamsVO);
        TaskInstanceResultDTO taskInstanceResultDTO = processInstanceService.taskPageByProcessId(projectCode,instanceSearchDTO);
        return new PageResultVO<>(
                taskInstanceResultDTO.getTotal(),
                dctTaskInstanceParamsVO.getPagination().getPage(),
                dctTaskInstanceParamsVO.getPagination().getSize(),
                DctProcessInstanceMapper.INSTANCE.userDqcProcessTaskInstanceVO(taskInstanceResultDTO.getTotalList()));
    }

}
