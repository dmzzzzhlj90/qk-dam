package com.qk.dm.datacollect.mapstruct;

import com.qk.dm.datacollect.dolphin.dto.ProcessInstanceSearchDTO;
import com.qk.dm.datacollect.dolphin.dto.ProcessInstanceDTO;
import com.qk.dm.datacollect.dolphin.dto.TaskInstanceDTO;
import com.qk.dm.datacollect.vo.DctInstanceParamsVO;
import com.qk.dm.datacollect.vo.DctProcessInstanceVO;
import com.qk.dm.datacollect.vo.DctTaskInstanceParamsVO;
import com.qk.dm.datacollect.dolphin.dto.TaskInstanceSearchDTO;
import com.qk.dm.datacollect.vo.DctTaskInstanceVO;
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
public interface DctProcessInstanceMapper {
    DctProcessInstanceMapper INSTANCE = Mappers.getMapper(DctProcessInstanceMapper.class);

    DctProcessInstanceVO userDqcProcessInstanceVO(ProcessInstanceDTO processInstanceDTO);

    List<DctProcessInstanceVO> userDqcProcessInstanceVO(List<ProcessInstanceDTO> processInstanceDTO);

    List<DctTaskInstanceVO> userDqcProcessTaskInstanceVO(List<TaskInstanceDTO> taskInstanceDTOS);

    @Mappings({
            @Mapping(source = "pagination.page",target = "pageNo"),
            @Mapping(source = "pagination.size",target = "pageSize")
    })
    TaskInstanceSearchDTO taskInstanceSearchDTO(DctTaskInstanceParamsVO taskInstanceParamsDTO);

    @Mappings({
            @Mapping(source = "pagination.page",target = "pageNo"),
            @Mapping(source = "pagination.size",target = "pageSize")
    })
    ProcessInstanceSearchDTO instanceSearchDTO(DctInstanceParamsVO instanceParamsDTO);
//
//    List<TaskInstanceVO> taskInstanceVO(List<TaskInstanceDTO> taskInstanceDTOs);
}
