package com.qk.dm.dataquality.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.schedule.ExecuteTypeEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.mapstruct.mapper.DqcProcessInstanceMapper;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceExecuteDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerTaskInstanceParamsDTO;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerInstanceService;
import com.qk.dm.dataquality.utils.Pager;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.DqcProcessTaskInstanceVO;
import com.qk.dm.dataquality.vo.SchedulerInstanceConstantsVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/12/4 4:32 下午
 * @since 1.0.0
 */
@Service
public class DqcSchedulerInstanceServiceImpl implements DqcSchedulerInstanceService {

    private final DolphinScheduler dolphinScheduler;
    private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;

    public DqcSchedulerInstanceServiceImpl(DolphinScheduler dolphinScheduler, DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService) {
        this.dolphinScheduler = dolphinScheduler;
        this.dqcSchedulerBasicInfoService = dqcSchedulerBasicInfoService;
    }

    @Override
    public PageResultVO<DqcProcessInstanceVO> search(DqcSchedulerInstanceParamsDTO instanceParamsDTO) {
        if (instanceParamsDTO.getDirId() != null) {
            return getInstancePageByDirId(instanceParamsDTO);
        }
        return getInstancePage(instanceParamsDTO);
    }

    private PageResultVO<DqcProcessInstanceVO> getInstancePageByDirId(DqcSchedulerInstanceParamsDTO instanceParamsDTO) {
        //获取DirId底下所有实例信息
        List<ProcessInstanceDTO> totalList = getProcessInstanceResultList(instanceParamsDTO);
        //封装分页
        return getInstancePage(instanceParamsDTO, getPager(instanceParamsDTO, totalList), totalList.size());
    }

    private List<ProcessInstanceDTO> getPager(DqcSchedulerInstanceParamsDTO instanceParamsDTO, List<ProcessInstanceDTO> totalList) {
        //内存分页
        return Pager.getList(instanceParamsDTO.getPagination().getPage(), instanceParamsDTO.getPagination().getSize(), totalList);
    }

    private List<ProcessInstanceDTO> getProcessInstanceResultList(DqcSchedulerInstanceParamsDTO instanceParamsDTO) {
        //根据创建时间排序
        return getTotalListSort(getAllProcessInstance(instanceParamsDTO));
    }

    private List<ProcessInstanceDTO> getTotalListSort(List<ProcessInstanceDTO> totalList) {
        //时间排序
        totalList.sort((obj1, obj2) -> obj2.getStartTime().compareTo(obj1.getStartTime()));
        return totalList;
    }

    private List<ProcessInstanceDTO> getAllProcessInstance(DqcSchedulerInstanceParamsDTO instanceParamsDTO) {
        //查询所有实例信息
        List<ProcessInstanceDTO> totalList = new ArrayList<>();
        getProcessDefIdByDirId(instanceParamsDTO.getDirId()).forEach(pdId -> totalList.addAll(getProcessInstanceResult(instanceParamsDTO, pdId).getTotalList()));
        return totalList;
    }

    private List<Integer> getProcessDefIdByDirId(String dirId) {
        //查询目录下所有的流程定义ID
        return dqcSchedulerBasicInfoService.getInfoByDirId(dirId).stream().map(DqcSchedulerBasicInfo::getProcessDefinitionId).collect(Collectors.toList());
    }

    private ProcessInstanceResultDTO getProcessInstanceResult(DqcSchedulerInstanceParamsDTO instanceParamsDTO, Integer pdId) {
        //dolphin查询
        return dolphinScheduler.instanceList(
                getProcessInstanceSearch(
                        instanceParamsDTO, pdId, 1, instanceParamsDTO.getPagination().getPage() * instanceParamsDTO.getPagination().getSize()
                )
        );
    }

    private PageResultVO<DqcProcessInstanceVO> getInstancePage(DqcSchedulerInstanceParamsDTO instanceParamsDTO) {
        // 获取到最近运行实例
        ProcessInstanceResultDTO instance = getProcessInstanceResult(instanceParamsDTO);
        // 封装分页
        return getInstancePage(instanceParamsDTO, instance.getTotalList(), instance.getTotal());
    }

    private ProcessInstanceResultDTO getProcessInstanceResult(DqcSchedulerInstanceParamsDTO instanceParams) {
        //dolphin查询
        return dolphinScheduler.instanceList(
                getProcessInstanceSearch(
                        instanceParams, instanceParams.getProcessDefinitionId(), instanceParams.getPagination().getPage(), instanceParams.getPagination().getSize()
                )
        );
    }

    private ProcessInstanceSearchDTO getProcessInstanceSearch(DqcSchedulerInstanceParamsDTO instanceParamsDTO, Integer pdId, int page, int size) {
        //封装查询参数
        return ProcessInstanceSearchDTO.builder()
                .processDefinitionId(pdId)
                .pageNo(page)
                .pageSize(size)
                .startDate(instanceParamsDTO.getStartDate())
                .endDate(instanceParamsDTO.getEndDate())
                .searchVal(instanceParamsDTO.getSearchVal())
                .stateType(instanceParamsDTO.getStateType())
                .executorName(instanceParamsDTO.getExecutorName())
                .build();
    }

    private PageResultVO<DqcProcessInstanceVO> getInstancePage(DqcSchedulerInstanceParamsDTO instanceParamsDTO, List<ProcessInstanceDTO> list, int size) {
        return new PageResultVO<>(
                size,
                instanceParamsDTO.getPagination().getPage(),
                instanceParamsDTO.getPagination().getSize(),
                DqcProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(list));
    }

    private List<ProcessInstanceDTO> getProcessInstanceList(List<ProcessInstanceResultDTO> instanceList) {
        return instanceList.stream().map(ProcessInstanceResultDTO::getTotalList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public void execute(DqcSchedulerInstanceExecuteDTO instanceExecute) {
        dolphinScheduler.execute(instanceExecute.getProcessInstanceId(), instanceExecute.getExecuteType());
    }

    @Override
    public SchedulerInstanceConstantsVO getInstanceConstants() {
        return SchedulerInstanceConstantsVO
                .builder()
                .instanceStateTypeEnum(InstanceStateTypeEnum.getAllValue())
                .executeTypeEnum(ExecuteTypeEnum.getAllValue())
                .build();
    }

    @Override
    public PageResultVO<DqcProcessTaskInstanceVO> searchTask(DqcSchedulerTaskInstanceParamsDTO taskInstanceParamsDTO) {
        ProcessTaskInstanceResultDTO processTaskInstanceResultDTO = dolphinScheduler.taskInstanceList(ProcessTaskInstanceSearchDTO.builder()
                .processInstanceId(taskInstanceParamsDTO.getProcessInstanceId())
                .pageNo(taskInstanceParamsDTO.getPagination().getPage())
                .pageSize(taskInstanceParamsDTO.getPagination().getSize())
                .startDate(taskInstanceParamsDTO.getStartDate())
                .endDate(taskInstanceParamsDTO.getEndDate())
                .searchVal(taskInstanceParamsDTO.getSearchVal())
                .stateType(taskInstanceParamsDTO.getStateType())
                .executorName(taskInstanceParamsDTO.getExecutorName())
                .taskName(taskInstanceParamsDTO.getTaskName())
                .build());
        return new PageResultVO<>(
                processTaskInstanceResultDTO.getTotal(),
                taskInstanceParamsDTO.getPagination().getPage(),
                taskInstanceParamsDTO.getPagination().getSize(),
                DqcProcessInstanceMapper.INSTANCE.userDqcProcessTaskInstanceVO(processTaskInstanceResultDTO.getTotalList()));
    }


}
