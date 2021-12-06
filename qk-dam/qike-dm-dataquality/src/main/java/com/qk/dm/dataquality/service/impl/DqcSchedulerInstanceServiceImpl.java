package com.qk.dm.dataquality.service.impl;

import com.qk.dm.dataquality.constant.schedule.ExecuteTypeEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceExecuteDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.service.DqcSchedulerInstanceService;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.SchedulerInstanceConstantsVO;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/12/4 4:32 下午
 * @since 1.0.0
 */
@Service
public class DqcSchedulerInstanceServiceImpl implements DqcSchedulerInstanceService {

    private final DolphinScheduler dolphinScheduler;

    public DqcSchedulerInstanceServiceImpl(DolphinScheduler dolphinScheduler) {
        this.dolphinScheduler = dolphinScheduler;
    }

    @Override
    public DqcProcessInstanceVO search(DqcSchedulerInstanceParamsDTO instanceParamsDTO) {
        ProcessInstanceSearchDTO instanceSearchDTO =
                ProcessInstanceSearchDTO.builder()
                        .processDefinitionId(instanceParamsDTO.getProcessDefinitionId())
                        .pageNo(instanceParamsDTO.getPagination().getPage())
                        .pageSize(instanceParamsDTO.getPagination().getSize())
                        .startDate(instanceParamsDTO.getStartDate())
                        .endDate(instanceParamsDTO.getEndDate())
                        .searchVal(instanceParamsDTO.getSearchVal())
                        .stateType(instanceParamsDTO.getStateType())
                        .build();
        // 获取到最近运行实例
        return dolphinScheduler.detailByList(instanceSearchDTO);
    }

    @Override
    public void execute(DqcSchedulerInstanceExecuteDTO instanceExecute) {
        dolphinScheduler.execute(instanceExecute.getProcessInstanceId(),instanceExecute.getExecuteType());
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
