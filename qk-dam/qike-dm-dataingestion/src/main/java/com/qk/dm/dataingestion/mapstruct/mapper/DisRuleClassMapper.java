package com.qk.dm.dataingestion.mapstruct.mapper;

import com.qk.dm.dataingestion.entity.DisRuleClassification;
import com.qk.dm.dataingestion.vo.DisRuleClassVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DisRuleClassMapper {
    DisRuleClassMapper INSTANCE = Mappers.getMapper(DisRuleClassMapper.class);

    DisRuleClassVO of(DisRuleClassification disRuleClassification);

    List<DisRuleClassVO> list(List<DisRuleClassification> disRuleClassificationList);
}
