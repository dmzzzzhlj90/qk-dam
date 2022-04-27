package com.qk.dm.datacollect.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.dolphin.dto.*;
import com.qk.dm.datacollect.dolphin.service.DolphinProcessService;
import com.qk.dm.datacollect.dolphin.service.ProcessInstanceService;
import com.qk.dm.datacollect.mapstruct.DctProcessInstanceMapper;
import com.qk.dm.datacollect.service.DctInstanceService;
import com.qk.dm.datacollect.util.Pager;
import com.qk.dm.datacollect.vo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2022/4/25 11:31
 * @since 1.0.0
 */
@Service
public class DctInstanceServiceImpl implements DctInstanceService {
    private final ProcessInstanceService processInstanceService;
    private final DolphinProcessService dolphinProcessService;
    @Value("${dolphinscheduler.task.projectCode}")
    private Long projectCode;

    public DctInstanceServiceImpl(ProcessInstanceService processInstanceService, DolphinProcessService dolphinProcessService) {
        this.processInstanceService = processInstanceService;
        this.dolphinProcessService = dolphinProcessService;
    }

    @Override
    public PageResultVO<DctProcessInstanceVO> search(DctSchedulerInstanceParamsDTO instanceParamsDTO) {

        //dolphin查询
        ProcessInstanceSearchDTO instanceSearch = DctProcessInstanceMapper.INSTANCE.instanceSearchDTO(instanceParamsDTO);
        List<ProcessInstanceDTO> totalList = new ArrayList<>();
        long size;
        if (instanceParamsDTO.getDirId() != null) {
            instanceSearch.setSearchVal(instanceParamsDTO.getDirId());
            ProcessInstanceResultDTO instance;
            int page = 1;
            instanceSearch.setPageSize(100);
            do {
                instanceSearch.setPageNo(page);
                instance = processInstanceService.search(projectCode, instanceSearch);
                totalList.addAll(instance.getTotalList());
                page++;
            } while (instance.getTotalList().size() > 0);
            if (instanceParamsDTO.getSearchVal() != null) {
                totalList = totalList
                        .stream()
                        .filter(processDefinition -> processDefinition.getName().contains(instanceParamsDTO.getSearchVal()))
                        .collect(Collectors.toList());
            }
            size = totalList.size();
            totalList = Pager.getList(instanceParamsDTO.getPagination(), totalList);
        } else {
            ProcessInstanceResultDTO instance = processInstanceService.search(projectCode, instanceSearch);
            totalList.addAll(instance.getTotalList());
            size = instance.getTotal();
        }

        List<DctProcessInstanceVO> dqcProcessInstanceList = DctProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(totalList);
        //把name中的分类去除
        DctProcessInstanceVO.changeName(dqcProcessInstanceList);


        //查询任务实例并赋值
        dqcProcessInstanceList.forEach(processInstance -> {
            TaskInstanceListResultDTO taskInstanceResultDTO = processInstanceService.taskByProcessId(processInstance.getId().intValue(), projectCode);
            List<TaskInstanceListDTO> taskList = taskInstanceResultDTO.getTaskList();
            if (taskList.size() > 0) {
                processInstance.setTaskInstanceId(taskList.get(0).getId());
            }
        });
        return new PageResultVO<>(
                size,
                instanceParamsDTO.getPagination().getPage(),
                instanceParamsDTO.getPagination().getSize(),
                dqcProcessInstanceList
        );


//        //dolphin查询
//        ProcessInstanceSearchDTO instanceSearch = DctProcessInstanceMapper.INSTANCE.instanceSearchDTO(instanceParamsDTO);
//        ProcessInstanceResultDTO instance = processInstanceService.search(projectCode, instanceSearch);
//        List<DctProcessInstanceVO> dqcProcessInstanceList = DctProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(instance.getTotalList());
//        //查询任务实例并赋值
//        dqcProcessInstanceList.forEach(processInstance -> {
//            TaskInstanceListResultDTO taskInstanceResultDTO = processInstanceService.taskByProcessId(processInstance.getId().intValue(), projectCode);
//            List<TaskInstanceListDTO> taskList = taskInstanceResultDTO.getTaskList();
//            if (taskList.size() > 0) {
//                processInstance.setTaskInstanceId(taskList.get(0).getId());
//            }
//        });
//        return new PageResultVO<>(
//                instance.getTotal(),
//                instanceParamsDTO.getPagination().getPage(),
//                instanceParamsDTO.getPagination().getSize(),
//                dqcProcessInstanceList
//        );

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
        TaskInstanceResultDTO taskInstanceResultDTO = processInstanceService.taskPageByProcessId(projectCode, instanceSearchDTO);
        return new PageResultVO<>(
                taskInstanceResultDTO.getTotal(),
                dctTaskInstanceParamsVO.getPagination().getPage(),
                dctTaskInstanceParamsVO.getPagination().getSize(),
                DctProcessInstanceMapper.INSTANCE.userDqcProcessTaskInstanceVO(taskInstanceResultDTO.getTotalList()));
    }

    @Override
    public Object taskLogDownload(Integer taskInstanceId) {
        StringBuilder result = new StringBuilder();
        int skipLineNum = 0;
        int limit = 1000;
        String log;
        do {
            log = processInstanceService.taskLog(taskInstanceId, limit, skipLineNum);
            result.append(log);
            skipLineNum += limit;
        } while (!"".equals(log));
        return result.toString();
    }


}
