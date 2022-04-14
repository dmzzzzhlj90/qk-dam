package com.qk.dm.dataingestion.mapstruct.mapper;

import com.qk.dm.dataingestion.entity.DisRuleClassification;
import com.qk.dm.dataingestion.vo.DisRuleClassVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DisRuleClassMapper {
    DisRuleClassMapper INSTANCE = Mappers.getMapper(DisRuleClassMapper.class);

    @Mappings({
            @Mapping(source = "classificationName", target = "title"),
            @Mapping(source = "classificationName", target = "value"),
            @Mapping(target = "children",ignore = true),
    })
    DisRuleClassVO of(DisRuleClassification disRuleClassification);

    List<DisRuleClassVO> list(List<DisRuleClassification> disRuleClassificationList);

    @Mappings({
            @Mapping(source = "title", target = "classificationName"),
            @Mapping(target = "gmtCreate",ignore = true),
            @Mapping(target = "gmtModified",ignore = true),
            @Mapping(target = "delFlag",ignore = true),
    })
    DisRuleClassification of(DisRuleClassVO disRuleClassVO);

    @Mappings({
            @Mapping(source = "title", target = "classificationName")
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void of(DisRuleClassVO disRuleClassVO, @MappingTarget DisRuleClassification disRuleClassification);
}
