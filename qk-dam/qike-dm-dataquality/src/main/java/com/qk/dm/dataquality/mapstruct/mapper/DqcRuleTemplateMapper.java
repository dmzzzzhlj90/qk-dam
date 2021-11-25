package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVO;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/9 6:13 下午
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DqcRuleTemplateMapper {
    DqcRuleTemplateMapper INSTANCE = Mappers.getMapper(DqcRuleTemplateMapper.class);

    DqcRuleTemplate userDqcRuleTemplate(DqcRuleTemplateVO dqcRuleTemplateVo);

    List<DqcRuleTemplateInfoVO> userDqcRuleTemplateInfoVo(List<DqcRuleTemplate> dqcRuleTemplateList);

    DqcRuleTemplateInfoVO userDqcRuleTemplateInfoVo(DqcRuleTemplate dqcRuleTemplate);

    void userDqcRuleTemplate(DqcRuleTemplateVO dqcRuleTemplateVo, @MappingTarget DqcRuleTemplate dqcRuleTemplate);
}
