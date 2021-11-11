package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.entity.DqcSchedulerConfig;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据质量_规则调度_规则信息VO转换mapper
 *
 * @author wjq
 * @date 2021/11/09
 * @since 1.0.0
 */
@Mapper
public interface DqcSchedulerConfigMapper {
    DqcSchedulerConfigMapper INSTANCE = Mappers.getMapper(DqcSchedulerConfigMapper.class);

    DqcSchedulerConfigVO userDqcSchedulerConfigVO(DqcSchedulerConfig dqcSchedulerConfig);

    DqcSchedulerConfig userDqcSchedulerConfig(DqcSchedulerConfig dqcSchedulerConfig);

}
