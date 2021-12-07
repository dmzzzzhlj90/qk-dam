package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceDTO;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
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
}
