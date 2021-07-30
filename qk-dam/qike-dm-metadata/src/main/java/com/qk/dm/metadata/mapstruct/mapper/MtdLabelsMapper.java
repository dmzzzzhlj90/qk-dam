package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdLabels;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MtdLabelsMapper {
    MtdLabelsMapper INSTANCE = Mappers.getMapper(MtdLabelsMapper.class);

    MtdLabels useMtdLabels(MtdLabelsVO mtdLabelsVO);

    MtdLabelsVO useMtdLabelsVO(MtdLabels mtdLabels);
}
