package com.qk.dm.dataquality.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.schedule.ExecuteTypeEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.mapstruct.mapper.DqcProcessInstanceMapper;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceExecuteDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerInstanceService;
import com.qk.dm.dataquality.utils.Pager;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.SchedulerInstanceConstantsVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        ProcessInstanceSearchDTO instanceSearchDTO =
                ProcessInstanceSearchDTO.builder()
                        .processDefinitionId(instanceParamsDTO.getProcessDefinitionId())
                        .pageNo(instanceParamsDTO.getPagination().getPage())
                        .pageSize(instanceParamsDTO.getPagination().getSize())
                        .startDate(instanceParamsDTO.getStartDate())
                        .endDate(instanceParamsDTO.getEndDate())
                        .searchVal(instanceParamsDTO.getSearchVal())
                        .stateType(instanceParamsDTO.getStateType())
                        .executorName(instanceParamsDTO.getExecutorName())
                        .build();
        // 获取到最近运行实例
        ProcessInstanceResultDTO instance = dolphinScheduler.detailByList(instanceSearchDTO);
        return getPageResultVO(instanceParamsDTO, instance.getTotalList(), instance.getTotal());

    }

    private PageResultVO<DqcProcessInstanceVO> getInstancePageByDirId(DqcSchedulerInstanceParamsDTO instanceParamsDTO) {
        List<DqcSchedulerBasicInfo> infoByDirId = dqcSchedulerBasicInfoService.getInfoByDirId(instanceParamsDTO.getDirId());
        List<Integer> collect = infoByDirId.stream().map(DqcSchedulerBasicInfo::getProcessDefinitionId).collect(Collectors.toList());
        List<ProcessInstanceDTO> totalList = new ArrayList<>();
        collect.forEach(pdId -> {
            ProcessInstanceSearchDTO instanceSearchDTO =
                    ProcessInstanceSearchDTO.builder()
                            .processDefinitionId(pdId)
                            .pageNo(1)
                            .pageSize(instanceParamsDTO.getPagination().getPage() * instanceParamsDTO.getPagination().getSize())
                            .startDate(instanceParamsDTO.getStartDate())
                            .endDate(instanceParamsDTO.getEndDate())
                            .searchVal(instanceParamsDTO.getSearchVal())
                            .stateType(instanceParamsDTO.getStateType())
                            .executorName(instanceParamsDTO.getExecutorName())
                            .build();
            ProcessInstanceResultDTO instance = dolphinScheduler.detailByList(instanceSearchDTO);
            totalList.addAll(instance.getTotalList());
        });
        //根据创建时间排序
        totalList.sort((obj1, obj2) -> obj2.getStartTime().compareTo(obj1.getStartTime()));
        //内存分页
        List<ProcessInstanceDTO> list = Pager.getList(instanceParamsDTO.getPagination().getPage(),
                instanceParamsDTO.getPagination().getSize(), totalList);
        return getPageResultVO(instanceParamsDTO, list, totalList.size());
    }

    private PageResultVO<DqcProcessInstanceVO> getPageResultVO(DqcSchedulerInstanceParamsDTO instanceParamsDTO, List<ProcessInstanceDTO> list, int size) {
        return new PageResultVO<>(
                size,
                instanceParamsDTO.getPagination().getPage(),
                instanceParamsDTO.getPagination().getSize(),
                DqcProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(list));
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


}
