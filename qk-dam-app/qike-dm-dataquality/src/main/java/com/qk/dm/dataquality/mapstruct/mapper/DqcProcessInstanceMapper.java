package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceSearchDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TaskInstanceDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerTaskInstanceParamsDTO;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.DqcProcessTaskInstanceVO;
import com.qk.dm.dataquality.vo.TaskInstanceVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/26 3:25 下午
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DqcProcessInstanceMapper {
    DqcProcessInstanceMapper INSTANCE = Mappers.getMapper(DqcProcessInstanceMapper.class);

    DqcProcessInstanceVO userDqcProcessInstanceVO(ProcessInstanceDTO processInstanceDTO);

    List<DqcProcessInstanceVO> userDqcProcessInstanceVO(List<ProcessInstanceDTO> processInstanceDTO);

    List<DqcProcessTaskInstanceVO> userDqcProcessTaskInstanceVO(List<TaskInstanceDTO> taskInstanceDTOS);

    @Mappings({
            @Mapping(source = "pagination.page",target = "pageNo"),
            @Mapping(source = "pagination.size",target = "pageSize")
    })
    ProcessTaskInstanceSearchDTO taskInstanceSearchDTO(DqcSchedulerTaskInstanceParamsDTO taskInstanceParamsDTO);

    @Mappings({
            @Mapping(source = "pagination.page",target = "pageNo"),
            @Mapping(source = "pagination.size",target = "pageSize")
    })
    ProcessInstanceSearchDTO instanceSearchDTO(DqcSchedulerInstanceParamsDTO instanceParamsDTO);

    List<TaskInstanceVO> taskInstanceVO(List<TaskInstanceDTO> taskInstanceDTOs);
}
